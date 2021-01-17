package org.example.keyword.resultEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpPostResultEntity extends BaseResultEntity {
    private String responseContent;
    private Map<String, String> soapResultMap = new HashMap<>();
    private Map<String, String> variabels = new HashMap<>();


    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public Map<String, String> getSoapResultMap() {
        return soapResultMap;
    }

    public void setSoapResultMap(Map<String, String> soapResultMap) {
        this.soapResultMap = soapResultMap;
    }

    public Map<String, String> getVariabels() {
        return variabels;
    }

    public void setVariabels(Map<String, String> variabels) {
        this.variabels = variabels;
    }
}
