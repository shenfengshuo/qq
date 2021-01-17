package org.example.utils.httpClient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;

import java.util.*;

public class Request {
    public Request() {
    }

    public Request(String method,String basicUrl) {
        this.method = method;
        this.basicUrl = basicUrl;
    }

    private String basicUrl;
    private String realUrl;
    private String method;
    private Map<String,String> params = new LinkedHashMap();
    private Map<String,String> realParams = new LinkedHashMap();
    private Map<String,String> headParams = new HashMap<>();
    private CookieStore cookieStore;
    private List<HttpClientVariable> requiredVariables = new ArrayList<>();
    private Response response;
    private boolean isAutoRedirect = true;
    private HttpEntity httpEntity;
    private String browserType;



    public HttpEntity getHttpEntity() {
        return httpEntity;
    }
    public String getBrowserType() {
        return browserType;
    }
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }
    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }
    public void setBasicUrl(String basicUrl) {
        this.basicUrl = basicUrl;
    }
    public void setMethod(String method) { this.method = method; }
    public void setRequiredVariables(List<HttpClientVariable> requiredVariables) {
        this.requiredVariables = requiredVariables;
    }
    public List<HttpClientVariable> getRequiredVariables() {
        return requiredVariables;
    }
    public String getBasicUrl() {
        return basicUrl;
    }
    public String getMethod() {
        return method;
    }
    public void setResponse(Response response) {
        this.response = response;
        this.cookieStore = response.getLast().getCookieStore();
    }
    public Response getResponse() {        return response;    }
    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
    public CookieStore getCookieStore() {        return cookieStore;    }
    public void setParams(Map<String, String> params){this.params = params;}
    public Map<String, String> getParams() {return params;}

    public Map<String, String> getHeadParams() {return headParams;}
    public void setHeadParams(Map<String, String> headParams){this.headParams = headParams;}

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public Map<String, String> getRealParams() {return realParams;}
    public void setRealParams(Map<String, String> realParams){this.realParams = realParams;}

    public boolean isAutoRedirect() {
        return isAutoRedirect;
    }

    public void setAutoRedirect(boolean isAutoRedirect) {
        isAutoRedirect = isAutoRedirect;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}