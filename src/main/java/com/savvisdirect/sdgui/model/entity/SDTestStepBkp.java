package com.savvisdirect.sdgui.model.entity;

import com.savvisdirect.sdgui.enums.ActionType;
import com.savvisdirect.sdgui.enums.IdentifyBy;
import com.savvisdirect.sdgui.enums.WaitType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class SDTestStepBkp extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 4289151143888117381L;

    private String name;
    private String elementIdentity;
    private IdentifyBy identificationType;
    private Boolean isWait;
    private Integer waitTime;
    private Boolean isAction;
    private ActionType actionType;
    private String keys;
    private WaitType waitType;

    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElementIdentity() {
        return elementIdentity;
    }

    public void setElementIdentity(String elementIdentity) {
        this.elementIdentity = elementIdentity;
    }

    public IdentifyBy getIdentificationType() {
        if (null == identificationType) {
            identificationType = IdentifyBy.XPath;
        }
        return identificationType;
    }

    public void setIdentificationType(IdentifyBy identificationType) {
        this.identificationType = identificationType;
    }

    public Boolean getIsWait() {
        if (null == isWait) {
            isWait = false;
        }
        return isWait;
    }

    public void setIsWait(Boolean isWait) {
        this.isWait = isWait;
    }

    public Integer getWaitTime() {
        if (null == waitTime || waitTime <= 0) {
            waitTime = 1;
        }
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Boolean getIsAction() {
        if (null == isAction) {
            isAction = false;
        }
        return isAction;
    }

    public void setIsAction(Boolean isAction) {
        this.isAction = isAction;
    }

    public ActionType getActionType() {
        if (null == actionType) {
            actionType = ActionType.Click;
        }
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public WaitType getWaitType() {
        if (waitType == null) {
            waitType = WaitType.NONE;
        }
        return waitType;
    }

    public void setWaitType(WaitType waitType) {
        this.waitType = waitType;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int hashCode() {
        return (new HashCodeBuilder()).append(this.id).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SDTestStepBkp rhs = (SDTestStepBkp) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }
}
