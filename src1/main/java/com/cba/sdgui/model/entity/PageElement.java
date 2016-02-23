package com.cba.sdgui.model.entity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "page_element")
@AssociationOverrides({
    @AssociationOverride(name = "id.page", joinColumns = @JoinColumn(name = "page_id", insertable = false, updatable = false)),
    @AssociationOverride(name = "id.element", joinColumns = @JoinColumn(name = "element_id", insertable = false, updatable = false))
})
public class PageElement extends BaseEntity<PageElementId> implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4435473629958196511L;

    public PageElement() {
        this.id = new PageElementId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageElement other = (PageElement) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Transient
    public Element getElement() {
        return getId().getElement();
    }

    @Override
    @EmbeddedId
    public PageElementId getId() {
        return id;
    }

    @Transient
    public Page getPage() {
        return getId().getPage();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public void setElement(Element element) {
        this.getId().setElement(element);
    }

    @Override
    public void setId(PageElementId id) {
        this.id = id;
    }

    public void setPage(Page page) {
        this.getId().setPage(page);
    }
}
