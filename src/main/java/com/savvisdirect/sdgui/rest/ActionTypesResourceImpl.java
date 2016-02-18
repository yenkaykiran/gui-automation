package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.enums.ActionType;

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