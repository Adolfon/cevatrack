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
    private static final String SN_SITE = "sn_site:view:";
    private static final String SN_GLOBAL = "sn_site:view";
    private static final String TECHNET_SITE = "tech_site:view"; // WS user
    private static final String TN_GLOBAL = "tn_site:view";
    private static final String TN_SITE = "tn_site:view:";
    private static final String AM_GLOBAL = "am_site:view";
    private static final String AM_SITE = "am_site:view:";
    private static final String HC_GLOBAL = "hc_site:view";
    private static final String HC_SITE = "hc_site:view:";
    
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
    // I need to make this method generic for different networks (in case of different DB table structure)
    public Map<String, ConsignmentPortfolioSummary> getPortfolioStats(String fromYyyymmdd, String toYyyymmdd,
            List<SmartNetClientVO> clientsDB, String network) throws Exception {
    	if (clientsDB == null){
    		return null;
    	}
    	LOGGER.info(" Starting getPortfolioStats, database members: " + clientsDB.size());
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
            WsConsignmentSummaries = getConsignmentSummaries(fromYyyymmdd, toYyyymmdd, true, false, network);
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
        LOGGER.info(" -- Clients that are NOT in the clients database: " + outSmartNet);

        // This adds up prefixes by client in the DataBase
        clientsDB.forEach(cl -> {
            ConsignmentPortfolioSummary clientSummary = new ConsignmentPortfolioSummary(cl);
            cl.getPfx().forEach(pfx -> {
                clientSummary.addPrefixSummary(prefixCountsDB.get(pfx));
            });
            resp.put(cl.getName(), clientSummary);
        });
        LOGGER.info(" -- Finising method response size: " + resp.size());
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
            List<String> prefixes, Boolean cache, Boolean WS, String network) throws Exception {
        DatedConsignmentList parent;
        List<ConsignmentSummary> respList = new ArrayList<ConsignmentSummary>();
        try {
            parent = getConsignmentSummaries(fromYyyymmdd, toYyyymmdd, cache, WS, network);
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
            Boolean WS, String network) throws Exception {

        housekeep(); // Erase old consignments
        String listKey;
        Subject currentUser = null;
        WsMicroliseEnvironment env = null;
        if (!WS) {
            currentUser = SecurityUtils.getSubject();
            env = new WsMicroliseEnvironment(network);
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
                    return getUserConsignmentList(consignmentStore.get(listKey), currentUser, WS, network);
                }
            }
        }

        try {
            DatedConsignmentList dcl = new DatedConsignmentList(fromYyyymmdd, toYyyymmdd, WS, network);
            // Fix Status from Pending to Delivered when the consignment arrival date is present            
            LOGGER.info("Consignment store size before put: " + consignmentStore.size());
            if (dcl.getConsignmentSummaries() == null){
            	LOGGER.info( " --------it is null");
            }else{
            	LOGGER.info( " --------it is not null, size: " + dcl.getConsignmentSummaries().size());            	
            	consignmentStore.put(listKey, dcl);
            	fixStatusPendingToDeliveredWhenArrivalTimeIsPresent(dcl);
            }
            
            LOGGER.info("Consignment store after put: " + consignmentStore.size() + ",key: " + listKey
                    + ", DatedConsignmentList size: " + dcl.getConsignmentSummaries().size() + " WS?: " + WS);
            return getUserConsignmentList(dcl, currentUser, WS, network);
        } catch (Exception e) {// here I need IOException
        	LOGGER.error(" -- Exception in Consignment List manager calling new DatedConsignmentList or fixing status..");
            if (consignmentStore.containsKey(listKey)) {
                // old data is better than no data!
                return getUserConsignmentList(consignmentStore.get(listKey), currentUser, WS, network);
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

    private DatedConsignmentList getUserConsignmentList(DatedConsignmentList source, Subject currentUser, Boolean WS, String network) {
        // this subroutine will only allow out things that a user is allowed to see
//        if (WS || currentUser.isPermitted(SN_GLOBAL) || currentUser.isPermitted(TECHNET_SITE)) {
//            return source;
//        }
        LOGGER.info(" -- Starting method: getUserConsignmentList, network: " + network);
        if (WS && currentUser.isPermitted(TECHNET_SITE)){
        	return source;
        }        
        DatedConsignmentList dcl = new DatedConsignmentList(source); //here dcl = 0 but source = 989 elements...(starts with 0)
        if(network.equalsIgnoreCase("smartnet")){
        	if (currentUser.isPermitted(SN_GLOBAL)){
        		return source;
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(SN_SITE + con.getSiteId())).collect(Collectors.toList()));
        }        
        if(network.equalsIgnoreCase("technet")){
        	if (currentUser.isPermitted(TN_GLOBAL)){
        		return source;
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(TN_SITE + con.getSiteId())).collect(Collectors.toList()));
        }           
        if(network.equalsIgnoreCase("automotive")){
        	if (currentUser.isPermitted(AM_GLOBAL)){
        		return source;
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(AM_SITE + con.getSiteId())).collect(Collectors.toList()));
        }
        if(network.equalsIgnoreCase("healthcare")){ //It is not entering here!!! that´s why the consignments are not being added...
        	if (currentUser.isPermitted(HC_GLOBAL)){
        		return source;
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(HC_SITE + con.getSiteId())).collect(Collectors.toList()));
        } 
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
    public Consignment getConsignmentDetail(String consignmentReference, String network) throws Exception {
        Consignment consignment;
        Subject currentUser = SecurityUtils.getSubject();
        try {
            consignment = WsMicroliseComms.getConsignment(consignmentReference, network);
            if (consignment == null){
            	LOGGER.error(" -- The consignment was not retrieved and it is null --");
            	throw new Exception("-- The consignment was not retrieved and it is null --");
            }
            fixStatusPendingToDeliveredWhenArrivalTimeIsPresent(consignment);
            if (network.equalsIgnoreCase("smartnet")){
	            if (currentUser.isPermitted(SN_GLOBAL) || currentUser.isPermitted(SN_SITE + consignment.getSiteId())) {
	                return consignment;
	            }	            
            }
            if (network.equalsIgnoreCase("technet")){
	            if (currentUser.isPermitted(TN_GLOBAL) || currentUser.isPermitted(TN_SITE + consignment.getSiteId())) {
	                return consignment;
	            }	            
            }     
            if (network.equalsIgnoreCase("Automotive")){
	            if (currentUser.isPermitted(AM_GLOBAL) || currentUser.isPermitted(AM_SITE + consignment.getSiteId())) {
	                return consignment;
	            }	            
            }
            if (network.equalsIgnoreCase("Healthcare")){
	            if (currentUser.isPermitted(HC_GLOBAL) || currentUser.isPermitted(HC_SITE + consignment.getSiteId())) {
	                return consignment;
	            }	            
            }
            if (network.equalsIgnoreCase("HealthLink")){
            	// HEALTH NETWORK FOR NUTRICIA LINK VALIDATES FOR NOW ONLY THROUGH DATABASE LINK
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
