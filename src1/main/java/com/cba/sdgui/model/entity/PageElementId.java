package com.cba.sdgui.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PageElementId implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7630068430046310767L;

    private Element element;

    private Page page;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageElementId other = (PageElementId) obj;
        if (element == null) {
            if (other.element != null)
                return false;
        } else if (!element.equals(other.element))
            return false;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        return false;
    }

    @ManyToOne
    public Element getElement() {
        return element;
    }

    @ManyToOne
    public Page getPage() {
        return page;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((element == null) ? 0 : element.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        return result;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
