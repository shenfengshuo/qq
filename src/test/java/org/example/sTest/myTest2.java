package org.example.sTest;


import com.alibaba.fastjson.JSON;
import org.example.keyword.commonTool.HttpGetRequest;
import org.example.keyword.commonTool.HttpPostRequest;
import org.example.keyword.requestEntity.HttpGetRequestEntity;
import org.example.keyword.requestEntity.HttpPostRequestEntity;
import org.example.keyword.resultEntity.HttpGetResultEntity;
import org.example.keyword.resultEntity.HttpPostResultEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
// TASK20191125871.json组合 hive hive

// TASK201912202319.json非组合 hive hive     ok
// TASK20191127879.json非组合 ftp hive     ok
// TASK201912202305.json非组合 ftp ftp     ok
//TASK2019122323299 纵向合并数据源hive
//纵向合并数据源ftp
//横向合并数据源hive
public class myTest2 {

	@BeforeMethod
	public void setUp() throws Exception {
	
	}

	@Test(priority = 1)
	public void getTest1() {
        HttpGetRequestEntity request =  new HttpGetRequestEntity();
        request.setTargetHost("http://jshmgsdmfb.market.alicloudapi.com/shouji/query?shouji=13456755448");
        request.setHeaders("Authorization:APPCODE 440b35e36e4846798fdff12ca3f6b684^^^Content-Type:application/x-www-form-urlencoded; charset=utf-8");

        HttpGetRequest keyword = new HttpGetRequest();
        HttpGetResultEntity result = keyword.execute(request);

        System.out.println(JSON.toJSONString(result));
	}


    	@Test(priority = 2)
    	public void postTest1() {
        HttpPostRequestEntity request =  new HttpPostRequestEntity();
        request.setTargetHost("http://yaosu.market.alicloudapi.com/api/zmxy/zmxy_two/ali_two?certNo=411425198511125415&name=申丰硕&serialNo=123654");
        request.setHeaders("Authorization:APPCODE 440b35e36e4846798fdff12ca3f6b684^^^Content-Type:application/x-www-form-urlencoded; charset=utf-8");

        HttpPostRequest keyword = new HttpPostRequest();
        HttpPostResultEntity result = keyword.execute(request);

        System.out.println(JSON.toJSONString(result));
	}

}
