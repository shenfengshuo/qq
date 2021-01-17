package org.example.keyword.resultEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseResultEntity {

    private String detailMessage;
    private String traceId;
    private Map<String, Object> detailObjects  = new LinkedHashMap<>();
    private List<TableObjectEntity> tableObjects = new ArrayList<>();
    private String errorCode;
    private String errorMsg;
    private boolean isSuccess = true;
    private String jsonData;
    private Map<String, String> moreDetailMessages = new LinkedHashMap<>();
    private String operator;
    private Map<String, Object> rawOutputs  = new LinkedHashMap<>();
    private Map<String, Object> usedVars  = new LinkedHashMap<>();

    public BaseResultEntity(){}

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getJsonData() {
        return jsonData;
    }

    public Map<String, String> getMoreDetailMessages() {
        return moreDetailMessages;
    }

    public String getOperator() {
        return operator;
    }

    public boolean getRawFieldBooleanValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return field.getBoolean(this);

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public int getRawFieldIntegerValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return field.getInt(this);

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public long getRawFieldLongValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return field.getLong(this);

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public Map getRawFieldMapValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return (Map)field.get(this);

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public String getRawFieldStringValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return String.valueOf(field.get(this));

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public String getRawFieldStringValueOrNull(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return field.get(this) == null ? "" : String.valueOf(field.get(this));

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public List<String> getRawFieldStringListValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return (List)field.get(this);

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public Object getRawFieldValue(String fieldName){
        try{
            Field field = getField(this.getClass(),fieldName);
            field.setAccessible(true);
            return field.get(this);

        } catch (Exception var3){
            throw new RuntimeException(fieldName + "字段值获取失败",var3);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException var4) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw var4;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }
    public static Method getMethod(Class<?> clazz, String mName) throws NoSuchMethodException,SecurityException {
        try {
            return clazz.getDeclaredMethod(mName);
        } catch (NoSuchMethodException var4) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw var4;
            } else {
                return getMethod(superClass, mName);
            }
        }
    }

    public Map<String, Object> getRawOutputs() {
        return rawOutputs;
    }

    public Map<String, Object> getUsedVars() {
        return usedVars;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public void setDetailObjects(Map<String, Object> detailObjects) {
        this.detailObjects = detailObjects;
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setMoreDetailMessages(Map<String, String> moreDetailMessages) {
        this.moreDetailMessages = moreDetailMessages;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    public void setRawOutputs(Map<String, Object> rawOutputs) {
        this.rawOutputs = rawOutputs;
    }
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public void setUsedVars(Map<String, Object> usedVars) {
        this.usedVars = usedVars;
    }

    public void setTableObjects(List<TableObjectEntity> tableObjects) {
        this.tableObjects = tableObjects;
    }
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceId(String traceId) {
        return traceId;
    }

    public List<TableObjectEntity> getTableObjects() {
        return tableObjects;
    }
}
