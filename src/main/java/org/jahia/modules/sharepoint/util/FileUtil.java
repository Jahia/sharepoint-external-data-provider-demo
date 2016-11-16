package org.jahia.modules.sharepoint.util;

import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jahia.modules.sharepoint.SharepointCredentials;

public class FileUtil {

	private  FileUtil() {}
	
	
	public static byte[]  getFile(SharepointCredentials credentials, String urlPart){
		String  sharepointUserName = credentials.getSharepointUsername();
		String  sharepointPassword = credentials.getSharepointPassword();
		Integer sharepointPort     = credentials.getSharepointPortInt();
		String  sharepointHost     = credentials.getSharepointHost();
		String  sharepointProtocol = credentials.getSharepointProtocol();
		String  sharepointUrl      = credentials.getSharepointUrl();
		
		PoolingHttpClientConnectionManager connPool = null;
		CredentialsProvider credsProvider = null;
		RequestConfig config = null;
		CloseableHttpClient httpClient = null;
		HttpClientContext context = null;
		HttpHost target = null;
		HttpHead request1 = null;
		HttpGet  request2 = null; 
		CloseableHttpResponse response1 = null;
		CloseableHttpResponse response2 = null;
		HttpEntity entity = null;
		byte[] result = null;
		
		try{     
		     connPool = new PoolingHttpClientConnectionManager();
		     connPool.setMaxTotal(200);
		     connPool.setDefaultMaxPerRoute(200);
		     
		     // Authentication
		     credsProvider = new BasicCredentialsProvider();
		     credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(sharepointUserName, sharepointPassword, "", ""));
	     
		     config = RequestConfig.custom().setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM)).build();
		     httpClient = HttpClients.custom().setConnectionManager(connPool).setDefaultRequestConfig(config).build();

		     context = HttpClientContext.create();
		     context.setCredentialsProvider(credsProvider);    

		     // You may get 401 if you go through a load-balancer.
		     // To fix this, go directly to one of the sharepoint web server or
		     // change the config. See this article :
		     // http://blog.crsw.com/2008/10/14/unauthorized-401-1-exception-calling-web-services-in-sharepoint/
		     target = new HttpHost(sharepointHost, sharepointPort, sharepointProtocol);
		     context.setCredentialsProvider(credsProvider);

		     // The authentication is NTLM.
		     // To trigger it, we send a minimal http request
		     request1 = new HttpHead("/");
		     response1 = httpClient.execute(target, request1, context);
		     EntityUtils.consume(response1.getEntity());
		     System.out.println("1 : " + response1.getStatusLine().getStatusCode());

		     // The real request, reuse authentication
		     String file = sharepointUrl + "/" + urlPart;  // source
		     request2 = new HttpGet(file);
		     response2 = httpClient.execute(target, request2, context);
		     entity = response2.getEntity();
		     int rc = response2.getStatusLine().getStatusCode();
		     String reason = response2.getStatusLine().getReasonPhrase();
		     if (rc == HttpStatus.SC_OK) {
		     	System.out.println("Getting the file ["+ file + "].");
		        result = EntityUtils.toByteArray(entity);
		        
		        //close all the components
		        sharepointUserName = null;
				sharepointPassword = null;
				sharepointPort     = null;
				sharepointHost     = null;
				sharepointProtocol = null;
			    if (response1 != null ) {
			    	response1.close();
			    	response1 = null;
			    }
			    if (response2 != null ) {
			    	response2.close();
			    	response2 = null;
			    }
			    if (httpClient != null ) {
			    	httpClient.close();
			    	httpClient = null;
			    }	        
		       }
		       else {
		          throw new Exception("Problem while receiving[" + file + "],  reason : " + reason + " httpcode : " + rc);
		       }		     
		    
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return result;
	}

}
