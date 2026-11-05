package com.ceva.cevatrack.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.CrmStatus78;

/**
 * DeltaSpike repository following the same style as SEBRepository.
 * Java 8 compatible.
 *
 * - Queries return a plain ArrayList (OpenJPA result lists are read-only and break PrimeFaces sorting/filtering).
 * - Write methods are @Transactional so they work under JTA (TomEE / OpenEJB).
 */
@Repository(forEntity = CrmStatus78.class)
public abstract class CrmStatus78Repository extends AbstractFullEntityRepository<CrmStatus78, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(CrmStatus78Repository.class);

    @SuppressWarnings("unchecked")
    public List<CrmStatus78> findByDateWindow(Date from, Date to) {
        try {
            if (from == null || to == null) {
                return new ArrayList<CrmStatus78>();
            }
            // Format as wall-clock strings and cast in SQL so OpenJPA/Postgres
            // does not apply a timezone offset to the parameters.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fromStr = sdf.format(from);
            String toStr = sdf.format(to);
            LOG.info("CRM date filter – ATA from {} to {}", fromStr, toStr);

            List<CrmStatus78> result = entityManager()
                    .createNativeQuery(
                            "SELECT * FROM crm_status78 "
                            + "WHERE ata >= CAST(:from AS TIMESTAMP) "
                            + "AND ata <= CAST(:to AS TIMESTAMP) "
                            + "ORDER BY ata DESC, atd DESC",
                            CrmStatus78.class)
                    .setParameter("from", fromStr)
                    .setParameter("to", toStr)
                    .getResultList();
            return new ArrayList<CrmStatus78>(result);
        } catch (Exception e) {
            LOG.error("Error querying CRM status78 by date window", e);
            return new ArrayList<CrmStatus78>();
        }
    }

    public long countAll() {
        try {
            Long count = entityManager()
                    .createQuery("SELECT COUNT(c) FROM CrmStatus78 c", Long.class)
                    .getSingleResult();
            return count != null ? count.longValue() : 0L;
        } catch (Exception e) {
            LOG.error("Error counting CRM status78 records", e);
            return 0L;
        }
    }

    @Transactional
    public int deleteOlderThan(Date cutoff) {
        try {
            if (cutoff == null) {
                return 0;
            }
            return entityManager()
                    .createQuery("DELETE FROM CrmStatus78 c WHERE c.lastUpdated < :cutoff OR c.ata < :cutoff")
                    .setParameter("cutoff", cutoff)
                    .executeUpdate();
        } catch (Exception e) {
            LOG.error("Error purging old CRM status78 records", e);
            return 0;
        }
    }

    public CrmStatus78 findByOrderAndLine(String orderRef, String lineRef) {
        try {
            if (orderRef == null || lineRef == null) {
                return null;
            }
            List<CrmStatus78> list = entityManager()
                    .createQuery("SELECT c FROM CrmStatus78 c WHERE c.orderReference = :o AND c.orderLineRef = :l",
                            CrmStatus78.class)
                    .setParameter("o", orderRef)
                    .setParameter("l", lineRef)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            LOG.debug("No existing CRM record for {} / {}", orderRef, lineRef);
            return null;
        }
    }

    @Transactional
    public void saveOrUpdate(CrmStatus78 entity) {
        if (entity == null) {
            return;
        }
        CrmStatus78 existing = findByOrderAndLine(entity.getOrderReference(), entity.getOrderLineRef());
        if (existing == null) {
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(new Date());
            }
            save(entity);
        } else {
            existing.setSiteId(entity.getSiteId());
            existing.setSiteName(entity.getSiteName());
            existing.setRouteCode(entity.getRouteCode());
            existing.setAta(entity.getAta());
            existing.setAtd(entity.getAtd());
            existing.setPlannedQty(entity.getPlannedQty());
            existing.setActualQty(entity.getActualQty());
            existing.setStatus(entity.getStatus());
            existing.setScanned(entity.getScanned());
            existing.setClauseReason(entity.getClauseReason());
            existing.setNetwork(entity.getNetwork());
            existing.setLastUpdated(entity.getLastUpdated());
            existing.setLastWsCall(entity.getLastWsCall());
            save(existing);
        }
    }
}
