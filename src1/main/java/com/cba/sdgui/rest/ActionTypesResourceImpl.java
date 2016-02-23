package com.cba.sdgui.rest;

import com.cba.sdgui.enums.ActionType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/actionTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ActionTypesResourceImpl extends AbstractMetaResourceImpl<ActionType> {

    public ActionTypesResourceImpl() {
        super(ActionType.Click);
    }
}