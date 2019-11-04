package com.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.app.crawler.CFilter;
import com.bobo.base.BaseClass;

/**
 * @Description: TODO
 * @date 2019年6月19日 下午2:45:05
 * @ClassName: HttpUtil
 */
public class HttpUtil extends BaseClass {

    public static String doGetRequest(String url) {
        return doGetRequest(url, new BasicCookieStore(), "UTF-8");
    }


    public static String doGetRequest(String url, String charset) {
        return doGetRequest(url, new BasicCookieStore(), charset);
    }

    public static String doGetRequest(String url, CookieStore cookieStore) {
        return doGetRequest(url, cookieStore, "UTF-8");
    }

    public static String doGetRequest(String url, CookieStore cookieStore, String charset) {
    	 LOGGER.info("请求地址:【{}】", url);
    	 CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            
            HttpGet get = new HttpGet(url);
            get.addHeader("Accept", "*/*");
            get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
            get.addHeader("Connection", "keep-alive");
            get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (Exception e) {
            LOGGER.error("【{}】下载失败,错误信息:【{}】", url, e);
            try {
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        }finally {
        	try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return null;
    }

    public static HttpResult doPostGetgetHttpResult(String url, Map<String, String> headParamsMap, Map<String, String> formMap) {
        CookieStore cookieStore = new BasicCookieStore();
        return doPostGetgetHttpResult(url, headParamsMap, formMap, "UTF-8", cookieStore);
    }

    public static HttpResult doPostGetgetHttpResult(String url, Map<String, String> headParamsMap, Map<String, String> formMap, CookieStore cookieStore) {
        return doPostGetgetHttpResult(url, headParamsMap, formMap, "UTF-8", cookieStore);
    }

    public static HttpResult doPostGetgetHttpResult(String url, Map<String, String> headParamsMap, Map<String, String> formMap, String charset, CookieStore cookieStore) {
        LOGGER.info("POST请求地址:【{}】,参数：【{}】", url, formMap);
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        HttpResult result = new HttpResult();
        try {
        	if (Objects.nonNull(cookieStore)){
				httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			}else{
				httpClient = HttpClients.custom().build();
			}

            httpPost = new HttpPost(url);
            // 设置请求体参数
            List<NameValuePair> list = new ArrayList<>();
            Iterator<Entry<String, String>> iterator = formMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }

            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }

            // 设置请求头通用信息
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

            Set<Entry<String, String>> entrySet = headParamsMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    String content = EntityUtils.toString(response.getEntity(), charset);
                    result.setCookies(cookieStore.getCookies());
                    result.setDocument(Jsoup.parse(content));
                    result.setCookieStore(cookieStore);
                }
            }
        } catch (Exception e) {
            LOGGER.error("【{}】下载失败,错误信息:【{}】", url, e);
        } finally {
//            httpPost.abort();
            if (Objects.nonNull(httpClient)) {
            	try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        return result;
    }

    public static void doGetByUrlSsl(String url, CFilter<InputStream> cfilter) {
        LOGGER.info("SSL请求地址:【{}】", url);
        CloseableHttpClient httpClient = createSSLClientDefault();
        HttpGet get = new HttpGet(url);
        get.addHeader("Accept", "*/*");
        get.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        get.addHeader("Connection", "keep-alive");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

        HttpResponse response;
        try {
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                if (null != cfilter) {
                    cfilter.excute(is);
                }
            }
        } catch (Exception e) {
            LOGGER.error("【{}】下载失败,错误信息:【{}】", url, e);
        }
    }

    public static CloseableHttpClient createSSLClientDefault() {

        try {

            @SuppressWarnings("deprecation")
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }

        return HttpClients.createDefault();
    }

    public static class HttpResult {
        private List<Cookie> cookies = null;
        private Document document = null;
        private CookieStore cookieStore;

        public List<Cookie> getCookies() {
            return cookies;
        }

        public void setCookies(List<Cookie> cookies) {
            this.cookies = cookies;
        }

        public Document getDocument() {
            return document;
        }

        public void setDocument(Document document) {
            this.document = document;
        }

        public CookieStore getCookieStore() {
            return cookieStore;
        }

        public void setCookieStore(CookieStore cookieStore) {
            this.cookieStore = cookieStore;
        }

    }
}
