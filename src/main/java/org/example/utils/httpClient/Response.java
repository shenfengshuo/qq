package org.example.utils.httpClient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;


import java.util.HashMap;
import java.util.Map;

public class Response {
    private String currentUrl;
    private String initialUrl;

    public String getInitialUrl() {
        return initialUrl;
    }
    public void setInitialUrl(String initialUrl) {
        this.initialUrl = initialUrl;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public Map<String,String> getCurrentQueryParameters(){
        return parseQueryString(currentUrl);
    }
    public Map<String,String> getInitalQueryParameters(){
        return parseQueryString(initialUrl);
    }


    private static Map<String, String> parseQueryString(String query){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(query)){
            return map;
        }

        String[] tempArr = query.split("&");
        for(String t : tempArr){
            String[] tempArr2 = t.split("=",2);
            map.put(tempArr2[0],tempArr2[1]);
        }
        return map;
    }

    private String initialQuery;

    public String getInitialQuery() {
        return initialQuery;
    }

    public void setInitialQuery(String initialQuery) {
        this.initialQuery = initialQuery;
    }

    private String currentQuery;

    public String getCurrentQuery() {
        return currentQuery;
    }

    public void setCurrentQuery(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    int code;
    String redirectLocation;
    CookieStore cookieStore;
    String resultHtml;
    Response next;

    public void setCode(int code) {
        this.code = code;
    }
    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
    public void setResultHtml(String resultHtml) {
        this.resultHtml = resultHtml;
    }
    public CookieStore getCookieStore() {
        return cookieStore;
    }
    public int getCode() {
        return code;
    }
    public String getResultHtml() {
        return resultHtml;
    }
    public void setNext(Response next) {
        this.next = next;
    }
    public Response getNext() {
        return next;
    }

    public Response getLast(){
        if(null == next){
            return  this;
        }
        return next.getLast();
    }

    public String getRedirectLocation() {
        return redirectLocation;
    }

    public void setRedirectLocation(String redirectLocation) {
        this.redirectLocation = redirectLocation;
    }


    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("code:" + code);
        sBuilder.append(",result html" + resultHtml);
        return sBuilder.toString();
    }
}
