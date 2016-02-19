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

import com.savvisdirect.sdgui.enums.ProxyType;

@Entity
@Table(name = "proxy", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "id", insertable = false, updatable = false)),
        @AttributeOverride(name = "name", column = @Column(name = "name")),
        @AttributeOverride(name = "type", column = @Column(name = "type")),
        @AttributeOverride(name = "httpHost", column = @Column(name = "http_host")),
        @AttributeOverride(name = "httpPort", column = @Column(name = "http_port")),
        @AttributeOverride(name = "sslHost", column = @Column(name = "ssl_host")),
        @AttributeOverride(name = "sslPort", column = @Column(name = "ssl_port"))
})
public class Proxy extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 4289151143888117381L;

    private String name;
    private ProxyType type;
    private String httpHost;
    private Integer httpPort;
    private String sslHost;
    private Integer sslPort;

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

    public ProxyType getType() {
        if (null == type) {
            type = ProxyType.AUTODETECT;
        }
        return type;
    }

    public void setType(ProxyType type) {
        this.type = type;
    }

    public String getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public String getSslHost() {
        return sslHost;
    }

    public void setSslHost(String sslHost) {
        this.sslHost = sslHost;
    }

    public Integer getSslPort() {
        return sslPort;
    }

    public void setSslPort(Integer sslPort) {
        this.sslPort = sslPort;
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
        Proxy rhs = (Proxy) obj;
        return (new EqualsBuilder()).append(this.id, rhs.id).isEquals();
    }
}