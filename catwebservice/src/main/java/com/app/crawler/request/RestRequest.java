package com.app.crawler.request;

import com.app.crawler.request.client.DefaultHttpRequestRetryHandler;
import com.bobo.base.BaseClass;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class RestRequest extends BaseClass {
	private PoolingHttpClientConnectionManager clientConnectionManager;
	private HttpClientBuilder httpClient = null;

	private HttpClientContext context = HttpClientContext.adapt(new BasicHttpContext());

	private String charset = "UTF-8";
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * // 创建连接的最长时间 // 从连接池中获取到连接的最长时间 // 数据传输的最长时间10s
	 */
	RequestConfig config = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(500)
			.setSocketTimeout(3 * 1000).build();

	public RestRequest() {
		this.clientConnectionManager = new PoolingHttpClientConnectionManager();
		clientConnectionManager.setValidateAfterInactivity(3 * 1000);// 提交请求前测试连接是否可用

		httpClient = HttpClientBuilder.create().setConnectionManager(clientConnectionManager).setRetryHandler(new DefaultHttpRequestRetryHandler());
		
	}

	
	public byte[] doGetByte(String url) {
		HttpGet request = new HttpGet(url);
		this.addRequestHeaders(request);
		
		byte[] result = null;
		CloseableHttpResponse httpResponse = null;
		InputStream inputStream = null;
		try {
			httpResponse = httpClient.build().execute(request, context);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				this.forEachCookies(httpResponse);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				if (Objects.nonNull(inputStream)) {
					return this.toByteArray(inputStream);
				}
			}

		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		} finally {
			// 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
			request.reset();
			request.releaseConnection();
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(httpResponse);
		}
		return result;
	}

	public String doGet(String url) {
		HttpGet request = new HttpGet(url);
		this.addRequestHeaders(request);
		String result = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.build().execute(request, context);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				this.forEachCookies(httpResponse);
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, charset);
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			// 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
			request.reset();
			request.releaseConnection();
			IOUtils.closeQuietly(httpResponse);
		}
		return result;
	}

	public String doPost(String url, HttpEntity entity) {
		HttpPost request = new HttpPost(url);
		this.addRequestHeaders(request);
		String result = null;
		CloseableHttpResponse httpResponse = null;
		try {
			if (Objects.nonNull(entity)) {
				request.setEntity(entity);
			}

			httpResponse = httpClient.build().execute(request, context);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				this.forEachCookies(httpResponse);
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity, charset);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
			request.reset();
			request.releaseConnection();
			IOUtils.closeQuietly(httpResponse);
		}
		return result;
	}

	public byte[] doPostByte(String url, HttpEntity entity)  {
		HttpPost request = new HttpPost(url);
		this.addRequestHeaders(request);
		byte[] result = null;
		CloseableHttpResponse httpResponse = null;
		InputStream inputStream = null;
		try {
			if (Objects.nonNull(entity)) {
				request.setEntity(entity);
			}

			httpResponse = httpClient.build().execute(request, context);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				this.forEachCookies(httpResponse);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				if (Objects.nonNull(inputStream)) {
					return this.toByteArray(inputStream);
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 当不再需要HttpClient实例时,关闭连接管理器以确保释放所有占用的系统资源
			request.reset();
			request.releaseConnection();
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(httpResponse);
		}
		return result;
	}

	private void forEachCookies(CloseableHttpResponse httpResponse) {
		CookieStore cookieStore = new BasicCookieStore();
		Header[] headers = httpResponse.getAllHeaders();
		for(int index = 0; index < headers.length; index ++) {
			Header header = headers[index];
			String name = header.getName();
			String value = header.getValue();
			Cookie cookie = new BasicClientCookie(name,value);
			cookieStore.addCookie(cookie);
		}
	}
	
	private void addRequestHeaders(HttpRequestBase request) {
		request.addHeader("Connection", "close");  
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		request.addHeader("Connection", "keep-alive");
		request.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
	}

	public byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}
}
