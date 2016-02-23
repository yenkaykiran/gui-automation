package com.cba.sdgui.rest;

import com.cba.sdgui.enums.NavigationType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/navigationTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class NavigationTypesResourceImpl extends AbstractMetaResourceImpl<NavigationType> {

    public NavigationTypesResourceImpl() {
        super(NavigationType.Back);
    }
}