package org.example.keyword.commonTool;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.log4j.Logger;
import org.example.keyword.requestEntity.BaseRequestEntity;
import org.example.keyword.resultEntity.BaseResultEntity;
import org.example.utils.httpClient.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseRequest<T extends BaseRequestEntity, R extends BaseResultEntity>  {
    protected final static String aqcHomeUrl = "";
    protected static final Logger logger = Logger.getLogger(BaseRequest.class);

    protected Boolean isNotBlank(String s){
        if (s == null || s.isEmpty()){
            return false;
        }
        return true;
    }

    protected static Map<String, String > convert2Map(String content){
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isNoneBlank(content)){
            String[] headers = StringUtils.split(content,"^^^");
            for(String h : headers){
                String value = StringUtils.substringAfter(h,":");
                String name = StringUtils.substringBefore(h,":");
                map.put(name, value);
            }
        }
        return map;
    }

    protected static Map<String, String> convert2MapV2(String content){
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isNoneBlank(content)){
            try{
                map = JSON.parseObject(content,Map.class);
            }catch (Exception e){
                logger.error("conver2MapV2 error",e);
            }
        }
        return map;
    }


    protected static List<HttpClientVariable> convert2ListOfHttpClientVariable(String content){
        List<HttpClientVariable> list = new ArrayList<>();
        if(StringUtils.isNoneBlank(content)){
            String[] lines = StringUtils.split(content,"\n");
            for(String line : lines){
                String elementType = StringUtils.substringBetween(line,"[TYPE]","-[NAME]");
                String elementName = StringUtils.substringBetween(line,"-[NAME]","-[VALUE1]");
                String elementValue1 = StringUtils.substringBetween(line,"-[NALUE1]","-[VALUE2]");
                String elementValue2 = StringUtils.substringAfterLast(line,"-[VALUE2]");
                if(StringUtils.equals("HTML",elementType)){
                    HtmlBaseVariable htmlBaseVariable = new HtmlBaseVariable(elementName,elementValue1,elementValue2);
                    list.add(htmlBaseVariable);
                } else if (StringUtils.equals("COOKIE",elementType)){
                    CookieBasedVariable cookieBasedVariable = new CookieBasedVariable(elementName,elementValue1);
                    list.add(cookieBasedVariable);
                }
            }


        }
        return list;
    }




    protected BaseHttpResult postRequest(String serviceAddress, String requestUrl,
                                         List<HttpClientVariable> requestVariables,
                                         Map<String, String> dataParamsMap,
                                         Map<String, String> headParam, BaseResultEntity result){
        return postRequest(serviceAddress,requestUrl,requestVariables,dataParamsMap,headParam,true,null,result);
    }

    protected BaseHttpResult postRequest(String serviceAddress, String requestUrl,
                                         List<HttpClientVariable> requestVariables,
                                         Map<String, String> dataParamsMap,
                                         Map<String, String> headParam, Boolean autoDirect,
                                         HttpEntity entity, BaseResultEntity baseServiceResult){

        if(requestUrl == null){
            requestUrl = "";
        }
        String url = serviceAddress + requestUrl;
        Request request = new Request("POST",url);
        if(headParam != null){
            if(headParam.get("Referer") != null){
                headParam.put("Referer",headParam.get("Referer"));

            }
            int test = 1;
            request.setHeadParams(headParam);
        }

        //添加从request中获取的变量
        for(HttpClientVariable variable : requestVariables){
            request.getRequiredVariables().add(variable);
        }

        request.setParams(dataParamsMap);
        request.setAutoRedirect(false);

        request.setRealUrl(url);

        Response response = HttpclientUtil.sendSingleRequest(request);


        BaseHttpResult result = new BaseHttpResult(response);
        for(int i = 0;i< request.getRequiredVariables().size();i++){
            result.getVariables().add(request.getRequiredVariables().get(i).getValue().toString());

        }
        return result;


    }




}
