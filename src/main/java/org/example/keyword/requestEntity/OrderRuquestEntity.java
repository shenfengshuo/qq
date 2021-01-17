package org.example.keyword.requestEntity;

public class OrderRuquestEntity extends BaseRequestEntity {
    private String orderContextId;
    private String userId;
    private String itemIdList;
    private String sceneCode;

    public String getOrderContextId() {
        return orderContextId;
    }

    public void setOrderContextId(String orderContextId) {
        this.orderContextId = orderContextId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(String itemIdList) {
        this.itemIdList = itemIdList;
    }

    public String getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }
}
