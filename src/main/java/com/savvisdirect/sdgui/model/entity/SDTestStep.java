package com.savvisdirect.sdgui.model.entity;

import com.savvisdirect.sdgui.enums.ActionType;
import com.savvisdirect.sdgui.enums.IdentifyBy;
import com.savvisdirect.sdgui.enums.WaitType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
        @AttributeOverride(name = "elementIdentity", column = @Column(name = "element_identity")),
        @AttributeOverride(name = "identificationType", column = @Column(name = "identification_type")),
        @AttributeOverride(name = "isWait", column = @Column(name = "is_wait")),
        @AttributeOverride(name = "waitTime", column = @Column(name = "wait_time")),
        @AttributeOverride(name = "isAction", column = @Column(name = "is_action")),
        @AttributeOverride(name = "actionType", column = @Column(name = "action_type")),
        @AttributeOverride(name = "keys", column = @Column(name = "keys_to_send")),
        @AttributeOverride(name = "waitType", column = @Column(name = "wait_type"))
})
public class SDTestStep extends BaseEntity<Integer> implements Serializable {

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
    private SDTest test;

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
        if(null == waitTime || waitTime <= 0) {
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public SDTest getTest() {
        return test;
    }

    public void setTest(SDTest test) {
        this.test = test;
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
        SDTestStep rhs = (SDTestStep) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }
}
