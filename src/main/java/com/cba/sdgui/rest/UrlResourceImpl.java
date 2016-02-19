package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Url;
import com.cba.sdgui.repository.UrlRepository;
import com.cba.sdgui.service.UrlService;

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
