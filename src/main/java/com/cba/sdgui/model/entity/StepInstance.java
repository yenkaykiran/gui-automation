package com.cba.sdgui.model.entity;

import com.cba.sdgui.enums.ActionType;
import com.cba.sdgui.enums.ExtractType;
import com.cba.sdgui.enums.IdentifyBy;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "sd_test_inst", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
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
        @AttributeOverride(name = "waitType", column = @Column(name = "wait_type")),
        @AttributeOverride(name = "stepOrder", column = @Column(name = "step_order")),
        @AttributeOverride(name = "stepId", column = @Column(name = "step_id")),
        @AttributeOverride(name = "timeConsumed", column = @Column(name = "time_consumed")),
        @AttributeOverride(name = "status", column = @Column(name = "status")),
        @AttributeOverride(name = "exception", column = @Column(name = "exception")),
        @AttributeOverride(name = "needVerification", column = @Column(name = "need_verification")),
        @AttributeOverride(name = "visibilityCheck", column = @Column(name = "visibility_check")),
        @AttributeOverride(name = "enableCheck", column = @Column(name = "enable_check")),
        @AttributeOverride(name = "extractData", column = @Column(name = "extract_data")),
        @AttributeOverride(name = "extractType", column = @Column(name = "extract_type")),
        @AttributeOverride(name = "extractedData", column = @Column(name = "extracted_data")),
        @AttributeOverride(name = "captureScreenshot", column = @Column(name = "capture_screenshot"))
})
public class StepInstance extends BaseEntity<Integer> implements Serializable, Comparable<StepInstance> {

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
	private Integer stepOrder = 1;
	private SDTest test;
	private Integer stepId;
	private Long timeConsumed;
	private String status;
	private String exception;
	private TestRun run;
	private Boolean needVerification;
	private String visibilityCheck;
    private String enableCheck;
    private Boolean extractData;
    private ExtractType extractType;
    private String extractedData;
    private Boolean captureScreenshot;

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

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	public SDTest getTest() {
		return test;
	}

	public void setTest(SDTest test) {
		this.test = test;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Long getTimeConsumed() {
		return timeConsumed;
	}

	public String getStatus() {
		return status;
	}

	@Lob
	public String getException() {
		return exception;
	}

	public void setTimeConsumed(Long timeConsumed) {
		this.timeConsumed = timeConsumed;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	public TestRun getRun() {
		return run;
	}

	public void setRun(TestRun run) {
		this.run = run;
	}

	public Boolean getNeedVerification() {
        return needVerification;
    }

    public void setNeedVerification(Boolean needVerification) {
        this.needVerification = needVerification;
    }

    public String getVisibilityCheck() {
        return visibilityCheck;
    }

    public void setVisibilityCheck(String visibilityCheck) {
        this.visibilityCheck = visibilityCheck;
    }

    public String getEnableCheck() {
        return enableCheck;
    }

    public void setEnableCheck(String enableCheck) {
        this.enableCheck = enableCheck;
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

    @Lob
    public String getExtractedData() {
        return extractedData;
    }

    public void setExtractedData(String extractedData) {
        this.extractedData = extractedData;
    }

    public Boolean getCaptureScreenshot() {
        return captureScreenshot;
    }

    public void setCaptureScreenshot(Boolean captureScreenshot) {
        this.captureScreenshot = captureScreenshot;
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
		StepInstance rhs = (StepInstance) obj;
		return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
	}

	@Override
	public int compareTo(StepInstance o) {
		int result = 0;
		if (this.getStepOrder() != null && o.getStepOrder() != null) {
			result = this.getStepOrder() - o.getStepOrder();
		}
		return result;
	}
}