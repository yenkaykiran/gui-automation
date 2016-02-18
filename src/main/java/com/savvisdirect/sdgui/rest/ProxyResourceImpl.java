package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.model.entity.Proxy;
import com.savvisdirect.sdgui.repository.ProxyRepository;
import com.savvisdirect.sdgui.service.ProxyService;

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
