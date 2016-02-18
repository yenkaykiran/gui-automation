package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.enums.WaitType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/waitTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class WaitTypesResourceImpl extends AbstractMetaResourceImpl<WaitType> {

    public WaitTypesResourceImpl() {
        super(WaitType.NONE);
    }
}