package com.cba.sdgui.rest;

import com.cba.sdgui.enums.ExtractType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/extractTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ExtractTypesResourceImpl extends AbstractMetaResourceImpl<ExtractType> {

    public ExtractTypesResourceImpl() {
        super(ExtractType.TEXT);
    }
}