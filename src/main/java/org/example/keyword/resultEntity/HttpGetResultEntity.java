package org.example.keyword.resultEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpGetResultEntity extends BaseResultEntity {
    private String responseContent;
    private Map<String, String> nextTargetQueryStrings = new HashMap<>();
    private Map<String, String> variables = new HashMap<>();

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public Map<String, String> getNextTargetQueryStrings() {
        return nextTargetQueryStrings;
    }

    public void setNextTargetQueryStrings(Map<String, String> nextTargetQueryStrings) {
        this.nextTargetQueryStrings = nextTargetQueryStrings;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
}
