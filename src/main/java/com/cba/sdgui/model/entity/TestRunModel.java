package com.cba.sdgui.model.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestRunModel implements Serializable {

	private static final long serialVersionUID = 4289151143888117381L;

	private Boolean withProxy;
	private Integer id;
	private Integer url;
	private Integer browser;
	private Integer proxy;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUrl() {
		return url;
	}

	public void setUrl(Integer url) {
		this.url = url;
	}

	public Boolean getWithProxy() {
		return withProxy;
	}

	public Integer getBrowser() {
		return browser;
	}

	public Integer getProxy() {
		return proxy;
	}

	public void setWithProxy(Boolean withProxy) {
		this.withProxy = withProxy;
	}

	public void setBrowser(Integer browser) {
		this.browser = browser;
	}

	public void setProxy(Integer proxy) {
		this.proxy = proxy;
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
		TestRunModel rhs = (TestRunModel) obj;
		return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
	}
}
