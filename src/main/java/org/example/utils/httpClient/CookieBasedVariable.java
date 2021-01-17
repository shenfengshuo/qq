package org.example.utils.httpClient;

import org.apache.http.cookie.Cookie;
import org.apache.http.client.CookieStore;
import java.util.List;

public class CookieBasedVariable extends HttpClientVariable{
    public CookieBasedVariable(String name, String cookieKey){
    this.name = name;
    this.cookieKey = cookieKey;
    }

    String cookieKey;

    public boolean parse(Response response){
        CookieStore cookieStore = response.getCookieStore();
        String value = null;
        List<Cookie> cookies = cookieStore.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieKey)){
                System.out.println(("key:" + cookieKey + ",domain:" + cookie.getDomain()));
                value = cookie.getValue();
                break;
            }
        }
        super.setValue(value);
        return value != null;
    }
}
