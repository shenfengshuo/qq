package org.example.utils.httpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpclientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpclientUtil.class);

    final static int REDIRECT_CODE = 302;

    //http请求,302则跳转继续执行

    public static Response sendSingleRequest(Request request){

        //从线程上下文获取Httpclient上下文
        HttpClientVariableContainer container = HttpClientContext.getInstance().getHttpClientVariableContainer();

        //如果上次请求的结果不为空，则将上次请求后的cookie塞入当前的cookieStore中
        Response lastResponse = null;
        lastResponse = container.getLastResponse();
        if(lastResponse != null){
            request.setCookieStore(lastResponse.getCookieStore());
        }

        //请求前置处理，执行请求
        prepareRequest(request, container);
        Response response = SendRequest.doRequest(request);
        //如果response为null,直接抛出异常
        if(response == null || response.getCode() == 0){
            throw new RuntimeException("httpclient调用返回response为null");
        }
        Response last = response;

        //如果该次请求结果包含302跳转,自动按照新地址发送请求
        while (last.getCode() == REDIRECT_CODE && request.isAutoRedirect()){
            Request nextRequest = requestFromResponse(last);
            logger.info("redirect to: " + nextRequest.getBasicUrl());
            nextRequest.setCookieStore(last.getCookieStore());

            //请求前置处理,执行请求
            prepareRequest(nextRequest, container);
            Response nextResponse = SendRequest.doRequest(nextRequest);
            last = nextResponse;

        }

        //从请求结果中获取参数
        parseVariableFromResponse(request,last,container);

        //重置lastResponse进入到静态容器中
        container.setLastResponse(last);

        return last;
    }

    private static void prepareRequest(Request request, HttpClientVariableContainer container){
        RequestVariableReplacer.replaceBaseUrl(request,container);
        RequestVariableReplacer.replaceParams(request,container);
    }


    //从请求返回中获取新的返回信息
    private static Request requestFromResponse(Response response){
        Request request = new Request();
        String url = response.getRedirectLocation();
        request.setBasicUrl(url);
        request.setMethod("GET");
        return request;
    }

    //将请求结果中的html变量写入到容器中去
    private static void parseVariableFromResponse(Request request, Response response,
                                                  HttpClientVariableContainer container){
        List<HttpClientVariable> requiredVariables = request.getRequiredVariables();
        for(HttpClientVariable v : requiredVariables){
            v.parse(response);
            container.addVariable(v);
        }
    }



}
