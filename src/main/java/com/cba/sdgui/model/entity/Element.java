package com.cba.sdgui.model.entity;

import com.cba.sdgui.enums.IdentifyBy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "elements", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "identity", column = @Column(name = "identity")),
        @AttributeOverride(name = "identificationType", column = @Column(name = "identification_type"))
})
public class Element extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 4289151143888117381L;

    private String name;
    private String identity;
    private IdentifyBy identificationType;

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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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
        Element rhs = (Element) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }
}
