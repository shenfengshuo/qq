package org.example.utils.httpClient;

public class HttpClientContext extends SofaContext{
    private static final String HTTPCLIENT_VARIABLE_CONTAINER = "httpClientVarableContainer";
    private static final String HTTPCLIENT_BROWSERTYPE = "httpClientType";

    //获取当前的HttpClientConetxt实例
    public static HttpClientContext getInstance(){
        HttpClientContext httpClientContext = (HttpClientContext) SofaContext.getInstance(HttpClientContext.class);
        if(httpClientContext.getHttpClientVariableContainer() == null){
            httpClientContext.setHttpclientVariableContainer(new HttpClientVariableContainer());
        }
        return httpClientContext;
    }

    public static void resetContext(){
        HttpClientContext httpClientContext = (HttpClientContext) SofaContext.getInstance(HttpClientContext.class);
        if(httpClientContext.getHttpClientVariableContainer() != null){
            httpClientContext.setHttpclientVariableContainer(null);
        }
    }

    //设置httpClient请求自动化测试参数容器
    public void setHttpclientVariableContainer(HttpClientVariableContainer container){
        this.addProperty(HTTPCLIENT_VARIABLE_CONTAINER,container);
    }

    //获取httpClient请求自动化测试参数容器
    public HttpClientVariableContainer getHttpClientVariableContainer(){
        try{
            return (HttpClientVariableContainer) this.getProperty(HTTPCLIENT_VARIABLE_CONTAINER);
        }catch (Exception e){
            return null;
        }
    }

    public void setBrowserType(String browserType){this.addProperty(HTTPCLIENT_BROWSERTYPE, browserType);};
    public String getBrowserType(){return (String) this.getProperty(HTTPCLIENT_BROWSERTYPE);};

}
