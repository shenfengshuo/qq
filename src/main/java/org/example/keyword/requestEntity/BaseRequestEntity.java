package org.example.keyword.requestEntity;

public class BaseRequestEntity {
    private boolean shadowMark = false;
    private boolean remoteType = false;
    private String operator;
    private String targetUrl;
    private String expectedUrl;
    private String environmentType;
    private String contextId;

    public BaseRequestEntity(){}

    public boolean isShadowMark() {
        return this.shadowMark;
    }

    public void setShadowMark(boolean shadowMark) {
        this.shadowMark = shadowMark;
    }

    public boolean isRemoteType() {
        return this.remoteType;
    }

    public void setRemoteType(boolean remoteType) {
        this.remoteType = remoteType;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTargetHost() {
        return this.targetUrl;
    }

    public void setTargetHost(String targetHost) {
        this.targetUrl = targetHost;
    }

    public String getExpectedUrl() {
        return this.expectedUrl;
    }

    public void setExpectedUrl(String expectedUrl) {
        this.expectedUrl = expectedUrl;
    }

    public String getEnvironmentType() {
        return this.environmentType;
    }

    public void setEnvironmentType(String environmentType) {
        this.environmentType = environmentType;
    }

    public String getContextId() {
        return this.contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
}
