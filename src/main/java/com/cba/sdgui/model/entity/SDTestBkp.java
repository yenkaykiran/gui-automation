package com.cba.sdgui.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SDTestBkp extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 4289151143888117381L;

    private String name;
    private String description;
    private List<SDTestStepBkp> steps = new ArrayList<SDTestStepBkp>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SDTestStepBkp> getSteps() {
        return steps;
    }

    public void setSteps(List<SDTestStepBkp> steps) {
        this.steps = steps;
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
        SDTestBkp rhs = (SDTestBkp) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }
}
