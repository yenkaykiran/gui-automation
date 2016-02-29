package com.cba.sdgui.model.entity;

import com.cba.sdgui.enums.ActionType;
import com.cba.sdgui.enums.ExtractType;
import com.cba.sdgui.enums.WaitType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "sd_test_step", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "isWait", column = @Column(name = "is_wait")),
        @AttributeOverride(name = "waitTime", column = @Column(name = "wait_time")),
        @AttributeOverride(name = "isAction", column = @Column(name = "is_action")),
        @AttributeOverride(name = "actionType", column = @Column(name = "action_type")),
        @AttributeOverride(name = "keys", column = @Column(name = "keys_to_send")),
        @AttributeOverride(name = "waitType", column = @Column(name = "wait_type")),
        @AttributeOverride(name = "stepOrder", column = @Column(name = "step_order")),
        @AttributeOverride(name = "needVerification", column = @Column(name = "need_verification")),
        @AttributeOverride(name = "visibility", column = @Column(name = "visibility")),
        @AttributeOverride(name = "enabledisable", column = @Column(name = "enabledisable")),
        @AttributeOverride(name = "extractData", column = @Column(name = "extract_data")),
        @AttributeOverride(name = "extractType", column = @Column(name = "extract_type"))
})
public class SDTestStep extends BaseEntity<Integer> implements Serializable, Comparable<SDTestStep> {

    private static final long serialVersionUID = 4289151143888117381L;

    private String name;
    private Boolean isWait;
    private Integer waitTime;
    private Boolean isAction;
    private ActionType actionType;
    private String keys;
    private WaitType waitType;
    private Integer stepOrder = 1;
    private SDTest test;
    private Boolean needVerification;
    private Boolean visibility;
    private Boolean enabledisable;
    private Page page;
    private Element element;
    private Boolean extractData;
    private ExtractType extractType;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public SDTest getTest() {
        return test;
    }

    public void setTest(SDTest test) {
        this.test = test;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
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
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
        SDTestStep rhs = (SDTestStep) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }

    @Override
    public int compareTo(SDTestStep o) {
        int result = 0;
        if (this.getStepOrder() != null && o.getStepOrder() != null) {
            result = this.getStepOrder() - o.getStepOrder();
        }
        return result;
    }
}
