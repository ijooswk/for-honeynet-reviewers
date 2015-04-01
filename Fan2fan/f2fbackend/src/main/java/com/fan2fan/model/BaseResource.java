package com.fan2fan.model;

/**
 * Basic Model of all resources model.
 * Contains some attrs that would be used for most resource models.
 *
 * @author huangsz
 */
public abstract class BaseResource extends BaseModel {

    /**
     * who is operating the resource, usually it's the user id
     */
    private Long operatorId;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}
