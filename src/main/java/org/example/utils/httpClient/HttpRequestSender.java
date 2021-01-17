package org.example.utils.httpClient;

//http请求发送器接口
public interface HttpRequestSender {
    public Response service(Request request);
}
