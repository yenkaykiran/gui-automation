package com.savvisdirect.sdgui.model.entity;

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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.savvisdirect.sdgui.enums.BrowserType;

@Entity
@Table(name = "browsers", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "type", column = @Column(name = "type")),
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "driverPath", column = @Column(name = "driver_path"))
})
public class Browser extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 4289151143888117381L;

	private BrowserType type;
	private String name;
	private String driverPath;

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

	public BrowserType getType() {
		if (null == type) {
			type = BrowserType.Chrome;
		}
		return type;
	}

	public void setType(BrowserType type) {
		this.type = type;
	}

	public Integer getTypeIndex() {
		return getType().ordinal();
	}

	public void setTypeIndex(Integer type) {
		this.setType(BrowserType.values()[type]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverPath() {
		return driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
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
		Browser rhs = (Browser) obj;
		return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
	}
}
