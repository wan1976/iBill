package net.toeach.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;

/**
 * Http工具类<br/>
 * net.toeach.common.utils.HttpUtil
 * @author 万云  <br/>
 * @version 1.0
 * @date 2015-3-5 下午3:09:27
 */
public class HttpUtil {
	private static final int REQUEST_TIMEOUT = 10000;
	private static final int SO_TIMEOUT = 10000;
	
	private String url;// URL地址
	private Header[] headers;// 头信息

	public HttpUtil(String url) {
		this.url = url;
	}
	
	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}
	
	/**
	 * Get方式提交请求<br/>
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public String get() throws ClientProtocolException, IOException, IllegalStateException {
		HttpGet get = new HttpGet(url);
		HttpResponse response = execute(get);
		return getTextResponse(response);
	}
	/**
	 * POST方式提交请求<br/>
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public HttpResponse post(Map<String, String> params) throws ClientProtocolException, IOException, IllegalStateException {
		HttpResponse response = postMethod(params);
		return response;
	}

	private HttpResponse postMethod(Map<String, String> params) throws ClientProtocolException, IOException {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (params != null) {
			Iterator<String> iter = params.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = (String) params.get(key);
				NameValuePair pair = new BasicNameValuePair(key, value);
				pairs.add(pair);
			}
		}

		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(pairs));
		return execute(post);
	}
	/**
	 * 提交请求<br/>
	 * @param req
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse execute(HttpUriRequest req) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.connection.timeout", REQUEST_TIMEOUT);
		client.getParams().setParameter("http.socket.timeout", SO_TIMEOUT);
		DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(0, false);	
		client.setHttpRequestRetryHandler(retryHandler);
		if(headers != null) {
			req.setHeaders(headers);
		}
		return client.execute(req);
	}
	
	/**
	 * 获取返回的内容 <br/>
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public String getTextResponse(HttpResponse response) throws IOException, IllegalStateException {
		if ((response == null) || (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		HttpEntity httpEntity = response.getEntity();
		InputStream inputStream = httpEntity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
}
