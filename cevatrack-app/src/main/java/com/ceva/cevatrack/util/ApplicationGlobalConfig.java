package com.ceva.cevatrack.util;

public class ApplicationGlobalConfig {
	
	public final static String GoogleMapsAPIKey ="AIzaSyCQalgzQEH3zeLOh3JIuM6bv7-uKWgeWHU";//For Nutricia
	public final static String GoogleMapsAPIKey2 = "AIzaSyDQoq0zlLhdLwOQNvQO48kGAVcHOTWz97E";//For TechNET
	public final static String GoogleMapsAPIKey3 = "AIzaSyDYp5dH4HBJkSIU6-vjTHHTZ9grWdNerp4";//For Baxter
	public final static String TechNetLink = "TechNetLink";//For TechNET
	public final static String BaxterLink = "BaxterLink";
	public final static String TechNetLink_environment_prod = "technet_link.tibco.prod";//For TechNET
	public final static String Baxter_environment_prod = "baxter_link.tibco.prod";
	/* SEB */
	public final static String secret = "Private"; //update also seb.xhtml link fragment
	/*DPD*/
	public final static String DPD_TEST_URI ="http://test-apps.geopostuk.com/tracking-core/dpd/parcels";
	public final static String DPD_PROD_URI ="https://apps.geopostuk.com/trackingcore/dpd/parcels";
	public final static String GDC_PROXY ="gdcproxy.logistics.corp";
	public final static int GDC_PROXY_PORT = 3128;
	public final static String DEV_PROXY_1 ="10.245.12.18";
	public final static String DEV_PROXY_2 ="10.245.12.19";
	public final static int DEV_PROXY_PORT = 3128;
	/*TECHNET IMAGES WEB SERVICE*/
	public final static String IMG_TEST_URI ="https://uat.microlise.com/CEVA_TechNET/TMCWEBSERVICE/GetImage.ashx?username=image&id=";
	public final static String IMG_TEST_USER ="Technet";
	public final static String IMG_TEST_PWS ="CEVA";
	public final static String IMG_PROD_USER ="Technet";
	public final static String IMG_PROD_PWS ="CEVA";
	public final static String IMG_PROD_URI ="https://live.microlise.com/CEVA_TechNET/TMCWEBSERVICE/GetImage.ashx?username=image&id=";
	public final static String IMG_TEST_PROXY_URL = "euixvlprx002.logistics.corp";
	public final static String IMG_TEST_PROXY_PORT = "3128";
	public final static String IMG_TEST_PROXY_USR_PSW = "Technet:CEVA";
	public final static String IMG_PROD_PROXY_URL = "euixvlprx002.logistics.corp";
	public final static String IMG_PROD_PROXY_PORT = "3128";
	public final static String IMG_PROD_PROXY_USR_PSW = "Technet:CEVA";	
	public final static int MAX_NUMBER_TIMES_REUSING_CONNECTION = 500;
	// REsult = OK = {
//	   "shipped": "2022-01-28T10:31:00",
//	   "ATA": "Return in progress"
//	} 
	public static final String TEST_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
			"<trackingresponse>" +
		    "<trackingdetails>" +
		        "<trackingdetail>" +
		            "<trackingnumber>15502027343989N</trackingnumber>" +
		            "<parcelnumber>15502027343989</parcelnumber>" +
		            "<consignmentnumber>2027294408</consignmentnumber>" +
		            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
		            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
		            "<deliverylocality>Rickmansworth</deliverylocality>" +
		            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
		            "<deliverycountrycode>GB</deliverycountrycode>" +
		            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
		            "<yellowcardnumber>35465072</yellowcardnumber>" +
		            "<parcelcode>15502027343989*19752</parcelcode>" +
		            "<trackingevents>" +
		                "<trackingevent>" +
		                    "<date>2022-01-31T16:15:00Z</date>" +
		                    "<code>62</code>" +
		                    "<type>DEPOT OUTBOUND</type>" +
		                    "<description>Parcel left depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-31T11:33:00Z</date>" +
		                    "<code>53</code>" +
		                    "<type>PARCEL RECONSIGNED RTC</type>" +
		                    "<description>Parcel reconsigned for onward journey (Return To Consignor)</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-31T00:20:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-31T00:18:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T15:27:00Z</date>" +
		                    "<code>68</code>" +
		                    "<type>FAILED NOTIFICATION RECEIVED</type>" +
		                    "<description>Unable to deliver, notification received</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T15:25:00Z</date>" +
		                    "<code>58</code>" +
		                    "<type>REQUEST FURTHER INSTRUCTIONS</type>" +
		                    "<description>Further instructions requested</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T15:23:00Z</date>" +
		                    "<code>8</code>" +
		                    "<type>FAILED CALLING CARD</type>" +
		                    "<description>Unable to deliver, calling card left - 35465072</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey>S5^0022*19754*868*3600*41*008**1</imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T07:55:00Z</date>" +
		                    "<code>15</code>" +
		                    "<type>OUT FOR DELIVERY</type>" +
		                    "<description>On vehicle for delivery</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T02:32:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T02:27:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T12:22:00Z</date>" +
		                    "<code>8</code>" +
		                    "<type>FAILED CALLING CARD</type>" +
		                    "<description>Unable to deliver, calling card left - 12823420</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey>S5^0022*19753*667*3600*72*008**1</imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T05:56:00Z</date>" +
		                    "<code>15</code>" +
		                    "<type>OUT FOR DELIVERY</type>" +
		                    "<description>On vehicle for delivery</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T01:14:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T01:12:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-28T22:57:00Z</date>" +
		                    "<code>9</code>" +
		                    "<type>HUB SCAN</type>" +
		                    "<description>Confirmed at Hub</description>" +
		                    "<locality>Hub 4 - Hinckley</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-28T10:31:00Z</date>" +
		                    "<code>0</code>" +
		                    "<type>PARCEL DATA</type>" +
		                    "<description>Customer data received</description>" +
		                    "<locality>Sheffield</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		            "</trackingevents>" +
		        "</trackingdetail>" +
		    "</trackingdetails>" +
		"</trackingresponse>"
;
	// Result = OK = {
//	   "shipped": "2022-01-28T10:31:00",
//	   "ATA": "Delivery Failed. Contact Nike"
//	}
	public static final String TEST_RESPONSE1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
			"<trackingresponse>" +
		    "<trackingdetails>" +
		        "<trackingdetail>" +
		            "<trackingnumber>15502027343989N</trackingnumber>" +
		            "<parcelnumber>15502027343989</parcelnumber>" +
		            "<consignmentnumber>2027294408</consignmentnumber>" +
		            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
		            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
		            "<deliverylocality>Rickmansworth</deliverylocality>" +
		            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
		            "<deliverycountrycode>GB</deliverycountrycode>" +
		            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
		            "<yellowcardnumber>35465072</yellowcardnumber>" +
		            "<parcelcode>15502027343989*19752</parcelcode>" +
		            "<trackingevents>" +		               
		                "<trackingevent>" +
		                    "<date>2022-01-31T00:20:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-31T00:18:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T15:27:00Z</date>" +
		                    "<code>68</code>" +
		                    "<type>FAILED NOTIFICATION RECEIVED</type>" +
		                    "<description>Unable to deliver, notification received</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T15:25:00Z</date>" +
		                    "<code>58</code>" +
		                    "<type>REQUEST FURTHER INSTRUCTIONS</type>" +
		                    "<description>Further instructions requested</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T15:23:00Z</date>" +
		                    "<code>8</code>" +
		                    "<type>FAILED CALLING CARD</type>" +
		                    "<description>Unable to deliver, calling card left - 35465072</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey>S5^0022*19754*868*3600*41*008**1</imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T07:55:00Z</date>" +
		                    "<code>15</code>" +
		                    "<type>OUT FOR DELIVERY</type>" +
		                    "<description>On vehicle for delivery</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T02:32:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T02:27:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T12:22:00Z</date>" +
		                    "<code>8</code>" +
		                    "<type>FAILED CALLING CARD</type>" +
		                    "<description>Unable to deliver, calling card left - 12823420</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey>S5^0022*19753*667*3600*72*008**1</imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T05:56:00Z</date>" +
		                    "<code>15</code>" +
		                    "<type>OUT FOR DELIVERY</type>" +
		                    "<description>On vehicle for delivery</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T01:14:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T01:12:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-28T22:57:00Z</date>" +
		                    "<code>9</code>" +
		                    "<type>HUB SCAN</type>" +
		                    "<description>Confirmed at Hub</description>" +
		                    "<locality>Hub 4 - Hinckley</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-28T10:31:00Z</date>" +
		                    "<code>0</code>" +
		                    "<type>PARCEL DATA</type>" +
		                    "<description>Customer data received</description>" +
		                    "<locality>Sheffield</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		            "</trackingevents>" +
		        "</trackingdetail>" +
		    "</trackingdetails>" +
		"</trackingresponse>"
;
	// Result: OK{
//	   "shipped": "2022-01-28T10:31:00",
//	   "ETA": "2022-01-30T09:55:00"
//	}
	public static final String TEST_RESPONSE2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
			"<trackingresponse>" +
		    "<trackingdetails>" +
		        "<trackingdetail>" +
		            "<trackingnumber>15502027343989N</trackingnumber>" +
		            "<parcelnumber>15502027343989</parcelnumber>" +
		            "<consignmentnumber>2027294408</consignmentnumber>" +
		            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
		            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
		            "<deliverylocality>Rickmansworth</deliverylocality>" +
		            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
		            "<deliverycountrycode>GB</deliverycountrycode>" +
		            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
		            "<yellowcardnumber>35465072</yellowcardnumber>" +
		            "<parcelcode>15502027343989*19752</parcelcode>" +
		            "<trackingevents>" +		                
		                "<trackingevent>" +
		                    "<date>2022-01-30T07:55:00Z</date>" +
		                    "<code>15</code>" +
		                    "<type>OUT FOR DELIVERY</type>" +
		                    "<description>On vehicle for delivery</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T02:32:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-30T02:27:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T12:22:00Z</date>" +
		                    "<code>8</code>" +
		                    "<type>FAILED CALLING CARD</type>" +
		                    "<description>Unable to deliver, calling card left - 12823420</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey>S5^0022*19753*667*3600*72*008**1</imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T05:56:00Z</date>" +
		                    "<code>15</code>" +
		                    "<type>OUT FOR DELIVERY</type>" +
		                    "<description>On vehicle for delivery</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T01:14:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-29T01:12:00Z</date>" +
		                    "<code>4</code>" +
		                    "<type>CONFIRMED AT DEPOT</type>" +
		                    "<description>Confirmed at depot</description>" +
		                    "<locality>Dunstable</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-28T22:57:00Z</date>" +
		                    "<code>9</code>" +
		                    "<type>HUB SCAN</type>" +
		                    "<description>Confirmed at Hub</description>" +
		                    "<locality>Hub 4 - Hinckley</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		                "<trackingevent>" +
		                    "<date>2022-01-28T10:31:00Z</date>" +
		                    "<code>0</code>" +
		                    "<type>PARCEL DATA</type>" +
		                    "<description>Customer data received</description>" +
		                    "<locality>Sheffield</locality>" +
		                    "<countrycode>GB</countrycode>" +
		                    "<imagekey></imagekey>" +
		                "</trackingevent>" +
		            "</trackingevents>" +
		        "</trackingdetail>" +
		    "</trackingdetails>" +
		"</trackingresponse>"
;
	// Result: OK {
//	   "shipped": "2022-01-28T10:31:00",
//	   "ETA": "Delivery Failed. Awaiting Second Attempt"
//	}
		public static final String TEST_RESPONSE3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<trackingresponse>" +
			    "<trackingdetails>" +
			        "<trackingdetail>" +
			            "<trackingnumber>15502027343989N</trackingnumber>" +
			            "<parcelnumber>15502027343989</parcelnumber>" +
			            "<consignmentnumber>2027294408</consignmentnumber>" +
			            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
			            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
			            "<deliverylocality>Rickmansworth</deliverylocality>" +
			            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
			            "<deliverycountrycode>GB</deliverycountrycode>" +
			            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
			            "<yellowcardnumber>35465072</yellowcardnumber>" +
			            "<parcelcode>15502027343989*19752</parcelcode>" +
			            "<trackingevents>" +		                			               
			                "<trackingevent>" +
			                    "<date>2022-01-30T02:32:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-30T02:27:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T12:22:00Z</date>" +
			                    "<code>8</code>" +
			                    "<type>FAILED CALLING CARD</type>" +
			                    "<description>Unable to deliver, calling card left - 12823420</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey>S5^0022*19753*667*3600*72*008**1</imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T05:56:00Z</date>" +
			                    "<code>15</code>" +
			                    "<type>OUT FOR DELIVERY</type>" +
			                    "<description>On vehicle for delivery</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T01:14:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T01:12:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-28T22:57:00Z</date>" +
			                    "<code>9</code>" +
			                    "<type>HUB SCAN</type>" +
			                    "<description>Confirmed at Hub</description>" +
			                    "<locality>Hub 4 - Hinckley</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-28T10:31:00Z</date>" +
			                    "<code>0</code>" +
			                    "<type>PARCEL DATA</type>" +
			                    "<description>Customer data received</description>" +
			                    "<locality>Sheffield</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			            "</trackingevents>" +
			        "</trackingdetail>" +
			    "</trackingdetails>" +
			"</trackingresponse>"
	;
		//This response is malformed because after 'out for delivery' should have delivered or failed calling card...
		/* ..but still receiving the right response = OK
		 * {
			   "shipped": "2022-01-28T10:31:00",
			   "ETA": "Awaiting ETA Confirmation"
			}
		 * 
		 */
		public static final String TEST_RESPONSE4 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<trackingresponse>" +
			    "<trackingdetails>" +
			        "<trackingdetail>" +
			            "<trackingnumber>15502027343989N</trackingnumber>" +
			            "<parcelnumber>15502027343989</parcelnumber>" +
			            "<consignmentnumber>2027294408</consignmentnumber>" +
			            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
			            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
			            "<deliverylocality>Rickmansworth</deliverylocality>" +
			            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
			            "<deliverycountrycode>GB</deliverycountrycode>" +
			            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
			            "<yellowcardnumber>35465072</yellowcardnumber>" +
			            "<parcelcode>15502027343989*19752</parcelcode>" +
			            "<trackingevents>" +		                			               
			                "<trackingevent>" +
			                    "<date>2022-01-30T02:32:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-30T02:27:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +			               
			                "<trackingevent>" +
			                    "<date>2022-01-29T05:56:00Z</date>" +
			                    "<code>15</code>" +
			                    "<type>OUT FOR DELIVERY</type>" +
			                    "<description>On vehicle for delivery</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T01:14:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T01:12:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-28T22:57:00Z</date>" +
			                    "<code>9</code>" +
			                    "<type>HUB SCAN</type>" +
			                    "<description>Confirmed at Hub</description>" +
			                    "<locality>Hub 4 - Hinckley</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-28T10:31:00Z</date>" +
			                    "<code>0</code>" +
			                    "<type>PARCEL DATA</type>" +
			                    "<description>Customer data received</description>" +
			                    "<locality>Sheffield</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			            "</trackingevents>" +
			        "</trackingdetail>" +
			    "</trackingdetails>" +
			"</trackingresponse>"
	;
		/*
		 * {
   "shipped": "2022-01-28T10:31:00",
   "ETA": "Awaiting ETA Confirmation"
}
		 */
		public static final String TEST_RESPONSE5 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<trackingresponse>" +
			    "<trackingdetails>" +
			        "<trackingdetail>" +
			            "<trackingnumber>15502027343989N</trackingnumber>" +
			            "<parcelnumber>15502027343989</parcelnumber>" +
			            "<consignmentnumber>2027294408</consignmentnumber>" +
			            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
			            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
			            "<deliverylocality>Rickmansworth</deliverylocality>" +
			            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
			            "<deliverycountrycode>GB</deliverycountrycode>" +
			            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
			            "<yellowcardnumber>35465072</yellowcardnumber>" +
			            "<parcelcode>15502027343989*19752</parcelcode>" +
			            "<trackingevents>" +		                			               			                
			                "<trackingevent>" +
			                    "<date>2022-01-29T01:14:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-29T01:12:00Z</date>" +
			                    "<code>4</code>" +
			                    "<type>CONFIRMED AT DEPOT</type>" +
			                    "<description>Confirmed at depot</description>" +
			                    "<locality>Dunstable</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-28T22:57:00Z</date>" +
			                    "<code>9</code>" +
			                    "<type>HUB SCAN</type>" +
			                    "<description>Confirmed at Hub</description>" +
			                    "<locality>Hub 4 - Hinckley</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			                "<trackingevent>" +
			                    "<date>2022-01-28T10:31:00Z</date>" +
			                    "<code>0</code>" +
			                    "<type>PARCEL DATA</type>" +
			                    "<description>Customer data received</description>" +
			                    "<locality>Sheffield</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			            "</trackingevents>" +
			        "</trackingdetail>" +
			    "</trackingdetails>" +
			"</trackingresponse>"
	;
		/*
		 * {
   "shipped": "2022-01-28T10:31:00",
   "ETA": "Awaiting ETA Confirmation"
}
		 */
		public static final String TEST_RESPONSE6 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<trackingresponse>" +
			    "<trackingdetails>" +
			        "<trackingdetail>" +
			            "<trackingnumber>15502027343989N</trackingnumber>" +
			            "<parcelnumber>15502027343989</parcelnumber>" +
			            "<consignmentnumber>2027294408</consignmentnumber>" +
			            "<deliveryaddress1>Summus Sports - Kjt</deliveryaddress1>" +
			            "<deliveryaddress2>280 Baldwins Lane</deliveryaddress2>" +
			            "<deliverylocality>Rickmansworth</deliverylocality>" +
			            "<deliverypostcode>WD3 3LD</deliverypostcode>" +
			            "<deliverycountrycode>GB</deliverycountrycode>" +
			            "<relabelledas>1550 8002 446 245 8</relabelledas>" +
			            "<yellowcardnumber>35465072</yellowcardnumber>" +
			            "<parcelcode>15502027343989*19752</parcelcode>" +
			            "<trackingevents>" +		                			               			                			                
			                "<trackingevent>" +
			                    "<date>2022-01-28T10:31:00Z</date>" +
			                    "<code>0</code>" +
			                    "<type>PARCEL DATA</type>" +
			                    "<description>Customer data received</description>" +
			                    "<locality>Sheffield</locality>" +
			                    "<countrycode>GB</countrycode>" +
			                    "<imagekey></imagekey>" +
			                "</trackingevent>" +
			            "</trackingevents>" +
			        "</trackingdetail>" +
			    "</trackingdetails>" +
			"</trackingresponse>"
	;
}
