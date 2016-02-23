package com.cba.sdgui.rest;

import org.openqa.selenium.Proxy.ProxyType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meta/proxyTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProxyTypesResourceImpl extends AbstractMetaResourceImpl<ProxyType> {

    public ProxyTypesResourceImpl() {
        super(ProxyType.MANUAL);
    }
}