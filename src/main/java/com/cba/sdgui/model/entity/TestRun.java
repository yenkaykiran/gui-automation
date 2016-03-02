package com.cba.sdgui.model.entity;

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
@Table(name = "sd_test_run", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "withProxy", column = @Column(name = "with_proxy")),
        @AttributeOverride(name = "exception", column = @Column(name = "exception"))
})
public class TestRun extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 4289151143888117381L;

    private Boolean withProxy;
    private String exception;
    private Url url;
    private Browser browser;
    private Proxy proxy;
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

    public Boolean getWithProxy() {
        return withProxy;
    }

    @Lob
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    public Browser getBrowser() {
        return browser;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    public Proxy getProxy() {
        return proxy;
    }

    public void setWithProxy(Boolean withProxy) {
        this.withProxy = withProxy;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public SDTest getTest() {
        return test;
    }

    public void setTest(SDTest test) {
        this.test = test;
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
        TestRun rhs = (TestRun) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }
}
