package com.ceva.cevatrack.wsmicrolise;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.util.ApplicationGlobalConfig;
import org.omg.DynamicAny.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class WSMicroliseImage {
	private static final Logger Log = LoggerFactory.getLogger(WSMicroliseImage.class);	
	
	private static String user = null;
	private static String psw = null;
	private static String url_env = null;
	private static Proxy proxy = null;
	private static boolean hasProxy = false;
	
	public WSMicroliseImage(){
		Log.info("-- Creating object");
		initEnvironment();
	}
	
	public static void initEnvironment(){
		Properties systemProps = System.getProperties();		
		if (systemProps.get("environment.code") != null){
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-STAGE")){
				Log.info(" ----------- We are in the Production environment --> " + systemProps.get("environment.code"));//OK
				user = ApplicationGlobalConfig.IMG_TEST_USER;
				psw = ApplicationGlobalConfig.IMG_TEST_PWS;
				url_env = ApplicationGlobalConfig.IMG_TEST_URI;		
				hasProxy = true;
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.GDC_PROXY, ApplicationGlobalConfig.GDC_PROXY_PORT));
				//this.environment = "LOCAL";
			}
			
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("LOCAL")){	
				Log.info(" ----------- We INDEED are in the LOCAL environment --> " + systemProps.get("environment.code"));//OK
				user = ApplicationGlobalConfig.IMG_TEST_USER;
				psw = ApplicationGlobalConfig.IMG_TEST_PWS;
				url_env = ApplicationGlobalConfig.IMG_TEST_URI;	
				hasProxy = false;
			}				
			
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-DEVL")){
				Log.info(" ----------- We INDEED are in the DEVELOPMENT environment --> " + systemProps.get("environment.code"));//OK
				user = ApplicationGlobalConfig.IMG_TEST_USER;
				psw = ApplicationGlobalConfig.IMG_TEST_PWS;
				url_env = ApplicationGlobalConfig.IMG_TEST_URI;
				hasProxy = true;
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.DEV_PROXY_1, ApplicationGlobalConfig.DEV_PROXY_PORT));
			}
			
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-TEST")){
				Log.info(" ----------- We INDEED are in the TEST environment --> " + systemProps.get("environment.code"));//OK
				user = ApplicationGlobalConfig.IMG_TEST_USER;
				psw = ApplicationGlobalConfig.IMG_TEST_PWS;
				url_env = ApplicationGlobalConfig.IMG_TEST_URI;
				hasProxy = true;
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.GDC_PROXY, ApplicationGlobalConfig.GDC_PROXY_PORT));
			}
						
		}
	}

	public static synchronized byte[] callWS(String imageRef) {
		Log.info(" -- WEBSERVICE STARTED --");		
		String userAndPassword = String.format("%s:%s", user, psw);
		String encoding = Base64.getEncoder().encodeToString(userAndPassword.getBytes(StandardCharsets.UTF_8));
		String basicAuth = "Basic " + encoding;
		String finalResult = null;
		byte[] finalResultbytes = null;
		OutputStream out = null;
		int respCode = -1;
		boolean isSuccess = false;
		URL url = null;
		String soapNameSpace = null;
		HttpsURLConnection httpURLConnection = null;
		BufferedReader in = null;		
		try {
			//SSLContext sc = SSLContext.getInstance("TLSv1.2");
			//sc.init(null, null, new java.security.SecureRandom());
			Log.info("URL = " + url_env + imageRef);
			url = new URL(url_env + imageRef);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty ("Authorization", basicAuth);
			if (hasProxy){
				Log.info("-- It has proxy..");
				httpURLConnection = (HttpsURLConnection) url.openConnection(proxy);
			}else{
				httpURLConnection = (HttpsURLConnection) url.openConnection();
			}
			Log.info(" -- WS 1");						
			// Set up the connection object (https://stackoverflow.com/questions/9943351/httpsurlconnection-and-keep-alive)
			//httpURLConnection = (HttpsURLConnection) url.openConnection();
			//httpURLConnection.setSSLSocketFactory(sc.getSocketFactory());
			//httpURLConnection.setSSLSocketFactory(new SSLSocketFactoryExtended()); // <- provide support for old TLS uklsf003
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setUseCaches(false);
			
			httpURLConnection.setRequestProperty("Authorization", basicAuth);
			httpURLConnection.setRequestProperty("Connection", "keep-alive");
			httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
			httpURLConnection.setRequestProperty("Content-Type", "text/xml");
			httpURLConnection.setReadTimeout(30 * 1000);
			httpURLConnection.setConnectTimeout(10 * 1000);
			httpURLConnection.setRequestProperty("Accept", "*/*");
			httpURLConnection.setFollowRedirects(false);
			httpURLConnection.connect();
			out = httpURLConnection.getOutputStream();
			//No body needed
			//out.write(testPUtBody.getBytes(StandardCharsets.UTF_8));
			out.flush();
			// read the response
			int resp = httpURLConnection.getResponseCode(); 
			if (resp != 200) {
				Log.error("-- ERROR IN WEB SERVICE: " + resp);
				try (InputStream estr = httpURLConnection.getErrorStream()) {
					if (estr != null){
						finalResult = returnStringFromStream(estr);
					}
					Log.error("-- Error Stream: " + finalResult);					
				}
				return null;
			} else {
				Log.info("-- WEBSERVICE CODE: " + respCode + " ,SUCCESS: " + resp);
				try (InputStream str = httpURLConnection.getInputStream()) {
					// String result = new BufferedReader(new
					// InputStreamReader(str))
					// .lines().collect(Collectors.joining("\n"));
					finalResultbytes = returnFromStream(str);
					if (finalResultbytes != null){
						Log.info("-- Result WS returned: " + finalResultbytes.length);
					}
					return finalResultbytes;
				}
			}
		} catch (Exception e) {
			Log.error(" -- Error captured: ", e);
		} finally {
			if (out != null) {
				out = null;
			}
			if (in != null) {
				in = null;
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
		return finalResultbytes;
	}

	private static byte[] returnFromStream(InputStream str) throws Exception {
		byte[] resutByte = null;
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		for (int length; (length = str.read(buffer)) != -1;) {
			result.write(buffer, 0, length);
		}
		if (result != null){
			resutByte = result.toByteArray();
		}
		return resutByte;
	}
	
	private static String returnStringFromStream(InputStream str) throws Exception{
        String finalResult = null;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = str.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        finalResult = result.toString("UTF-8");
        //Log.i(TAG, " -- Converted from inputStr: " + finalResult);
        return finalResult;
    }

}
