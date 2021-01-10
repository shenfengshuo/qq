package org.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

public class HttpUtil<T> {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private Map respHeaders = new HashMap();
	private T respContent;
	private Integer statusCode;
	private HttpHost proxy;
	private String charset = "UTF-8";

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setProxy(String hostname, Integer port) {
		this.proxy = new HttpHost(hostname, port.intValue());
	}

	public Integer getStatusCode() {
		return this.statusCode;
	}

	public HttpUtil() {
	}

	public HttpUtil(String charset) {
		this.charset = charset;
	}

	public Map<String, String> getRespHeaders() {
		return this.respHeaders;
	}

	public T getRespContent() {
		return (T) this.respContent;
	}

	public void doGet(String url, Map params) {
		doGet(url, null, params);
	}

	private Map<String, Object> json2Map(String json) {
		JSONObject o = JSONObject.parseObject(json);
		Map<String, Object> m = Maps.newHashMap();
		Iterator<String> keys = o.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			m.put(key, o.get(key));
		}
		return m;
	}

	public HttpResponse doGet(String url, Map<String, String> headers, Map<String, Object> params) {

		HttpGet httpget = null;
		if ((null != params) && (params.size() > 0)) {
			StringBuffer sb = new StringBuffer();
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = params.get(key);
				sb.append(key);
				sb.append("=");
				try {
					sb.append(java.net.URLEncoder.encode(String.valueOf(value), getCharset()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sb.append("&");
			}
			String paramStr = sb.substring(0, sb.length() - 1);
			System.out.println("paramStrqqqq:  " + paramStr);
			if ((url.contains("&")) || (url.contains("?"))) {
				httpget = new HttpGet(url + "&" + paramStr);
			} else {
				httpget = new HttpGet(url + "?" + paramStr);
			}
		} else {
			httpget = new HttpGet(url);
		}
		httpget = (HttpGet) addHeaders(httpget, headers);
		return sendRequest(httpget, params, 0);
	}

	private HttpRequestBase addHeaders(HttpRequestBase request, Map headers) {
		Iterator<String> it;

		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				request.addHeader(key, String.valueOf(headers.get(key)));
			}
		}
		return request;
	}

	
	private synchronized HttpResponse sendRequest(HttpRequestBase request, Map params, int type) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = null;
		try {
			String method = type == 0 ? "get" : "post";
			
			logger.info(String.format("-------------  http %s start --------", new Object[] { method }));
			logger.info("use charset: " + getCharset());

			if (this.proxy != null) {
				httpclient.getParams().setParameter("http.route.default-proxy", this.proxy);
			}

			logger.info("request uri: " + request.getURI());
			logger.info("request headers: " + Lists.newArrayList(request.getAllHeaders()));
			logger.info("request params: " + params);
			response = httpclient.execute(request);

			HttpEntity entity = response.getEntity();
			Header[] respHeaderArray = response.getAllHeaders();
			for (Header head : respHeaderArray) {
				this.respHeaders.put(head.getName(), head.getValue());
			}
			
			this.statusCode = Integer.valueOf(response.getStatusLine().getStatusCode());
			this.respContent = formatResponse(entity);

			logger.info("response code: " + this.statusCode);
			logger.info("response headers: " + Lists.newArrayList(response.getAllHeaders()));
			logger.info("response body: " + this.respContent);
			logger.info(String.format("------http %s end --------",new Object[] { method }));
		} catch (ClientProtocolException e) {
			this.statusCode = Integer.valueOf(502);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private synchronized HttpResponse sendJSON(HttpPost request) {
		HttpResponse response = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			logger.info("------------  http post json start --------------");
			logger.info("use charset: " + getCharset());

//			if (this.proxy != null) {
//				httpclient.getParams().setParameter("http.route.default-proxy", this.proxy);
//			}

			logger.info("request uri: " + request.getURI());
			logger.info("request headers: " + Lists.newArrayList(request.getAllHeaders()));
			logger.info("request json string: " + org.apache.commons.io.IOUtils.toString(request.getEntity().getContent(), this.charset));
			logger.info("request0000   : " + request);
			
			response = httpclient.execute(request);

			HttpEntity entity = response.getEntity();
			Header[] respHeaderArray = response.getAllHeaders();
			for (Header head : respHeaderArray) {
				this.respHeaders.put(head.getName(), head.getValue());
			}
			this.statusCode = Integer.valueOf(response.getStatusLine().getStatusCode());
			this.respContent = formatResponse(entity);

			logger.info("response code: " + this.statusCode);
			logger.info("response headers: " + Lists.newArrayList(response.getAllHeaders()));
			logger.info("response body: " + this.respContent);
			logger.info("response: " + response);
			logger.info("response: " + response.getFirstHeader("location"));
			
	            
			logger.info("------------  http  post json end --------");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private synchronized HttpResponse sendTaskJSON(HttpPost request) {
		HttpResponse response = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			logger.info("------------  http post json start --------------");

			String task = org.apache.commons.io.IOUtils.toString(request.getEntity().getContent(), this.charset);
			logger.info("task task: " + task);

			response = httpclient.execute(request);

			HttpEntity entity = response.getEntity();
			Header[] respHeaderArray = response.getAllHeaders();
			for (Header head : respHeaderArray) {
				this.respHeaders.put(head.getName(), head.getValue());
			}
			this.statusCode = Integer.valueOf(response.getStatusLine().getStatusCode());
			this.respContent = formatResponse(entity);

			logger.info("response code: " + this.statusCode);
			logger.info("response headers: " + Lists.newArrayList(response.getAllHeaders()));
			logger.info("response body: " + this.respContent);
			logger.info("response: " + response);
			logger.info("response: " + response.getFirstHeader("location"));
			

			String newuri  =  response.getFirstHeader("location").getValue();
			
			HttpPost httpPost = new HttpPost(newuri);
	            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

	            httpPost.setEntity(null);
				logger.info("request1111   : " + request);
				logger.info("request headers111 : " + Lists.newArrayList(httpPost.getAllHeaders()));

				
	            response = httpclient.execute(httpPost);
	            response.getEntity().getContent().close();//关闭结果集
	            
	            
	            String newuri2 = response.getFirstHeader("location").getValue();

	            HttpPost httpget = new HttpPost(newuri2);
	            response = httpclient.execute(httpget);

				HttpResponse response2 = response;
				response.getEntity().getContent().close();//关闭结果集

	            
				String url = "http://fspre.ccccing.com/fogrs-web/api/v1/icc/restart";
				HttpPost httpPost2 = new HttpPost(url);
				
				
	            httpPost2.setHeader("Content-Type", "application/json");
	            httpPost2.setHeader("cookie", response2.getFirstHeader("Set-Cookie").getValue());
	            
//	            String js2 = "{\"taskId\":\"TASK20191227922\"}";
	            StringEntity se2 = new StringEntity(task);
//	            se.setContentType("text/json");

	            httpPost2.setEntity(se2);
	            HttpClient httpclient2 = new DefaultHttpClient();
	            response = httpclient2.execute(httpPost2);
				
	            logger.info("response: " + response);
				
				
				
				
	            
	            
			logger.info("------------  http  post json end --------");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private T formatResponse(HttpEntity entity) {
		try {
			if ((this.respHeaders != null) && (this.respHeaders.containsKey("Content-Type"))
					&& (this.respHeaders.get("Content-Type").toString().startsWith("image"))) {
				return (T) EntityUtils.toByteArray(entity);
			}
			return (T) EntityUtils.toString(entity, this.charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void doGet(String url) throws Exception {
		doGet(url, null);
	}

	private List<BasicNameValuePair> mapToPairs(Map parmas) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		Iterator i;
		if (parmas != null) {
			Set keys = parmas.keySet();
			for (i = keys.iterator(); i.hasNext();) {
				String key = String.valueOf(i.next());
				pairs.add(new BasicNameValuePair(key, String.valueOf(parmas.get(key))));
			}
		}
		return pairs;
	}

	public HttpResponse postTaskJson(String url, Map<String, String> headers, JSONObject json) throws UnsupportedEncodingException {
		HttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost = (HttpPost) addHeaders(httpPost, headers);
		StringEntity entity = new StringEntity(json.toString());
		entity.setContentEncoding(this.charset);
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		response = sendTaskJSON(httpPost);
		return response;
	}
	
	public HttpResponse postJson(String url, Map<String, String> headers, JSONObject json) throws UnsupportedEncodingException {
		HttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost = (HttpPost) addHeaders(httpPost, headers);
		StringEntity entity = new StringEntity(json.toString());
		entity.setContentEncoding(this.charset);
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		response = sendJSON(httpPost);
		return response;
	}

	private MultipartEntityBuilder toMultipartEntityBuilder(Map params, Map files) {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setCharset(Charset.forName(this.charset));
		ContentType contentType = ContentType.create("text/plain");
		if (null != params) {
			Iterator<String> it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = String.valueOf(params.get(key));
				builder.addPart(key, new StringBody(value, contentType));
			}
		}
		if (null != files) {
			Iterator<String> it1 = files.keySet().iterator();
			while (it1.hasNext()) {
				String key = it1.next();
				String value = String.valueOf(files.get(key));
				builder.addPart(key, new FileBody(new File(value), contentType));
			}
		}
		return builder;
	}

	private HttpPost setEntity(HttpPost httpPost, List<BasicNameValuePair> pairs) {
		try {
			httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(pairs, this.charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return httpPost;
	}

	public HttpResponse doPost(String url, Map<String, String> headers, Map<String, Object> params) {
		HttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		httpPost = (HttpPost) addHeaders(httpPost, headers);
		List<BasicNameValuePair> pairs = mapToPairs(params);
		httpPost = setEntity(httpPost, pairs);
		sendRequest(httpPost, params, 1);
		return response;
	}

	public void upload(String url, String localFile) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = org.apache.http.impl.client.HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(url);

			FileBody bin = new FileBody(new File(localFile));

			StringBody userName = new StringBody("Scott", ContentType.create("text/plain", Consts.UTF_8));

			StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));

			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("userName", userName)
					.addPart("pass", password).build();

			httpPost.setEntity(reqEntity);

			response = httpClient.execute(httpPost);

			System.out.println("The response value of token:" + response.getFirstHeader("token"));

			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				System.out.println("Response content length: " + resEntity.getContentLength());

				System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
			}

			EntityUtils.consume(resEntity);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	reqMap.put("taskId", "TASK20191222894");
//	post("http://fspre.ccccing.com/fogrs-web/api/v1/icc/restart", reqMap);
//	
	

//	public static void main(String[] args) throws Exception {
//		HttpUtil<byte[]> util = new HttpUtil<byte[]>();
//
//		String url = "http://fspre.cccing.com/fogrs-web/api/v1/icc/restart";
//		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		headers.put("Content-Type", "application/json");
//		headers.put("User-Agent",
//				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
//		Map<String, Object> body = new java.util.LinkedHashMap<String, Object>();
//		body.put("taskId", "TASK20190419426");
//		body.put("uuid", "4ff3b951-1f20-441f-a19c-ebf5137778f47b");
//		JSONObject taskJson = new JSONObject();
//		taskJson.put("id", "20200109934");
//
//		util.postTaskJson(url, headers, taskJson);
	
		
	    
	    
		
//	}
}