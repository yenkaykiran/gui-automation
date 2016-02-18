package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.model.entity.Url;
import com.savvisdirect.sdgui.repository.UrlRepository;
import com.savvisdirect.sdgui.service.UrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/urls", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UrlResourceImpl extends AbstractResourceImpl<Integer, Url, UrlRepository, UrlService> {

    @Autowired
    private UrlService urlService;

    @Override
    public UrlService getService() {
        return urlService;
    }
}
