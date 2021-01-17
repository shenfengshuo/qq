package org.example.utils.httpClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Maps;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class SendRequest {

    private static final Logger logger = LoggerFactory.getLogger(SendRequest.class);
    final static int REDIRECT_CODE = 302;
    final static String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3775.400 QQBrowser/10.6.4208.400";
    final static String FIREFOX_USER_AGENT = "";

    //执行请求
    public static Response doRequest(Request request){
        //实例化连接管理器和http本地客户端
        ClientConnectionManager comMgr = new ThreadSafeClientConnManager();
        DefaultHttpClient httpclient = new DefaultHttpClient(comMgr);

        //开启HTTPS
        enableSSL(httpclient);

        //准备请求
        HttpRequestBase realRequest = null;
        if(request.getMethod().equalsIgnoreCase("POST")){
            realRequest = preparePostRequest(request.getRealUrl(),request.getRealParams(),
                    request.getCookieStore(),request.getHeadParams(),request.getHttpEntity(),
                    request.getBrowserType());
        } else if(request.getMethod().equalsIgnoreCase("GET")){
            realRequest = prepareGetRequest(request.getRealUrl(),request.getRealParams(),
                    request.getCookieStore(),request.getHeadParams(),request.getBrowserType());
        }

//        realRequest.setHeader("Accept-Language","zh-CN,zh;q=0.8");
//      设置本次请求cookieStore
        httpclient.setCookieStore(request.getCookieStore());

        //执行本次请求
        Response response = executeRequest(httpclient,realRequest);

        //将cookie值写入返回结果中
        for(Cookie c : httpclient.getCookieStore().getCookies()){
            if(response != null && response.getCookieStore() != null){
                response.getCookieStore().addCookie(c);
            }
        }

        comMgr.closeExpiredConnections();
        comMgr.closeIdleConnections(20, TimeUnit.SECONDS);
        httpclient.close();

        return response;

    }

    //开启HTTPS
    private static void enableSSL(DefaultHttpClient httpclient){
          try{
              SSLContext sslcontext = SSLContext.getInstance("SSL");
              sslcontext.init(null,new TrustManager[] {truseAllManager},null);
              SSLSocketFactory sf = new SSLSocketFactory(sslcontext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
              Scheme https = new Scheme("https",443,sf);
              httpclient.getConnectionManager().getSchemeRegistry().register(https);

          }  catch (Exception e){
              logger.info(e.toString());
          }
    }

    //准备post请求
    private static HttpPost preparePostRequest(String url, Map<String,String> params,
                                               CookieStore cookie, Map<String, String> headParams,
                                               HttpEntity entity, String browser){
        HttpPost httpPost = new HttpPost(url);

        //设置参数容器
        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keySet = params.keySet();

        //设置请求页头
        Map<String, String> headers = Maps.newConcurrentMap();
        headers.put("Content-Type","application/x-www-form-urlencoded;charset=UTf-8");

        //multiPartEntity,不需要手动设置head
        if(entity != null){
            headers.remove("Content-Type");
        }
        headers.put("","");


        if(StringUtils.equals(browser,"CHROME")){
            headers.put("User-Agent",CHROME_USER_AGENT);
        } else {
            headers.put("User-Agent",FIREFOX_USER_AGENT);
        }
        //如果是服务器拓展收钱码,必须是来自dingding的请求
        if(keySet.toString().contains("materialUrl")){
            headers.put("User-Agent","DingTalk");
        }
        if(headParams.size() > 0){
            headers.putAll(headParams);
        }

        if(null != headers){
            for(Map.Entry<String, String> header : headers.entrySet()){
                httpPost.addHeader(header.getKey(),header.getValue());
            }
        }

        //将构造请求参数转化httpclient收受的post请求参数
        for(String paraKey : keySet){
            String paraValue = params.get(paraKey);
            if(paraValue == null){
                continue;
            }

            if(StringUtils.equalsIgnoreCase("##Payload##", paraKey)){
                //ajax json请求post方式
                try{
                    String newValue = params.get(paraKey);
                    StringEntity se = new StringEntity(newValue);
                    if(headers.containsKey("Content-Type") && StringUtils.contains(headers.get("Content-Type").toUpperCase(),"UTP-8")){
                        //
                        se = new StringEntity(newValue,"applicatio/json","UTF-8");
                    } else {
                        se.setContentType("text/json");
                        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"applicatio/json"));
                    }

                    httpPost.setEntity(se);

                } catch (Exception e){
                    logger.info("decode fail!");
//                    logger.error("decode fail!");
                }

            } else {
                if(paraValue.startsWith("cookie::")){

                    String cookieName = "_tb_token_";
                    if(cookie != null) {
                        List<Cookie> cookies = cookie.getCookies();
                        for(int i = 0; i < cookies.size(); i++){
                            if(cookieName.equals(cookies.get(i).getName())){
                                paraValue = cookies.get(i).getValue();
                                break;
                            }
                        }

//                        params.put(paraKey,paraValue);
                        nvps.add(new BasicNameValuePair(paraKey,paraValue));
                    }

                } else if (paraValue.equals("ct::value")){
                    String cookieName = "t";
                    if(cookie != null){
                        List<Cookie> cookies = cookie.getCookies();
                        for(int i = 0; i<cookies.size(); i++){
                            if(cookieName.equals(cookies.get(i).getName())){
                                paraValue = cookies.get(i).getValue();
                                break;
                            }

                        }
                        nvps.add(new BasicNameValuePair(paraKey,paraValue));
                    }


                }else {
                    try{
                        httpPost.getParams().setParameter(paraKey,paraValue);
                        //对于ctoken这个参数,
                        //当value值为"+df5...3DbDb....Agf1"时,
                        // encode会变成"df5...3DbDb....Agf1","+"消失,
                        //所以增加判断

                        String newValue;
                        if(paraKey.contains("ctoken")){
                            newValue = paraValue;
                        } else if(paraKey.contains("ebankSB")){
                            newValue = paraValue;
                        } else {
                            newValue = URLDecoder.decode(StringUtils.trimToEmpty(paraValue),"GBK");

                        }
                        BasicNameValuePair basic = new BasicNameValuePair(paraKey, newValue);
                        nvps.add(basic);
                    } catch (UnsupportedEncodingException e){
                        logger.info("decode fail!");
                    }
                }

            }

        }

        try{
            if(httpPost.getEntity() == null){
                httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
            }
            if(entity != null){
                httpPost.setEntity(entity);
            }
        } catch (UnsupportedEncodingException e){
            logger.info("set entity fail!");
        }

        return httpPost;
    }


    //发送get请求
    private static HttpGet prepareGetRequest(String url, Map<String,String> params,
                                             CookieStore cookie, Map<String, String> headParams,
                                             String browser){
        HttpGet httpGet = new HttpGet(url);

        //设置请求页头
        Map<String, String> headers = Maps.newConcurrentMap();
        headers.put("Content-Type","application/x-www-form-urlencoded;charset=UTf-8");
        headers.put("X-Requested-With","XMLHttpRequest");

        if(StringUtils.equals(browser,"CHROME")){
            headers.put("User-Agent",CHROME_USER_AGENT);
        } else {
            headers.put("User-Agent",FIREFOX_USER_AGENT);
        }

        if(headParams.size() > 0){
            headers.putAll(headParams);
        }

        if(null != headers){
            for(Map.Entry<String, String> header : headers.entrySet()){
                httpGet.addHeader(header.getKey(),header.getValue());
            }
        }

        //将构造请求参数转化httpclient收受的get请求参数
        Set<String> keySet = params.keySet();
        List<NameValuePair> nvps = new ArrayList<>();

        for(String paraKey : keySet){
            String paraValue = params.get(paraKey);
            if(StringUtils.isBlank(paraValue)){
                continue;
            }


            if(paraValue.startsWith("cookie::")){

                String cookieName = "_tb_token_";
                if(cookie != null) {
                    List<Cookie> cookies = cookie.getCookies();
                    for(int i = 0; i < cookies.size(); i++){
                        if(cookieName.equals(cookies.get(i).getName())){
                            paraValue = cookies.get(i).getValue();
                            break;
                        }
                    }
//                        params.put(paraKey,paraValue);
                    nvps.add(new BasicNameValuePair(paraKey,paraValue));
                }

            } else if (paraValue.equals("ct::value")){
                String cookieName = "t";
                if(cookie != null){
                    List<Cookie> cookies = cookie.getCookies();
                    for(int i = 0; i<cookies.size(); i++){
                        if(cookieName.equals(cookies.get(i).getName())){
                            paraValue = cookies.get(i).getValue();
                            break;
                        }

                    }
                    nvps.add(new BasicNameValuePair(paraKey,paraValue));
                }
            }else {
                try{
                    httpGet.getParams().setParameter(paraKey,paraValue);
                    //对于ctoken这个参数,
                    //当value值为"+df5...3DbDb....Agf1"时,
                    // encode会变成"df5...3DbDb....Agf1","+"消失,
                    //所以增加判断

                    String newValue;
                    if(paraKey.contains("ctoken")){
                        newValue = paraValue;
                    } else if(paraKey.contains("ebankSB")){
                        newValue = paraValue;
                    } else if(paraKey.contains("dataContent")){
                        newValue = paraValue;
                    } else {
                        newValue = URLDecoder.decode(StringUtils.trimToEmpty(paraValue),"GBK");

                    }
                    BasicNameValuePair basic = new BasicNameValuePair(paraKey, newValue);
                    nvps.add(basic);
                } catch (UnsupportedEncodingException e){
                    logger.info("decode fail!");
                }
            }

        }
        if(nvps.size() > 0){
            httpGet.setURI(URI.create(String.format(url + "?" + URLEncodedUtils.format(nvps,"UTF-8"))));
        }

    return httpGet;
    }


    //发送请求
    private static Response executeRequest(DefaultHttpClient httpClient, HttpUriRequest request){

//        CookieSpecFactory csf = new CookieSpecFactory() {/
//            @Override
//            public CookieSpec newInstance(HttpParams params) {
//                return (BrowserCompatSpec) validate(cookie,origin)>{ oh,i am easy};
//                return {oh,i am easy}
//            }
//        };
//  httpClient.getCookieSpecs().register("easy", csf);
//        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,"easy");

//        httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,false);
//        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        Response result = new Response();

        //实例化httpclient的Response
        HttpResponse response;

        try {

            HttpContext context = new BasicHttpContext();
            response = httpClient.execute(request, context);
            HttpEntity httpEntity = response.getEntity();

            String charSet = EntityUtils.getContentCharSet(httpEntity);
            if(StringUtils.isBlank(charSet)){
                //
                if(StringUtils.equals(request.getURI().getAuthority(),"www.baidu.net")){
                    charSet = "GBK";
                    logger.info("GBK");
                } else {
                    charSet = HTTP.UTF_8;
                    logger.info("UTF_8");
                }
            }

            //logger.info(JSON.toJSONString(response));
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            result.setCurrentQuery(currentReq.getURI().getQuery());
            HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

            String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());

            result.setInitialQuery(request.getURI().getQuery());
            result.setInitialUrl(request.getURI().toString());
            result.setCurrentUrl(currentUrl);
            logger.info("current url:  " + currentUrl);
            if(response == null){
                return null;
            }

            //将code写入result中
            result.setCode(response.getStatusLine().getStatusCode());

            //将跳转连接写入result中,当非302跳转是,将结果页面写入result中
            if(response.getStatusLine().getStatusCode() == 302){
                logger.info("302");
//                String location = result.getRedirectLocation(response);
                String location = result.getRedirectLocation();
                result.setRedirectLocation(location);
            } else if (response.getStatusLine().getStatusCode() == 200){
                String resultStr = paseResponse(response,charSet);

                logger.info("resultStr200:  " + resultStr);
                result.setResultHtml(resultStr);
            }


            //将cookie值写入结果中
            result.setCookieStore(httpClient.getCookieStore());
            //将返回的页头写入结果


        } catch (ClientProtocolException e) {
            logger.info("exe url ClientProtocolException error");
        } catch (IOException e){
            logger.info("exe url IOException error");
        } catch (Exception e){
            logger.info("exe url error");
        } finally {
            httpClient.getConnectionManager().shutdown();
        }


        return result;
    }




    //获取跳转url
    public static String getRedirectLocation(HttpResponse response){
        String redirectUrl = null;
        Header locationHeader = response.getFirstHeader("Location");
        if(null == locationHeader){
            return redirectUrl;
        }
        redirectUrl = locationHeader.getValue();
        return redirectUrl;
    }


    //解析请求结果
    public static String paseResponse(HttpResponse response, String charSet){
        HttpEntity entity = response.getEntity();
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException e){
            logger.info("passser body error");
        } catch (IOException e){
            logger.info("passser body error");
        } catch (Throwable e){
            logger.info("passser body error");
        }
        return body;
    }


    //ssh管理器
    private static TrustManager truseAllManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };














}
