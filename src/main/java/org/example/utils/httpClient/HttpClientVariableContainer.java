package org.example.utils.httpClient;

import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//httpClient请求自动化测试参数容器
public class HttpClientVariableContainer {
        //请求链路参数容器
        public List<HttpClientVariable> globalVariableContainer = new ArrayList<>();
        //请求链路参数map
        public Map<String, HttpClientVariable> map = new HashMap<>();
        //
        public Response lastResponse;
        //
        public CookieStore cookieStore;

        //增加参数,将同时增加至擦部署容器和参数表中
        public void addVariable(HttpClientVariable variable){
            //将增加的参数放入参数容器
            this.globalVariableContainer.add(variable);
            //将增加的参数放入参数map中,key为参数名
            this.map.put(variable.getName(),variable);
        }

        //根据参数名称获取参数
        public  HttpClientVariable getVariableByName(String name) {
            return this.map.get(name);
        }

    public Response getLastResponse() {
        return this.lastResponse;
    }

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }

    public CookieStore getCookieStore() {
        return this.cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }


    //清空容器
    public void clear(){
            this.globalVariableContainer = new ArrayList<HttpClientVariable>();
            this.map = new HashMap<String,HttpClientVariable>();
            this.lastResponse = null;
            this.cookieStore = null;
    }
}
