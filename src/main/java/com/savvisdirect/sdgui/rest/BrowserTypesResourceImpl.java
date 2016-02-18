package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.enums.BrowserType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/browserTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class BrowserTypesResourceImpl extends AbstractMetaResourceImpl<BrowserType> {

    public BrowserTypesResourceImpl() {
        super(BrowserType.Chrome);
    }
}