package com.cba.sdgui.rest;

import com.cba.sdgui.enums.IdentifyBy;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/identificationTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class IdentificationTypesResourceImpl extends AbstractMetaResourceImpl<IdentifyBy> {

    public IdentificationTypesResourceImpl() {
        super(IdentifyBy.Class);
    }
}