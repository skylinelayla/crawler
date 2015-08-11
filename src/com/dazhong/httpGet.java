package com.dazhong;
/*获得网页，
 * 最终返回网页源代码变量名responseBody
 * 
 * 
 */

import java.io.IOException;
import java.sql.Connection;
 





import javax.swing.JFrame;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.frame.DazhongFrame;
import com.frame.SinaFrame;
 
public class httpGet {
	public static String responseBody;
 
	DazhongFrame jf1;
	SinaFrame jf2;
	
	
	public final static void getByString(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //设置超时时间
      RequestConfig requestConfig = RequestConfig.custom()
        		 .setSocketTimeout(100000)
        		 .setConnectTimeout(100000)
        		 .build();
       
        try {
            HttpGet httpget = new HttpGet(url);
        
            
            System.out.println("executing request " + httpget.getURI());
 
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
 
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            responseBody = httpclient.execute(httpget, responseHandler);//网页下载存储变量
        
            //print the content of the page
    /* System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
       
      */     // parsePage.parseFromString(responseBody,conn);
             
        } finally {
            httpclient.close();
        }
    }
    public String getResult(){
    	return responseBody;
    }
}



