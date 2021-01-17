package org.example.utils.httpClient;

//httpClient请求中的变量基类
public class HttpClientVariable {
    String name;
    String value;

    public boolean parse(Response response){
        //该方法将由各子类具体实现
        return true;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }

}
