package org.example.keyword.commonTool;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.example.keyword.requestEntity.HttpGetRequestEntity;
import org.example.keyword.resultEntity.BaseResultEntity;
import org.example.keyword.resultEntity.HttpGetResultEntity;
import org.example.utils.httpClient.*;


import java.net.URI;
import java.util.List;
import java.util.Map;

public class HttpGetRequest extends BaseRequest<HttpGetRequestEntity, HttpGetResultEntity> {
    public HttpGetResultEntity execute(HttpGetRequestEntity request){
        HttpGetResultEntity result = new HttpGetResultEntity();
        try{
            Map<String, String> headerMap = convert2Map(request.getHeaders());
            Map<String, String> paramMap = convert2Map(request.getParams());
            List<HttpClientVariable> variables = convert2ListOfHttpClientVariable(request.getExpectedVariable());
            BaseHttpResult baseHttpResult = getRequest(request.getTargetHost(),request.getRelativeUrl(),variables,paramMap,headerMap,result);

            for(int i = 0; i < variables.size(); i++){
                String name = variables.get(i).getName();
                String value = baseHttpResult.getVariables().get(i);
                if(StringUtils.isNotBlank(value)){
                    result.getVariables().put(name,value);
                }
            }


            String nextTarget = baseHttpResult.getResponse().getCurrentUrl();

            if(StringUtils.isNotBlank(nextTarget)){
                URI target = new URI(nextTarget);
                List<NameValuePair> queryStrings = URLEncodedUtils.parse(target,"UTF-8");

                for(NameValuePair nameValuePair : queryStrings){

                    result.getNextTargetQueryStrings().put(nameValuePair.getName(),nameValuePair.getValue());
                }
            }

            result.getMoreDetailMessages().put("get请求结果: " , baseHttpResult.getResponse().getResultHtml());


        } catch (Exception e){
            logger.info(e.getMessage(),e);
            result.setSuccess(false);
            result.setDetailMessage(e.toString());
        }
        return result;
    }



    public BaseHttpResult getRequest(String targetHost, String relativeUrl,
                                      List<HttpClientVariable> requiredVariables,
                                      Map<String, String> dataParamsMap,
                                      Map<String, String> headaparam){
        return getRequest(targetHost,relativeUrl,requiredVariables,dataParamsMap,headaparam,new BaseResultEntity());
    }

    private BaseHttpResult getRequest(String targetHost, String relativeUrl,
                                      List<HttpClientVariable> requiredVariables,
                                      Map<String, String> dataParamsMap,
                                      Map<String, String> headaparam,
                                      BaseResultEntity serviceResult){
        String url = null;
        if(StringUtils.isNotBlank(relativeUrl)){
            url = targetHost + relativeUrl;

        } else {
            url = targetHost;
        }

        Request request = new Request("GET", url);
        request.setBrowserType(HttpClientContext.getInstance().getBrowserType());

        if(requiredVariables != null){
            for(HttpClientVariable variable : requiredVariables){
                request.getRequiredVariables().add(variable);
            }
        }

        //组装头信息,refer参数必须,为302做准备
        if(headaparam != null){
            if(headaparam.get("Referer") != null){

                //对于外链,referer应该用外链的host地址
                if(headaparam.get("Referer").toLowerCase().startsWith("http")){

                } else {
                    headaparam.put("Referer", targetHost + headaparam.get("Referer"));
                }
            }
            request.setHeadParams(headaparam);
        }

        //设置请求入参
        if(dataParamsMap != null){
            request.setParams(dataParamsMap);
        }

        //发起httpclient请求
        Response response =  HttpclientUtil.sendSingleRequest(request);
        serviceResult.getMoreDetailMessages().put("发起get请求",response.getInitialUrl());
        logger.info((response.getResultHtml()));
        BaseHttpResult result = new BaseHttpResult(response);
        for(int i = 0; i < request.getRequiredVariables().size(); i++){
            result.getVariables().add(request.getRequiredVariables().get(i).getValue().toString());
        }

        return result;

    }











}
