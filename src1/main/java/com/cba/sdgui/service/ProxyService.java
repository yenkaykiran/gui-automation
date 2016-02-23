package com.cba.sdgui.service;

import com.cba.sdgui.model.entity.Proxy;
import com.cba.sdgui.repository.ProxyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProxyService extends AbstractServiceImpl<Integer, Proxy, ProxyRepository> {

    @Autowired
    private ProxyRepository proxyRepository;

    @Override
    public ProxyRepository repository() {
        return proxyRepository;
    }

    public Proxy getFirst() {
        List<com.cba.sdgui.model.entity.Proxy> all = repository().findAll();
        com.cba.sdgui.model.entity.Proxy proxyDetails = null;
        if (all.size() > 0) {
            proxyDetails = all.get(0);
        } else {
            proxyDetails = new com.cba.sdgui.model.entity.Proxy();
        }
        return proxyDetails;
    }

}
