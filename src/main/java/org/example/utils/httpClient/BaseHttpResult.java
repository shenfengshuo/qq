package org.example.utils.httpClient;

import java.util.ArrayList;
import java.util.List;

public class BaseHttpResult {
    private List<String> variables;
    private Response response;
    public BaseHttpResult(Response response){
        super();
        this.variables = new ArrayList<String>();
        this.response = response;
    }

    public BaseHttpResult(ArrayList<String> variables, Response response){
        super();
        this.variables = variables;
        this.response = response;
    }

    public List<String> getVariables() {return variables;}

    public void setVariables(List<String> variables) {this.variables = variables;}

    public Response getResponse() {return response;}

    public void setResponse(Response response) {this.response = response;}

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "variables=" + variables +
                ", response=" + response +
                '}';
    }
}
