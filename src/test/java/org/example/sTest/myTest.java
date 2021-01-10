package org.example.sTest;


import org.apache.http.HttpResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.example.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// TASK20191125871.json组合 hive hive

// TASK201912202319.json非组合 hive hive     ok
// TASK20191127879.json非组合 ftp hive     ok
// TASK201912202305.json非组合 ftp ftp     ok
//TASK2019122323299 纵向合并数据源hive
//纵向合并数据源ftp
//横向合并数据源hive
public class myTest {

	

	@BeforeMethod
	public void setUp() throws Exception {
	
	}

	@Test(priority = 1)
	public void taskTest1() {
	    String host = "https://jshmgsdmfb.market.alicloudapi.com";
	    String path = "/shouji/query";
	    String method = "GET";
	    String appcode = "440b35e36e4846798fdff12ca3f6b684";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("shouji", "13456755448");


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println("111:  " + response.toString());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	@Test(priority=2)
	public void taskTest2(){
        String host = "https://wuliu.market.alicloudapi.com";// 【1】请求地址 支持http 和 https 及 WEBSOCKET
        String path = "/kdi";  // 【2】后缀
        String appcode = "440b35e36e4846798fdff12ca3f6b684"; // 【3】开通服务后 买家中心-查看AppCode
        String no = "780098068058";// 【4】请求参数，详见文档描述
        String type = "zto"; //  【4】请求参数，不知道可不填 95%能自动识别
        String urlSend = host + path + "?no=" + no +"&type="+type;  // 【5】拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon .setRequestProperty("Authorization", "APPCODE " + appcode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                System.out.println("正常请求计费(其他均不计费)");
                System.out.println("获取返回的json:");
                System.out.print(json);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("套餐包次数用完 ");
                } else {
                    System.out.println("参数名错误 或 其他错误");
                    System.out.println(error);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("URL格式错误");
        } catch (UnknownHostException e) {
            System.out.println("URL地址错误");
        } catch (Exception e) {
            // 打开注释查看详细报错异常信息
            // e.printStackTrace();
        }

    }

    /*
     * 读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
	

	@AfterMethod
	public void tearDown() {

	}

}
