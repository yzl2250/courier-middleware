package courier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientConnection {

    public CloseableHttpClient httpClient = HttpClients.createDefault();

    public void close() throws IOException {
        httpClient.close();
    }
    
    public void bypassSslCertCheck(List<String> errorMsgList) {
    	try {
	    	final SSLContext sslContext = new SSLContextBuilder()
	    	        .loadTrustMaterial(null, (x509CertChain, authType) -> true)
	    	        .build();
	
	    	httpClient = HttpClientBuilder.create().setSSLContext(sslContext).setConnectionManager(
	    	        		new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
	    	                                .register("http", PlainConnectionSocketFactory.INSTANCE)
	    	                                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
	    	                                .build()
	    	                                )
	    	        		).build();
    	} catch (Exception ex) {
    		errorMsgList.add(ex.getMessage());
    	}
    }

    public void sendGet() throws Exception {

//        HttpGet request = new HttpGet("https://www.google.com/search?q=mkyong");
        HttpGet request = new HttpGet("https://expired.badssl.com/");

        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
        bypassSslCertCheck(new ArrayList<>());	    

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        }

    }	
    
    public String sendGetWithParams(URIBuilder builder, List<String> errorMsgList) throws Exception {    	    	
    	HttpGet request = new HttpGet(builder.build());
    	String result = "";    
    	
    	bypassSslCertCheck(errorMsgList);
    	try {
    		CloseableHttpResponse response = httpClient.execute(request);
    		// Get HttpResponse Status
    		System.out.println(response.getStatusLine().toString());
    		
    		HttpEntity entity = response.getEntity();
    		Header headers = entity.getContentType();
    		System.out.println(headers);
    		
    		if (entity != null) {
    			// return it as a String
    			result = EntityUtils.toString(entity);
    			System.out.println(result);
    		}
    		
    	} catch (Exception ex) {
    		errorMsgList.add(ex.getMessage());
        } finally {
        	try {
        		httpClient.close();
        	} catch (Exception ex) {
        		errorMsgList.add(ex.getMessage());
        	}        	
        }   
    	
    	return result;
    }	
	
    public void sendPost() throws Exception {

        HttpPost post = new HttpPost("https://www.jtexpress.my/function/order.php");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", "abc"));
        urlParameters.add(new BasicNameValuePair("password", "123"));
        urlParameters.add(new BasicNameValuePair("custom", "secret"));
      
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }		
    
    public String sendPostWithFormData(MultipartEntityBuilder builder, String url, List<String> errorMsgList) {
    	HttpPost httpPost = new HttpPost(url);
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        String responseStr = "";
        
        bypassSslCertCheck(errorMsgList);
        try {
        	CloseableHttpResponse response = httpClient.execute(httpPost);
        	HttpEntity responseEntity = response.getEntity();
			responseStr = EntityUtils.toString(responseEntity, "UTF-8");
			System.out.println("Post return result" + responseStr);
        } catch (Exception ex) {
        	errorMsgList.add(ex.getMessage());
        } finally {
        	try {
        		httpClient.close();
        	} catch (Exception ex) {
        		errorMsgList.add(ex.getMessage());
        	}
        	
        }
        return responseStr;
    }	    
	
}
