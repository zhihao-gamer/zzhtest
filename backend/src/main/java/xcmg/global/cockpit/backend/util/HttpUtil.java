package xcmg.global.cockpit.backend.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * 
 * <p>ClassName: HttpUtil</p>
 * <p>Description: </p>
 * <p>Author: Lenovo</p>
 * <p>Date: 2023年11月27日</p>
 */
public class HttpUtil {
    public static String post(String url,Map<String,String> headers,Map<String,String> param) throws Exception{
        //采用绕过验证的方式处理https请求 
        SSLContext sslcontext = createIgnoreVerifySSL();
        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)  
                .register("https", new SSLConnectionSocketFactory(sslcontext))  
                .build();  
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
            HttpClients.custom().setConnectionManager(connManager);
            
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();  
        String strResult="";
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            //指定报文头Content-type、User-Agent
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            if(null!=headers&&headers.size()>0){
                for(Map.Entry<String, String> entry:headers.entrySet()){
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if(null!=param&&param.size()>0){
                List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                for(Map.Entry<String, String> entry:param.entrySet()){
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));//请求参数
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8"); 
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);
            if(httpResponse != null){ 
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(null!=httpResponse){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strResult;
    }
    
    public static String get(String url) throws Exception{
      //采用绕过验证的方式处理https请求 
        SSLContext sslcontext = createIgnoreVerifySSL();
        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
                .register("http", PlainConnectionSocketFactory.INSTANCE)  
                .register("https", new SSLConnectionSocketFactory(sslcontext))  
                .build();  
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
            HttpClients.custom().setConnectionManager(connManager);
            
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();  
        
        
        String strResult="";
        CloseableHttpResponse httpResponse = null;
        HttpGet httpGet = new HttpGet(url);
      
        try {
           httpResponse = httpClient.execute(httpGet);
           if(httpResponse != null){ 
               if (httpResponse.getStatusLine().getStatusCode() == 200) {
                   strResult = EntityUtils.toString(httpResponse.getEntity());
               } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                   strResult = "Error Response: " + httpResponse.getStatusLine().toString();
               } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                   strResult = "Error Response: " + httpResponse.getStatusLine().toString();
               } else {
                   strResult = "Error Response: " + httpResponse.getStatusLine().toString();
               } 
           }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }
    /** 
    * 绕过验证 
    *   
    * @return 
    * @throws NoSuchAlgorithmException  
    * @throws KeyManagementException  
    */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");//如果无法访问，就换成TLS
        //实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager(){

            @Override
            public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                
            }

            @Override
            public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            } 
            
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
}
