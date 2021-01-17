package org.example.utils.httpClient;

import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpContainerCacher {
    private static Map<String, HttpClientVariableContainer> cacheContainers = new LinkedHashMap<>();
    public synchronized static void remove(String key) {
        cacheContainers.remove(key);
    }
    public synchronized static void put(String key,HttpClientVariableContainer driver){
        if(cacheContainers.containsKey(key)){
            return;
        }

        List<String> keyList = new ArrayList<>();
        for(String k : cacheContainers.keySet()){
            keyList.add(k);
        }

        //只会缓存58个容器
        for(int i = 0;i < keyList.size() - 50; i++){
            cacheContainers.remove(keyList.get(i));
        }

        cacheContainers.put(key,driver);

    }

    public static HttpClientVariableContainer get(String key) {return cacheContainers.get(key);}
}
