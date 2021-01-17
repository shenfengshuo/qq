package org.example.utils.httpClient;

import java.util.HashMap;
import java.util.Map;

public class SofaContext {
    private static final ThreadLocal<SofaContext> sofaContextHolder = new ThreadLocal<>();
    protected Map<Object,Object> properties = new HashMap<>();

    public SofaContext(){}

    protected static SofaContext getInstance(Class clazz){
        if(clazz !=null && SofaContext.class.isAssignableFrom(clazz)){
            SofaContext sofaContext = (SofaContext) sofaContextHolder.get();

            try{
                if(sofaContext == null){
                    sofaContext = (SofaContext) clazz.newInstance();
                    sofaContextHolder.set(sofaContext);
                    return sofaContext;

                } else if(clazz.isAssignableFrom(sofaContext.getClass())){
                    return sofaContext;
                }else {
                    SofaContext newSofaContext = (SofaContext) clazz.newInstance();
                    newSofaContext.properties = sofaContext.properties;
                    sofaContextHolder.set(newSofaContext);
                    return newSofaContext;
                }
            } catch (InstantiationException var3){
                throw new RuntimeException("构造SofaContext出错",var3);
            } catch (IllegalAccessException var4){
                throw new RuntimeException("构造SofaContext出错,构造函数不能访问",var4);
            }
        } else {
            throw new IllegalArgumentException("IllegalArgument of method SofaContext.getInstance(class): " +clazz);
        }
    }

    public void clear(){this.properties = new HashMap<>();}

    public Object getProperty(Object key) {
        return this.properties.get(key);
    }

    public Object removeProperty(Object key) {
        return this.properties.remove(key);
    }

    public Object addProperty(Object key, Object value) {
        return this.properties.put(key, value);
    }

}
