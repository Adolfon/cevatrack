package com.ceva.cevatrack.rest.v1.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.AAFUser4Nike;
import com.ceva.cevatrack.domain.ClientsAPI;

@Path("/johnson")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JohnsonAPI implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PERSISTENCE_UNIT_NAME = "cevatrackPULocal";
    private static volatile EntityManagerFactory emf;

    private static final Logger LOGGER = LoggerFactory.getLogger(JohnsonAPI.class);
    private static final DateTimeFormatter FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @GET
    @Path("/getShipmentInfoByRef")
    public Response getShipmentInfoByRef() {
        LOGGER.info("--- The method was called without parameters..");
        return Response.status(400).entity("The request is malformed").build();
    }

    @GET
    @Path("/getShipmentInfoByRef/{ref}")
    public Response getShipmentInfoByRef(@PathParam("ref") String ref,
                                         @Context HttpHeaders headers,
                                         @Context HttpServletRequest req) {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();                 // START TRANSACTION

            if (ref == null || ref.trim().isEmpty()) {
                em.getTransaction().rollback();
                return Response.status(400).entity("No parameter was included on the call..").build();
            }
            String cleanRef = ref.trim();

            // 1. AUTHENTICATION
            int authResult = authenticateUser(headers, em);
            if (authResult != 0) {
            	em.getTransaction().commit();
                switch (authResult) {
                    case -3: return Response.status(403).entity("There was a problem with the user Login (unsupport encoding)").build();
                    case 3:  return Response.status(403).entity("Basic authentication is required").build();
                    case 4:  return Response.status(403).entity("User is blocked, too many attempts").build();
                    default: return Response.status(403).entity("Incorrect user - password combination").build();
                }
            }

            LOGGER.info(" Authentication of the user was correct!");

            // 2. MAIN BUSINESS QUERY
            ClientsAPI result = findShipmentByRef(cleanRef, em);
            if (result == null) {
                em.getTransaction().commit();            // commit even on 404
                LOGGER.info("-- The reference number entered did not return any result.. ");
                return Response.status(404).entity("The reference number is incorrect").build();
            }

            // SUCCESS — commit everything (including any login attempt resets)
            em.getTransaction().commit();
            return createResponse(result);

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                try { em.getTransaction().rollback(); } catch (Exception ignored) {}
            }
            LOGGER.error("Unexpected error in JohnsonAPI", e);
            return Response.status(500).entity("Internal server error").build();

        } finally {
            if (em != null && em.isOpen()) {
                try { em.close(); } catch (Exception ex) {
                    LOGGER.warn("Error closing EntityManager", ex);
                }
            }
        }
    }

    // ===================================================================
    // AUTH & LOGIN ATTEMPTS — NOW WORKING 100%
    // ===================================================================

    private int authenticateUser(HttpHeaders headers, EntityManager em) {
        String authHeader = headers.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return 3;
        }

        try {
            String base64 = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64), "UTF-8");
            String[] parts = credentials.split(":", 2);
            String username = parts[0];
            String password = parts.length > 1 ? parts[1] : "";

            return validateUser(username, password, em);

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error decoding Basic Auth", e);
            return -3;
        }
    }

    private int validateUser(String username, String password, EntityManager em) {
        if (username == null || username.isEmpty()) {
            return 2;
        }

        try {
            AAFUser4Nike user = em.createQuery(
                    "SELECT u FROM AAFUser4Nike u WHERE u.userPrincipal = :up", AAFUser4Nike.class)
                    .setParameter("up", username)
                    .getSingleResult();

            if (!user.getIsActive() || user.getIsLocked()) {
                return 4;
            }

            Integer attempts = user.getIncorrectLoginAttempts();
            if (attempts != null && attempts > 4) {
                return 4;
            }

            if (pass_validation(user.getPassword(), password)) {
                resetNumberAttemptUser(username, em);
                return 0; // success
            } else {
                addNumberAttemptUser(username, em);   // modifies managed entity → will be persisted on commit
                return 2; // wrong password
            }

        } catch (NoResultException e) {
            LOGGER.warn("User not found: {}", username);
            return 2;
        } catch (Exception e) {
            LOGGER.error("Error validating user", e);
            return 2;
        }
    }

    private void addNumberAttemptUser(String username, EntityManager em) {
        LOGGER.info(" -- Adding incorrect login attempt for user: {}", username);
        try {
            AAFUser4Nike user = em.createQuery(
                    "SELECT u FROM AAFUser4Nike u WHERE u.userPrincipal = :up", AAFUser4Nike.class)
                    .setParameter("up", username)
                    .getSingleResult();

            Integer attempts = user.getIncorrectLoginAttempts();
            int newCount = (attempts == null ? 0 : attempts) + 1;
            user.setIncorrectLoginAttempts(newCount);

            if (newCount > 4) {
                user.setIsLocked(true);
                LOGGER.warn("User {} locked after {} failed attempts", username, newCount);
            }
            
            em.flush(); // ← THIS LINE IS MANDATORY WITH OPENJPA 2.4.2
            // No commit here — we rely on the main transaction commit

        } catch (Exception e) {
            LOGGER.error("Failed to increment login attempts", e);
            throw new RuntimeException(e); // will trigger rollback in main method
        }
    }

    private void resetNumberAttemptUser(String username, EntityManager em) {
        LOGGER.info(" Resetting login attempts for user: {}", username);
        try {
            AAFUser4Nike user = em.createQuery(
                    "SELECT u FROM AAFUser4Nike u WHERE u.userPrincipal = :up", AAFUser4Nike.class)
                    .setParameter("up", username)
                    .getSingleResult();
            user.setIncorrectLoginAttempts(0);
            em.flush(); // ← THIS LINE IS MANDATORY WITH OPENJPA 2.4.2
        } catch (Exception e) {
            LOGGER.error("Failed to reset login attempts", e);
        }
    }

    private ClientsAPI findShipmentByRef(String ref, EntityManager em) {
        try {
            return em.createQuery(
                    "SELECT c FROM ClientsAPI c WHERE c.client_id = 'JHNJHN' AND c.shipment_number = :ref",
                    ClientsAPI.class)
                    .setParameter("ref", ref)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Response createResponse(ClientsAPI cb) {
        StringBuilder rs = new StringBuilder("{");
        rs.append("\n\"carrierName\" : \"CEVA\",");

        if (notEmpty(cb.getCarrier_name()))           rs.append("\n\"executingCN\":\"").append(cb.getCarrier_name()).append("\",");
        if (notEmpty(cb.getTracking_number()))        rs.append("\n\"trackingNumber\":\"").append(cb.getTracking_number()).append("\",");
        if (notEmpty(cb.getStatus()))                rs.append("\n\"status\":\"").append(cb.getStatus()).append("\",");

        ZoneId londonZone = TimeZone.getTimeZone("Europe/London").toZoneId();
        appendDate(rs, cb.getStatus_date(), "status_date", londonZone);
        appendDate(rs, cb.getRevise_delivery_date(), "revisedDeliveryDate", londonZone);
        appendDate(rs, cb.getDelivered_date(), "delivered_date", londonZone);
        appendDate(rs, cb.getOriginal_required_date(), "original_delivery_date", londonZone);
        appendDate(rs, cb.getClaused_date(), "claused_date", londonZone);

        if (notEmpty(cb.getReason_code()))           rs.append("\n\"reason\":\"").append(cb.getReason_code()).append("\",");
        if (notEmpty(cb.getFailure_reason_code()))   rs.append("\n\"failure_reason\":\"").append(cb.getFailure_reason_code()).append("\",");
        if (notEmpty(cb.getLate_reason_code()))      rs.append("\n\"late_reason\":\"").append(cb.getLate_reason_code()).append("\",");

        if (rs.charAt(rs.length()-1) == ',') {
            rs.setLength(rs.length()-1);
        }
        rs.append("\n}");

        return Response.ok().entity(rs.toString()).build();
    }

    private boolean notEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    private void appendDate(StringBuilder sb, java.util.Date date, String key, ZoneId zone) {
        if (date != null) {
            String formatted = LocalDateTime.ofInstant(date.toInstant(), zone).format(FORMATDATE);
            sb.append("\n\"").append(key).append("\":\"").append(formatted).append("\",");
        }
    }

    private boolean pass_validation(String pass_encoded, String pass_text) {
        if (pass_encoded == null || pass_text == null) return false;
        try {
            String[] mcf = pass_encoded.split("\\$");
            if (mcf.length != 6) return false;
            int iteration_count = Integer.parseInt(mcf[3]);
            String b64_salt = mcf[4];
            String b64_hash = mcf[5];
            byte[] salt = Base64.getDecoder().decode(b64_salt.getBytes("UTF-8"));
            String calculated = new Sha256Hash(pass_text, salt, iteration_count).toBase64();
            return calculated.equals(b64_hash);
        } catch (Exception e) {
            LOGGER.error("Password validation error", e);
            return false;
        }
    }

    // ===================================================================
    // ENTITYMANAGER FACTORY
    // ===================================================================

    private synchronized EntityManager getEntityManager() {
        if (emf == null) {
            Map<String, String> props = new HashMap<>();
            props.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");

            String env = System.getProperty("environment.code", "").trim().toUpperCase();

            switch (env) {
                case "LOCAL":
                    props.put("openjpa.ConnectionURL", "jdbc:postgresql://localhost:5432/cevatrack_v6?currentSchema=cevatrack_mix");
                    props.put("openjpa.ConnectionUserName", "postgres");
                    props.put("openjpa.ConnectionPassword", "holahola");
                    break;
                case "CEVA-CEVATRACK-DEVL":
                    props.put("openjpa.ConnectionURL", "jdbc:postgresql://iahlvlpgdb002:5432/cevatrack_responsive");
                    props.put("openjpa.ConnectionUserName", "cevatrack_responsive");
                    props.put("openjpa.ConnectionPassword", "Cevatrack_responsive@2024");
                    break;
                case "CEVA-CEVATRACK-TEST":
                    props.put("openjpa.ConnectionURL", "jdbc:postgresql://glsdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");
                    props.put("openjpa.ConnectionUserName", "cevatrack_responsive");
                    props.put("openjpa.ConnectionPassword", "NqHyxoHGwA56P");
                    break;
                case "CEVA-CEVATRACK-STAGE":
                    props.put("openjpa.ConnectionURL", "jdbc:postgresql://glpdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");
                    props.put("openjpa.ConnectionUserName", "cevatrack_responsive");
                    props.put("openjpa.ConnectionPassword", "LqEKbxqZvUWb");
                    break;
                default:
                    throw new IllegalStateException("Unknown environment.code: " + env);
            }

            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);
            LOGGER.info("EntityManagerFactory created for environment: {}", env);
        }
        return emf.createEntityManager();
    }
}