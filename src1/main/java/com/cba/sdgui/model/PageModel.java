package com.cba.sdgui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageModel implements Serializable {

    private static final long serialVersionUID = 4289151143888117381L;

    private Integer id;
    private String name;
    private List<ElementModel> elements = new ArrayList<ElementModel>();

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ElementModel> getElements() {
        return elements;
    }

    public void setElements(List<ElementModel> elements) {
        this.elements = elements;
    }
}