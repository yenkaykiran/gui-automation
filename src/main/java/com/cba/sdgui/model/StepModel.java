package com.cba.sdgui.model;

import com.cba.sdgui.enums.ActionType;
import com.cba.sdgui.enums.ExtractType;
import com.cba.sdgui.enums.WaitType;

import java.io.Serializable;

public class StepModel implements Serializable, Comparable<StepModel> {

    private static final long serialVersionUID = 4289151143888117381L;

    private Integer id;
    private Boolean enabled;
    private String name;
    private Boolean isWait;
    private Integer waitTime;
    private Boolean isAction;
    private ActionType actionType;
    private String keys;
    private WaitType waitType;
    private Integer stepOrder = 1;
    private Boolean needVerification;
    private Boolean visibility;
    private Boolean enabledisable;
    private Integer page;
    private Integer element;
    private Boolean extractData;
    private ExtractType extractType;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Boolean getNeedVerification() {
        return needVerification;
    }

    public void setNeedVerification(Boolean needVerification) {
        this.needVerification = needVerification;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Boolean getEnabledisable() {
        return enabledisable;
    }

    public void setEnabledisable(Boolean enabledisable) {
        this.enabledisable = enabledisable;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getElement() {
        return element;
    }

    public void setElement(Integer element) {
        this.element = element;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getExtractData() {
        return extractData;
    }

    public void setExtractData(Boolean extractData) {
        this.extractData = extractData;
    }

    public ExtractType getExtractType() {
        return extractType;
    }

    public void setExtractType(ExtractType extractType) {
        this.extractType = extractType;
    }

    @Override
    public int compareTo(StepModel o) {
        int result = 0;
        if (this.getStepOrder() != null && o.getStepOrder() != null) {
            result = this.getStepOrder() - o.getStepOrder();
        }
        return result;
    }
}
