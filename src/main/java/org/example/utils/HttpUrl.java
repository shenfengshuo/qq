package org.example.utils;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
	public final static Map<String, String> keymap = new HashMap<String, String>();
	static {
		keymap.put("pre_xpy1",
				"MIICdgIBADANBgkqhkimA***********************454zotvdVuZRCpjWqjJIapoM/P6pZ18JgERghe+NCximc65Cb6f+Mc/TjtJ12hbrUsnbfRpSMhqGijFTPPvsXxIllGQWiusdX7X3oO1ivfTwjGx8izBYw5MS35xjdGF1+h6iiN/n9QWvZeK85wCgoj/RIcXmXjVJSF/7AgMBAAECgYEAnOA0InSfpb7UgjZDMAAg0edxvYrBg5TNCgknS8b9BvbIJ1+ZkHo3KQi7oDGlQlh2KcnP118DLedaCzPcTW7BV/9AHNhIahH33A0aubflCPG9z5RlBSlBITP2o6sZXG5llHBSp77gOAuYvdYR00vMqf+XLq4APLw3NXlFDOZrIIkCQQD0nFD9BySOJzplGnsTlKwGjOBy4hCdoA7ZsPF7iWXlB2L+9g0nQtNSd60LGrTV/4fDjNovr5b6GryMJNlaHa/tAkEA1gdkYWrjvxB+tm5CU9mmL8zW8X7eshW0/Lm6iih5KoOnYSCGj0ZpKLiKlPPkXfHfVY498NVvz2UCb07kpavChwJACf3cnQlOp/PYQcr8Hgejozzb6jVrwlSIZsIAizBIljtgAtQgI2tqYGL9EYgMvPUMIaO+QdZlT2AKNZtHuZOe3QJABTcm5gzRwe0n+VAUg7CREScfscDSsmo+/82S0x0LXtLYeFBvlvwzQc/FaL3MudFUDOSrc7vvvpEKjyXQAo197wJAa7OK09+jX9se2SI+pUsWkjg0CY96yMIYhpYHZ+STl0dWcMfa/WXgYO8DVspdqkq1b4p6rwsfbqc1wrDRa8Db4g==");
		keymap.put("pre_0001",
				"MIICeAIBADANBgkqhkiG9yyaukPXU43***************h1ZZ3f3MVG2JT5FIxRacHxWarboPPviL4nkZPbU7YNQ8YrAdTzfsv8Est6jOxVXwcTplRcnWKe86j_fVH2LwOLjVcT2YMuCq8vk6wqNFrm5FpHs8MToibbrLay0MhPVMDv_QEHL1FSb3jIp2Y185MSXAgMBAAECgYEAq9Dm7hX8cBVwO_NssI7AHJZAi2ZPrAR0W07WbNmtG1CXyTurZmMrdzA1i3rHta9d2YRxjsIJcV6cAmUHqDZI-l5SaGUNI5W4uq5tFVenvgNEvYQsRzJWPfYtkFCgXG38CNu-v8G3lEbptxUa8jfBhYDYsqyAFuktY8eILfmqaOECQQDZ8brK_ANYVKeXy2eeYz8uikr_JCpTRbwc4_BPOuGQ604GhY3CqvHh5heJbZUnYjJRZh-P7QkJ67cEqQe2E_yVAkEA1vP-0dzlv_I83q1ECdXV_C0tRtwxfdNDeAhoJjD-7CPqFeH5FsMi1d1u7eI0A0cmtYgKv1yfOQ_3Wv4TPY2FewJAFjiZTPzo8mafN9DbXcDMvlgHUYBEeH0RsvhilhFw0i_LosqXK5P2WYv2NmLf5EYUz0vUR_5o_4PzAxyg1qWvCQJBANSK2rJkfAC545pDNP2MpVP-z8A7ReymkxYDKghpdgfE01fj40qwlKdnlqI9gzSijBH_fLEPQplHwkwE4r2ATDUCQQDMJmDsuubnHNUADCDin6NR9cyQRK1Y1QNuhK6xU_vEUTRMY9BkHJGXo1hQHirH5sr6Y8K3zx7cYgbKiyUG1IgX");
		keymap.put("sit_xpy1",
				"MIICdgIBADANBgkqhkiG9w0BAQ******************************888888OflNkPsEvGWCvvTM4tlWcodoPbC52Q6EXsv9UJyOqbzfX9u9xGRLiUv3fOs9J02QQPK6ZPQiR0g8RvPv2858bh5finE13iwIuYTpgSRZVi2Kn7goer4qqwXD_TjM1B6PIpOylJksbF9RESZP0A8cG2twJprdZ54xYj4HIGvAgMBAAECgYAPG-b9LpO-g3z0nv-ozIsD0zWduVGK8iZS1plJMfdnRh_I5LYnY_Q6oQz1GP7d3otbBVm9wv45PZVxnFqDySwajI4uAP9ZZQ8RHrPTNttFgLm3OQ0shbIUhBi2vXorxicR-EnS4qWpCOP10o5JrlpieZ295S2p7Dn_xmIoRgPRKQJBAN4ilfdxuEU3E-eiPo98gUXFPpCCCXKla4JMvN2R6em8d0MVYT8g0rXoXS44UnEg0vOoJ7ulPh5Col6ilqR2op0CQQCaEIGvc2PDa8jHXSmDuwpl4ogqafNyY7FCjPqWvlG-_auU0qaKBuVhIEMuy-3ZUMFFCsmGkMOKr_7ACTW3bM27AkEAwCihHIYmhtGniWhjwBJPbgC8J5wl-iQ5RWWGuBGCjSz46nIzRr3pKW2SNeqI_s4LTrY3cO74NoskFMOHl0v9TQJARmWofH0jZtZHZiGBqLm8pJWAVrEXFnvLMXetwVexjq3myxf-FS_VfC37xNRWGGi4B05Ii352e1az9xe-PdQvpQJATWPfQc1IR-cefoAvEcUyQlTsthVQkJ3wUpRMEosw2V5a1f9euwhJXJDf6ca8zOhtyfXuTIag1YturYfKyXgY3w");
	}

	public static String post(String url, Map<String, String> body) {
		body = body == null ? new HashMap<String, String>() : body;
		StringBuilder params = new StringBuilder();
		StringBuilder bodys = new StringBuilder();
		StringBuilder data = new StringBuilder();
		try {
			HttpURLConnection urlconn = (HttpURLConnection) new URL(url).openConnection();
			urlconn.setDoOutput(true);
			urlconn.setDoInput(true);
			urlconn.setRequestMethod("POST");
			urlconn.setConnectTimeout(100000);
			urlconn.setReadTimeout(100000);
			urlconn.setUseCaches(false);
			urlconn.setInstanceFollowRedirects(true);
			urlconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			urlconn.connect();
			DataOutputStream dataout = new DataOutputStream(urlconn.getOutputStream());
			for (String key : body.keySet()) {
				String value = body.get(key);
				value = value == null ? "" : value;
				params.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
				bodys.append("&").append(key).append("=").append(value);
			}
			params.delete(0, 1);
			bodys.delete(0, 1);
			System.out.println(url + "?" + bodys);
			dataout.writeBytes(params.toString());
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			for (String line = null; ((line = bf.readLine()) != null); data.append(line))
				;
			bf.close();
			urlconn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String getdata = data.toString().isEmpty() ? "--- no data---" : data.toString();
		System.out.println(getdata);
		return data.toString();
	}

	public static String post(String url, Map<String, String> header, Map<String, String> body) {
		body = body == null ? new HashMap<String, String>() : body;
		header = header == null ? new HashMap<String, String>() : header;
		StringBuilder params = new StringBuilder();
		StringBuilder bodys = new StringBuilder();
		StringBuilder data = new StringBuilder();
		try {
			HttpURLConnection urlconn = (HttpURLConnection) new URL(url).openConnection();
			urlconn.setDoOutput(true);
			urlconn.setDoInput(true);
			urlconn.setRequestMethod("POST");
			urlconn.setConnectTimeout(100000);
			urlconn.setReadTimeout(100000);
			urlconn.setUseCaches(false);
			urlconn.setInstanceFollowRedirects(true);
			urlconn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			for (String key : header.keySet()) {
				urlconn.setRequestProperty(key, header.get(key));
			}
			urlconn.connect();
			DataOutputStream dataout = new DataOutputStream(urlconn.getOutputStream());
			for (String key : body.keySet()) {
				String value = body.get(key);
				value = value == null ? "" : value;
				params.append("&").append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
				bodys.append("&").append(key).append("=").append(value);
			}
			params.delete(0, 1);
			bodys.delete(0, 1);
			System.out.println(url + "?" + bodys + "  " + header);
			dataout.writeBytes(params.toString());
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			for (String line = null; ((line = bf.readLine()) != null); data.append(line))
				;
			bf.close();
			urlconn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(data);
		return data.toString();
	}

	public static String soap(String url, String soapBody) {
		StringBuilder data = new StringBuilder();
		System.out.println(soapBody);
		try {
			URL _url = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) _url.openConnection();
			httpConnection.setConnectTimeout(100000);
			httpConnection.setReadTimeout(100000);
			httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			httpConnection.setRequestProperty("Content-Length", soapBody);
			httpConnection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setUseCaches(false);
			httpConnection.connect();
			OutputStream out = httpConnection.getOutputStream();
			out.write(soapBody.getBytes("UTF-8"));
			out.flush();
			out.close();

			BufferedReader bf = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			for (String line; ((line = bf.readLine()) != null);)
				data.append(line);
			bf.close();
			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data + "\n");
		return data.toString();
	}

	public static String downloadFile(String url, String localPath) {

		localPath = localPath.endsWith("/") ? localPath : localPath + "/";

		String filename = localPath + " " + url.substring(url.lastIndexOf("/") + 1);
		File dir = new File(filename).getParentFile();
		if (!dir.exists())
			dir.mkdirs();
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			InputStream inputStream = conn.getInputStream();
			byte[] buffer = new byte[1024];

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (int len = 0; (len = inputStream.read(buffer)) != -1;)
				bos.write(buffer, 0, len);
			bos.close();
			FileOutputStream fos = new FileOutputStream(new File(filename));
			fos.write(bos.toByteArray());
			fos.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("            " + filename);
		System.out.println("-- pass --> " + url + " download success");
		return filename;
	}

	public static String snpost(String url, Map<String, String> map) {
//		Map<String, String> treemap = MyMap.treeMap(map);
		Map<String, String> treemap = new HashMap<String, String>();
		treemap.remove("signAlgorithm");
		treemap.remove("signature");
		treemap.remove("digest");
		StringBuffer sb = new StringBuffer();
		for (String k : treemap.keySet())
			sb.append("&").append(treemap.get(k).isEmpty() ? "" : k + "=" + treemap.get(k));
		sb.delete(0, 1);
		String digest = SignUtil.md5(sb.toString());
		String key = keymap.get((url.contains("pre") ? "pre_" : "sit_") + map.get("publicKeyIndex"));
		String signature = SignUtil.snRSA(digest, key);
		map.put("signature", signature);
		map.put("signAlgorithm", "RSA");
		return HttpUrl.post(url, map);
	}

	public static void main(String[] args) {
//		String url = "https://ttttportpre.cccing.com/eppstdcb_portal//bill/day/detail/download.htm?day=20190219&currency=CNY&bpNo=70060626";
//		downloadFile(url,Config.desk);

//		Map<String, String> reqMap = new HashMap<String, String>();
//
//
//        reqMap.put("publicKeyIndex", "0001");
//        reqMap.put("merchantNo", "66666666");
//        reqMap.put("inputCharset", "UTF-8");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        reqMap.put("submitTime", dateFormat.format(new Date()));
//        reqMap.put("version", "1.5");
//        reqMap.put("merchantCreateTime", dateFormat.format(new Date()));
//        reqMap.put("outOrderNo", UUID.randomUUID().toString().replace("-", ""));
//
//	    String sign = "";
//	    reqMap.put("signature", sign);
//	    reqMap.put("signAlgorithm", "RSA");
//
//	    snpost("http://omapre.cccing.com/oma/canCode/refreshStandardCode.htm",reqMap);

		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("id", "20191222894");
		post("http://fspre.ccccing.com/fogrs-web/api/v1/icc/restart", reqMap);

	}

}
