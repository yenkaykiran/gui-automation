package com.cba.sdgui.model;

import com.cba.sdgui.enums.IdentifyBy;

public class ElementModel {

    private Integer id;

    public Integer getId() {
        return id;
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public IdentifyBy getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentifyBy identificationType) {
        this.identificationType = identificationType;
    }

    private String name;
    private String identity;
    private IdentifyBy identificationType;

}
