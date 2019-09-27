package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.vo.SmartNetClientVO;
//import com.ceva.cevatrack.vo.TechNetClientVO;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseComms;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseEnvironment;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;

/**
 * @author brian.goodall
 *
 */
@ManagedBean(name = "consignmentListManager")
@ApplicationScoped
public class ConsignmentListManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsignmentListManager.class);
    private Map<String, DatedConsignmentList> consignmentStore;
    private static final String PERM = "sn_site:view:";
    private static final String PERM_GLOBAL = "sn_site:view";
    private static final String TECHNET_SITE = "tech_site:view"; // WS user

    @PostConstruct
    public void init() {
        consignmentStore = new ConcurrentHashMap<String, DatedConsignmentList>();
    }

    public Map<String, DatedConsignmentList> getConsignmentStore() {
        return consignmentStore;
    }

    public void setConsignmentStore(Map<String, DatedConsignmentList> consignmentStore) {
        this.consignmentStore = consignmentStore;
    }

    /**
     * @param fromYyyymmdd
     *            from date
     * @param toYyyymmdd
     *            to date
     * @param clientsDB
     *            clients list of clients with prefixes to include
     * @return filtered list
     * @throws Exception
     *             if unable to access list
     */
    public Map<String, ConsignmentPortfolioSummary> getPortfolioStats(String fromYyyymmdd, String toYyyymmdd,
            List<SmartNetClientVO> clientsDB) throws Exception {
        DatedConsignmentList WsConsignmentSummaries;

        Map<String, ConsignmentPortfolioSummary> resp = new HashMap<String, ConsignmentPortfolioSummary>();
        Map<String, ConsignmentPrefixCounts> prefixCountsDB = new HashMap<String, ConsignmentPrefixCounts>();

        // first establish the prefixes to count for this request
        clientsDB.forEach(cl -> {
            cl.getPfx().forEach(pfx -> {
                if (!prefixCountsDB.containsKey(pfx)) {
                    prefixCountsDB.put(pfx, new ConsignmentPrefixCounts(pfx));
                }
            });
        });

        LOGGER.info(" -- Prefixes to filter from the database: " + prefixCountsDB.toString());
        // We're all set up to count now...
        try {
            WsConsignmentSummaries = getConsignmentSummaries(fromYyyymmdd, toYyyymmdd, true, false);
        } catch (IOException io) {
            throw new IOException("IO Error accessing WsConsignmentSummaries Consignment List", io);
        } catch (Exception e) {
            throw new Exception("Error accessing WsConsignmentSummaries Consignment List", e);
        }

        // This adds up cons by prefix
        HashSet<String> outSmartNet = new HashSet<>();
        WsConsignmentSummaries.getConsignmentSummaries().forEach(con -> {
            ConsignmentPrefixCounts sofar = prefixCountsDB.get(con.getPrefix());
            if (sofar != null) {
                sofar.addConsignment(con);
                prefixCountsDB.put(con.getPrefix(), sofar);
            } else {
                if (!outSmartNet.contains(con.getPrefix())) {
                    outSmartNet.add(con.getPrefix());
                }
            }
        });
        LOGGER.info(" -- Clients that are NOT in the SmartNet database: " + outSmartNet);

        // This adds up prefixes by client in the DataBase
        clientsDB.forEach(cl -> {
            ConsignmentPortfolioSummary clientSummary = new ConsignmentPortfolioSummary(cl);
            cl.getPfx().forEach(pfx -> {
                clientSummary.addPrefixSummary(prefixCountsDB.get(pfx));
            });
            resp.put(cl.getName(), clientSummary);
        });

        return resp;
    }

    /**
     * @param fromYyyymmdd
     *            from date
     * @param toYyyymmdd
     *            to date
     * @param prefixes
     *            list of consignment prefixes to include in the result
     * @return filtered list
     * @throws Exception
     *             if unable to access list
     * 
     *             This is the main method to call for ConsingmentSummaries
     */
    public DatedConsignmentList getFilteredConsignmentSummaries(String fromYyyymmdd, String toYyyymmdd,
            List<String> prefixes, Boolean cache, Boolean WS) throws Exception {
        DatedConsignmentList parent;
        List<ConsignmentSummary> respList = new ArrayList<ConsignmentSummary>();
        try {
            parent = getConsignmentSummaries(fromYyyymmdd, toYyyymmdd, cache, WS);
        } catch (IOException e1) {
            throw new IOException("IOExcpetion accessing Parent Consignment List", e1);
        } catch (Exception e) {
            throw new Exception("Error accessing Parent Consignment List", e);

        }

        if (prefixes != null) {
            LOGGER.info("Prefixes to filter: " + prefixes.toString());
            respList = parent.getConsignmentSummaries().stream().filter(c -> prefixes.contains(c.getPrefix()))
                    .collect(Collectors.toList());
        }
        return new DatedConsignmentList(parent.getRefreshDateTime(), parent.getDateRange(), respList,
                parent.getToDate(), parent.getFromDate());
    }

    /**
     * @param fromYyyymmdd
     *            from date
     * @param toYyyymmdd
     *            to date
     * @return container with list of consignment summaries
     * @throws Exception
     *             if unable to generate one
     */
    public DatedConsignmentList getConsignmentSummaries(String fromYyyymmdd, String toYyyymmdd, Boolean cache,
            Boolean WS) throws Exception {

        housekeep(); // Erase old consignments
        String listKey;
        Subject currentUser = null;
        WsMicroliseEnvironment env = null;
        if (!WS) {
            currentUser = SecurityUtils.getSubject();
            env = new WsMicroliseEnvironment();
        } else {
            env = new WsMicroliseEnvironment(WS);
        }

        if (StringUtils.isEmpty(toYyyymmdd)) {
            toYyyymmdd = fromYyyymmdd;
        }
        listKey = env.getEnvDescription() + fromYyyymmdd + toYyyymmdd;
        LOGGER.info(" -- The listkey value is: " + listKey);
        LOGGER.info(" The cache value is: " + cache);
        if (cache) {
            if (consignmentStore.containsKey(listKey)) {
                if (consignmentStore.get(listKey).isCurrent()) {
                    LOGGER.info("-- The obtained consignment list was from the cache -- cache: " + cache);
                    return getUserConsignmentList(consignmentStore.get(listKey), currentUser, WS);
                }
            }
        }

        try {
            DatedConsignmentList dcl = new DatedConsignmentList(fromYyyymmdd, toYyyymmdd, WS);
            // Fix Status from Pending to Delivered when the consignment arrival date is present
            fixStatusPendingToDeliveredWhenArrivalTimeIsPresent(dcl);
            LOGGER.info("Consignment store size before put: " + consignmentStore.size());
            consignmentStore.put(listKey, dcl);
            LOGGER.info("Consignment store after put: " + consignmentStore.size() + ",key: " + listKey
                    + ", DatedConsignmentList size: " + dcl.getConsignmentSummaries().size());
            return getUserConsignmentList(dcl, currentUser, WS);
        } catch (Exception e) {// here I need IOException
            if (consignmentStore.containsKey(listKey)) {
                // old data is better than no data!
                return getUserConsignmentList(consignmentStore.get(listKey), currentUser, WS);
            }
            if (e.getClass() == IOException.class) {
                throw new IOException("IO Exception error in remote server communication is escalating..", e);
            }
            throw new Exception("Error in remote server communication is escalating.", e);
        }
    }

    private void fixStatusPendingToDeliveredWhenArrivalTimeIsPresent(DatedConsignmentList dcl) {
        dcl.getConsignmentSummaries().stream().filter(obj -> !StringUtils.isBlank((CharSequence) obj.getAtaString()) &&
        // obj.getStatusString().trim().equalsIgnoreCase("Pending"))
                obj.getConsignmentStatus() == 78).forEach(obj -> {
                    LOGGER.info(
                            "-- Changing the status to Delivered for consignment: " + obj.getConsignmentReference());
                    obj.setConsignmentStatus((short) 67);
                });
    }

    private DatedConsignmentList getUserConsignmentList(DatedConsignmentList source, Subject currentUser, Boolean WS) {
        // this subroutine will only allow out things that a user is allowed to see
        if (WS || currentUser.isPermitted(PERM_GLOBAL) || currentUser.isPermitted(TECHNET_SITE)) {
            return source;
        }
        DatedConsignmentList dcl = new DatedConsignmentList(source);
        dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                .filter(con -> currentUser.isPermitted(PERM + con.getSiteId())).collect(Collectors.toList()));
        return dcl;
    }

    /**
     * clean up old data
     */
    public void housekeep() {
        LOGGER.info("Listing all the consignments..");
        if (consignmentStore != null) {
            consignmentStore.keySet().stream()
                    .forEach(c -> LOGGER.info("key: " + c + ",for deletion: " + consignmentStore.get(c).isForDeletion()
                            + ", valid minutes: " + consignmentStore.get(c).getValidMinutes() + ", age in minutes: "
                            + consignmentStore.get(c).getAgeInMinutes()));

            consignmentStore.keySet().stream().filter(c -> consignmentStore.get(c).isForDeletion()).forEach(c -> {
                consignmentStore.remove(c);
                LOGGER.info("--Old consignment erased:" + c);
            });
        } else {
            consignmentStore = new ConcurrentHashMap<String, DatedConsignmentList>();
        }
    }

    /**
     * @param consignmentReference
     *            is the chosen consignment
     * @return the consignment
     * @throws Exception
     *             if not available
     */
    public Consignment getConsignmentDetail(String consignmentReference) throws Exception {
        Consignment consignment;
        Subject currentUser = SecurityUtils.getSubject();
        boolean ok = false;
        try {
            consignment = WsMicroliseComms.getConsignment(consignmentReference);
            fixStatusPendingToDeliveredWhenArrivalTimeIsPresent(consignment);
            if (currentUser.isPermitted(PERM_GLOBAL)) {
                return consignment;
            }
            if (currentUser.isPermitted(PERM + consignment.getSiteId())) {
                return consignment;
            }
            throw new Exception("User does not have access to site - Security violation");
        } catch (Exception e) {
            // there was an error - so maybe build something from the summary instead??
            throw new Exception("Exception escalating in Con List Manager", e);
        }
    }

    private void fixStatusPendingToDeliveredWhenArrivalTimeIsPresent(Consignment consignment) {
        if (!StringUtils.isBlank((CharSequence) consignment.getAtaString()) &&
        // consignment.getStatusString().trim().equalsIgnoreCase("Pending")){
                consignment.getConsignmentStatus() == 78) {
            consignment.setConsignmentStatus((short) 67);
            LOGGER.info("Pending to Delivered status for consignment ref:" + consignment.getConsignmentReference());
        }

    }
}
