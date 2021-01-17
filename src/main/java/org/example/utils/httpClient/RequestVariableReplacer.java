package org.example.utils.httpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestVariableReplacer {

    private static final Logger logger = LoggerFactory.getLogger(HttpclientUtil.class);

    public static void replaceBaseUrl(Request request,HttpClientVariableContainer container){
        String url = request.getBasicUrl();
        String[] parts = url.split("\\?");
        StringBuffer replaceParam = new StringBuffer("");

        if(parts.length == 2){
            String paramStr = parts[1];
            String[] params = paramStr.split("&");
            for(String param : params){
                if(replaceParam.length() > 0){
                    replaceParam.append("&");
                }

                String[] kv = param.split("=");
                String value;
                if(kv.length < 2){
                    value = "";

                } else {
                    value = kv[1];

                }
                if(value.startsWith("$!")){
                    String variableKey = value.replace("$!","");
                    HttpClientVariable variable = container.getVariableByName(variableKey);
                    if(kv[0].equals("securetyId")){
                        try{
                            String encodedVal = URLEncoder.encode(variable.getValue(),"GBK");
                            replaceParam.append(kv[0]).append("=").append(encodedVal);
                        } catch (UnsupportedEncodingException e){
                            logger.info("encode failed");
                        }
                    } else {
                        replaceParam.append(kv[0]).append("=").append(variable.getValue());
                    }
                } else {
                    if(value.contains("$!alieditUid")){
                        String replaced = value.replaceAll("$!alieditUid",container.getVariableByName("alieditUid").getValue());
                        replaceParam.append(encodeUrlParam(kv[0] + "=" + replaced));
                    } else {
                        replaceParam.append(param);
                    }
                }

            }
            request.setRealUrl(parts[0] + "?" + replaceParam);
        } else {
            request.setRealUrl(url);
        }
    }

    public static String encodeUrlParam(String param){
        StringBuffer sb = new StringBuffer("");
        String[] kandv = param.split("=");
        try{
            String afterEncodeValue = URLEncoder.encode(kandv[1],"gbk");
            sb.append(kandv[0]).append("=").append(afterEncodeValue);
        } catch (UnsupportedEncodingException e){
            logger.info("encode failed", e.toString());
        }
        return sb.toString();
    }

    public static void replaceParams(Request request,HttpClientVariableContainer container){
        Map<String, String> oldParams = request.getParams();
        Map<String, String> newParams =new LinkedHashMap<>();
        for(String key : oldParams.keySet()){
            String value = oldParams.get(key);
            if(value != null){
                if(value.startsWith("$!")){
                    String variableKey = value.replace("$!","");
                    HttpClientVariable variable = container.getVariableByName(variableKey);
                    if(variable != null){
                        newParams.put(key,variable.getValue());
                    } else {
                        newParams.put(key, value);
                    }

                } else {
                    newParams.put(key,value);
                }
            }
            request.setRealParams(newParams);
        }

    }








}
