package org.example.keyword.requestEntity;

public class HttpGetRequestEntity extends BaseRequestEntity {

    private String bodyFormData;
    private String bodyFormUrlEncodeData;
    private String bodyRawData;
    private String headers;
    private String params;
    private String relativeUrl;
    private String targetHost;
    private String expectedVariable;

    public String getBodyFormData() {
        return bodyFormData;
    }

    public void setBodyFormData(String bodyFormData) {
        this.bodyFormData = bodyFormData;
    }

    public String getBodyFormUrlEncodeData() {
        return bodyFormUrlEncodeData;
    }

    public void setBodyFormUrlEncodeData(String bodyFormUrlEncodeData) {
        this.bodyFormUrlEncodeData = bodyFormUrlEncodeData;
    }

    public String getBodyRawData() {
        return bodyRawData;
    }

    public void setBodyRawData(String bodyRawData) {
        this.bodyRawData = bodyRawData;
    }

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
        return targetHost;
    }

    @Override
    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public String getExpectedVariable() {
        return expectedVariable;
    }

    public void setExpectedVariable(String expectedVariable) {
        this.expectedVariable = expectedVariable;
    }
}
