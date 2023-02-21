package com.jsrabk.reference.app.common.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

//import org.apache.commons.io.IOUtils;


public class SiteProbe {
	
	private String siteURL = null;
	private String redirectedSiteURL = null;
	private int responseCode = ProjectConstant.defaultResponseCode;
	private int maxRedirectURLDepth = ProjectConstant.maxRedirectURLDepth;
	private int connectionTimeoutInMs = ProjectConstant.connectionTimeoutInMs;
	private boolean isValidURL = false;
	private boolean testCompleted = false;
	
	public SiteProbe() {
		
	}

	public SiteProbe(String siteURL) {
		this.siteURL = siteURL;
	}
	
	public void runTest() {		
		runTest(this.siteURL);
	}
	
	public void runTest(String siteURL) {
		if(siteURL!=null) {
			if(!siteURL.startsWith("http")) {
				siteURL = "http://" + siteURL;
				setSiteURL(siteURL);
			}
			setRedirectedSiteURL(getDirectUrl(siteURL, 0));
		}else {
			System.out.println("Please input an URL");
            setIsValidURL(false);
            setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST);
		}
		this.testCompleted = true;
	}

    protected String getDirectUrl(String link, int redirectURLDepth){
    	
        String resultUrl = link;

    	HttpURLConnection connection = null;
    	int responseCode = HttpsURLConnection.HTTP_INTERNAL_ERROR;

        try {

			//Start: dummy https certificate
			//Reference: http://www.nakov.com/blog/2009/07/16/disable-certificate-validation-in-java-ssl-connections/        	
            TrustManager[] trustAllCerts = new TrustManager[] {
        		new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };
	 
	        // Install the all-trusting trust manager
	        SSLContext sc;
			try {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
		        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	 
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = HttpsURLConnection.getDefaultHostnameVerifier();
	        
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        //End: dummy https certificate         	
        	
        	if(link.startsWith("https")){
        		connection = (HttpsURLConnection) new URL(link).openConnection();
        	}else{
        		connection = (HttpURLConnection) new URL(link).openConnection();
        	}
        	
        	System.out.println("link................"+link);
        	
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(connectionTimeoutInMs);
            connection.connect();

            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM) {

                String locationUrl = connection.getHeaderField("Location");

                if (locationUrl != null && locationUrl.trim().length() > 0) {
                	locationUrl = locationUrl.trim();
                    //IOUtils.close(connection);
                    if(redirectURLDepth < getMaxRedirectURLDepth()){
                    	if(!locationUrl.startsWith("http")){
                    		if(locationUrl.startsWith("www"))
                    		{
                    			locationUrl = "http://"+locationUrl;
                    		}else{
	                    		if(locationUrl.startsWith("/") || link.endsWith("/")){
	                    			locationUrl = link + locationUrl;
	                    		}else{                    			
	                    			locationUrl = link + "/" +locationUrl;
	                    		}
                    		}
                    	}
                    	
                    	resultUrl = getDirectUrl(locationUrl.trim(), ++redirectURLDepth);
                        if(resultUrl.length()==0){
                        	resultUrl = link;
                        }
                	}else{
                    	resultUrl = link;
                    }
                }
            }else if (responseCode == HttpURLConnection.HTTP_BAD_GATEWAY || responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT ||
            		responseCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT || responseCode == HttpURLConnection.HTTP_GONE ||
            		responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR || responseCode == HttpURLConnection.HTTP_NO_CONTENT ||
            		responseCode == HttpURLConnection.HTTP_BAD_REQUEST || responseCode == HttpURLConnection.HTTP_CONFLICT ||
            		responseCode == HttpURLConnection.HTTP_ENTITY_TOO_LARGE || responseCode == HttpURLConnection.HTTP_LENGTH_REQUIRED ||
            		responseCode == HttpURLConnection.HTTP_NOT_IMPLEMENTED || responseCode == HttpURLConnection.HTTP_RESET ||
            		responseCode == HttpURLConnection.HTTP_UNAVAILABLE || responseCode == HttpURLConnection.HTTP_UNSUPPORTED_TYPE || 
            		responseCode == HttpURLConnection.HTTP_NOT_FOUND) {

            	resultUrl = link;
            	//IOUtils.close(connection);
            	setIsValidURL(false);
            }else{
            	setIsValidURL(true);
            }
        } catch (UnknownHostException  e) {
        	e.printStackTrace();
            resultUrl = link;
            setIsValidURL(false);
            setResponseCode(HttpURLConnection.HTTP_NOT_FOUND);
        } catch (MalformedURLException e) {
        	e.printStackTrace();
            resultUrl = link;
            setIsValidURL(false);
            setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST);
		} catch (IOException e) {
        	e.printStackTrace();
            resultUrl = link;
            setIsValidURL(false);
            setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}
        
        setRedirectedSiteURL(resultUrl);
        setResponseCode(responseCode);
        
        return resultUrl;
    }

    
	public String getSiteURL() {
		return this.siteURL;
	}

	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}

	public String getRedirectedSiteURL() {
		return this.redirectedSiteURL;
	}

	private void setRedirectedSiteURL(String redirectedSiteURL) {
		this.redirectedSiteURL = redirectedSiteURL;
	}

	public int getMaxRedirectURLDepth() {
		return this.maxRedirectURLDepth;
	}

	public void setMaxRedirectURLDepth(int maxRedirectURLDepth) {
		this.maxRedirectURLDepth = maxRedirectURLDepth;
	}

	public int getConnectionTimeoutInMs() {
		return this.connectionTimeoutInMs;
	}

	public void setConnectionTimeoutInMs(int connectionTimeoutInMs) {
		this.connectionTimeoutInMs = connectionTimeoutInMs;
	}

	public int getResponseCode() {
		return this.responseCode;
	}

	private void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public boolean getIsValidURL() {
		return this.isValidURL;
	}

	private void setIsValidURL(boolean isValidURL) {
		this.isValidURL = isValidURL;
	}

	public void printSiteProbeResults() {
		if(testCompleted) {
			System.out.println("Input URL: "+ getSiteURL());
			System.out.println("Redirected URL: "+ getRedirectedSiteURL());
			System.out.println("Is URL Valid: "+ getIsValidURL());
			System.out.println("Http Response code: "+ getResponseCode());
		}else {
			System.out.println("Please run the Test first");
		}
	}

	public JSONObject getSiteProbeResults() {
		JSONObject response = new JSONObject();
		if(this.testCompleted) {
			response.put("status_code", HttpStatus.OK);
			response.put("input_url", ""+getSiteURL());
			response.put("redirected_url", ""+getRedirectedSiteURL());
			response.put("is_url_valid", getIsValidURL());
			response.put("http_response_code", getResponseCode());
		}else {
			   runTest();
			   this.testCompleted = true;
			   response = getSiteProbeResults();
		}
		return response;
	}

	public static void main(String args) {
		String siteURL = ProjectConstant.defaultSiteURL;
		SiteProbe siteProbe = new SiteProbe(siteURL);
		siteProbe.runTest();
		siteProbe.printSiteProbeResults();
	}

}