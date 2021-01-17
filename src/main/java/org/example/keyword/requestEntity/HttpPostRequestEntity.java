package org.example.keyword.requestEntity;

public class HttpPostRequestEntity extends BaseRequestEntity {

    private String headers;
    private String params;
    private String relativeUrl;
    private String targetUrl;
    private String expectedVariable;
    private String payLoadBody;

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    @Override
    public String getTargetHost() {
        return targetUrl;
    }

    @Override
    public void setTargetHost(String targetHost) {
        this.targetUrl = targetHost;
    }

    public String getExpectedVariable() {
        return expectedVariable;
    }

    public void setExpectedVariable(String expectedVariable) {
        this.expectedVariable = expectedVariable;
    }

    public String getPayLoadBody() {
        return payLoadBody;
    }

    public void setPayLoadBody(String payLoadBody) {
        this.payLoadBody = payLoadBody;
    }
}
