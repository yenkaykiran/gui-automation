package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Proxy;
import com.cba.sdgui.repository.ProxyRepository;
import com.cba.sdgui.service.ProxyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/proxies", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProxyResourceImpl extends AbstractResourceImpl<Integer, Proxy, ProxyRepository, ProxyService> {

    @Autowired
    private ProxyService proxyService;

    @Override
    public ProxyService getService() {
        return proxyService;
    }
}
