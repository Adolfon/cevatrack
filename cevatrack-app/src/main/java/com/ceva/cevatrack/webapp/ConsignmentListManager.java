package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.service.P44Service;
import com.ceva.cevatrack.util.ApplicationGlobalConfig;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseComms;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseEnvironment;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static java.util.Comparator.comparing;
import java.util.TreeSet;

/**
 * @author brian.goodall
 *
 */
@ManagedBean(name = "consignmentListManager")
@ApplicationScoped
public class ConsignmentListManager {
	@EJB
	P44Service p44;	
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
            WsConsignmentSummaries = getConsignmentSummaries(fromYyyymmdd, toYyyymmdd, true, false, network, p44Consignments(fromYyyymmdd,toYyyymmdd, network));
            // This gerenerates duplicates
            //WsConsignmentSummaries.getConsignmentSummaries().addAll(p44Consignments(fromYyyymmdd,toYyyymmdd, network));
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


	private Collection<ConsignmentSummary> p44Consignments(String fromYyyymmdd, String toYyyymmdd,
			String network) {
        if (StringUtils.isEmpty(toYyyymmdd)) {
        	LOGGER.info(" toYyyymmdd parameter was null, setting it to the fromYyyymmdd one.. ");
            toYyyymmdd = fromYyyymmdd;
        }
		LOGGER.info(" -- Querying P44 consignments with dates, from: " + fromYyyymmdd + " , to: " + toYyyymmdd );	
		//Collection<Consignment44> col = p44.getListConsignmentsByDate(fromYyyymmdd, toYyyymmdd);		
		Collection<ConsignmentSummary> col = p44.getListConsignmentsByDateToCS(fromYyyymmdd, toYyyymmdd);
		LOGGER.info(" The collection has " + col.size() + " items ");
		//Collection<ConsignmentSummary> colect = new ArrayList<ConsignmentSummary>(col);		
		return col;		
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
            parent = getConsignmentSummaries(fromYyyymmdd, toYyyymmdd, cache, WS, network, p44Consignments(fromYyyymmdd,toYyyymmdd, network));
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
            Boolean WS, String network, Collection<ConsignmentSummary> p44) throws Exception {
    	LOGGER.info(" -- Received parameters: " + fromYyyymmdd + ", toYyyymmdd: " + toYyyymmdd + " ,cache: " + cache);
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
            LOGGER.info("Consignment store (cache) size before put: " + consignmentStore.size());
            if (dcl == null || dcl.getConsignmentSummaries() == null){
            	LOGGER.error( " --------dcl is null");
            	throw new Exception(" -- DatedConsignmentList returned null -- ");
            }else{
            	LOGGER.info( " --------dcl is not null, size: " + dcl.getConsignmentSummaries().size()); 
            	if (p44 != null){
            		LOGGER.info(" -- adding p44 consignments (size: " + p44.size() + ") to the Microlise ones, size: " + dcl.getConsignmentSummaries().size());            		
            		List<ConsignmentSummary> consignmentSummaries = addP44ConsignmentsWithoutDups(dcl.getConsignmentSummaries(),p44);
            		dcl.getConsignmentSummaries().clear();
            		dcl.getConsignmentSummaries().addAll(consignmentSummaries);
            		LOGGER.info(" -- Total number of consignments after the merge: " + dcl.getConsignmentSummaries().size());
            	}else{
            		LOGGER.warn(" -- P44 was null, no adding P44 consignments.. --");
            	}            	
            	consignmentStore.put(listKey, dcl);
            	LOGGER.info("-- The new cache position has been added correctly, new size: " + consignmentStore.size());
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

    /*
     * Add a P44 consignment if does not exist already from microlise and remove the duplicates
     */
    private List<ConsignmentSummary> addP44ConsignmentsWithoutDups(
			List<ConsignmentSummary> consignmentSummaries, Collection<ConsignmentSummary> p442) {
    	LOGGER.info(" -- Starting method addP44ConsignmentsWithoutDups --");
    	if (p442 == null){
    		return consignmentSummaries;
    	}    
    	LOGGER.info(" -- Starting no dups consignments method,  Microlise consignments: " + consignmentSummaries.size() +", p44: "+ p442.size() + ", -- ");
    	List<ConsignmentSummary> result = new ArrayList<>(p442);
    	LOGGER.info(" -- result after addding p44 cons: " + result.size());
    	result.addAll(consignmentSummaries);
    	LOGGER.info(" -- result after addding Microlise cons: " + result.size());
    	List<ConsignmentSummary> usersDistinctByRef = result.stream().collect(
                collectingAndThen(toCollection(() ->
                   new TreeSet<>(comparing(ConsignmentSummary::getConsignmentReference))), ArrayList::new));    	    
    	LOGGER.info(" --- List without duplicates size: " + usersDistinctByRef.size());
    	return usersDistinctByRef;
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
        	LOGGER.info(" -- Returning via WS route..");
        	return source;
        }        
        DatedConsignmentList dcl = new DatedConsignmentList(source); //here dcl = 0 but source = 989 elements...(starts with 0)
        if(network.equalsIgnoreCase("smartnet")){
        	if (currentUser.isPermitted(SN_GLOBAL)){
        		LOGGER.info(" -- SN_GLOBAL permission granted, returning source..");
        		return source;
        	}
        	LOGGER.info(" --- Not global permission in smartnet, filtering sites, before: " + source.getConsignmentSummaries().size());
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(SN_SITE + con.getSiteId())).collect(Collectors.toList()));
        	LOGGER.info(" -- Smartnet after filtering user permissions: " + source.getConsignmentSummaries().size());
        }        
        if(network.equalsIgnoreCase("technet")){
        	if (currentUser.isPermitted(TN_GLOBAL)){
        		LOGGER.info(" -- TN_GLOBAL permission granted, returning source..");
        		return source;
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(TN_SITE + con.getSiteId())).collect(Collectors.toList()));
        	LOGGER.info(" -- After filtering Technet: " + source.getConsignmentSummaries().size());
        }           
        if(network.equalsIgnoreCase("automotive")){
        	if (currentUser.isPermitted(AM_GLOBAL)){
        		LOGGER.info(" -- AM_GLOBAL permission granted, returning source..");
        		return source;        	
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(AM_SITE + con.getSiteId())).collect(Collectors.toList()));
        	LOGGER.info(" -- After filtering Automobile: " + source.getConsignmentSummaries().size());
        }
        if(network.equalsIgnoreCase("healthcare")){ //It is not entering here!!! that is why the consignments are not being added...
        	if (currentUser.isPermitted(HC_GLOBAL)){
        		LOGGER.info(" -- HC_GLOBAL permission granted, returning source..");
        		return source;
        	}
        	dcl.getConsignmentSummaries().addAll(source.getConsignmentSummaries().stream()
                    .filter(con -> currentUser.isPermitted(HC_SITE + con.getSiteId())).collect(Collectors.toList()));
        	LOGGER.info(" -- After filtering healthcare: " + source.getConsignmentSummaries().size());
        } 
        LOGGER.info(" -- Total consignments returned: " + source.getConsignmentSummaries().size());
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
    	LOGGER.info( "-- getConsignmentDetail parameters: "+ consignmentReference + ",network: " + network);
        Consignment consignment,cs44 = null, result = null;
        Subject currentUser = null;
        
        try {        	
            consignment = WsMicroliseComms.getConsignment(consignmentReference, network);
            if (consignment != null){
            	LOGGER.info(" -- consignment not null, ETA: " + consignment.getAta() + ", ATA string: " + consignment.getAtaString() 
            		+ ",ETA string: " + consignment.getEtaString());
            }
            //Consignment is null or has not ETA and ATA
            if (consignment == null || consignment != null && (consignment.getAtaString() == null && consignment.getEtaString() == null)){
            	LOGGER.warn("-- Could not recover consignment: " + consignmentReference + " ,from Microlise or has not ETA and ATA, trying from P44..");
	            cs44 = retrive_p44_consignment(consignmentReference);	
	            if (cs44 != null){
	            	LOGGER.info(" -- Consignment 44 ATA: " + cs44.getAta() + ",ATA String: " + cs44.getAtaString() + ",ETA: " + cs44.getEta() 
	            	+ " ,ETA string: " + cs44.getEtaString());
	            }
	            if (cs44 != null && cs44.getAtaString() != null && cs44.getEtaString() == null ||
	        			cs44 != null && cs44.getAtaString() == null && cs44.getEtaString() !=null){
	        		LOGGER.info(" -- P44 consignment recovered and has ATA or ETA!");   
	        		result = cs44;
	        	}
            }
            if (consignment == null && cs44 != null){
            	LOGGER.info(" -- No response from Microlise but from P44 --");
            	consignment = cs44;
            }
            if (result != null){
            	LOGGER.info(" -- Microlise response does not have ETA and ATA but P44 response does --");
            	consignment = result;
            }
            if (consignment == null){
            	LOGGER.error(" -- The consignment was not retrieved and it is null --");
            	throw new Exception("-- The consignment was not retrieved and it is null --");
            }
            if (network.equalsIgnoreCase("HealthLink") || network.equalsIgnoreCase(ApplicationGlobalConfig.TechNetLink) 
            		|| network.equalsIgnoreCase(ApplicationGlobalConfig.BaxterLink)){
            	// HEALTH NETWORK FOR NUTRICIA LINK VALIDATES FOR NOW ONLY THROUGH DATABASE LINK same as Technet link
            	LOGGER.info(" -- HealthLink or TechNetLink detected --");
                return consignment;
            }
        	LOGGER.info(" -- Going to secure the subject for network: " + network + " --");
        	currentUser = SecurityUtils.getSubject();
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
            throw new Exception("User does not have access to site - Security violation");
        } catch (Exception e) {
            // there was an error - so maybe build something from the summary instead??
            throw new Exception("Exception escalating in Con List Manager", e);
        }
    }

    /*
     * Check and retrieves if exists a project 44 consinment from the database
     */
    private Consignment retrive_p44_consignment(String consignmentReference) {
    	LOGGER.info(" -- reference p44 to query: " + consignmentReference);
		//P44Service p44 = new P44ServiceImpl();//NPE			
    	LOGGER.info(" EJB service: " + p44);
		return p44.getConsignment(consignmentReference);
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
