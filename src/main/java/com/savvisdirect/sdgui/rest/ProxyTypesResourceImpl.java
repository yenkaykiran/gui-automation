package com.savvisdirect.sdgui.rest;

import org.openqa.selenium.Proxy.ProxyType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/proxTypes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProxyTypesResourceImpl extends AbstractMetaResourceImpl<ProxyType> {

    @Override
    public void setType(ProxyType type) {
        
    }


}
