package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Browser;
import com.cba.sdgui.repository.BrowsersRepository;
import com.cba.sdgui.service.BrowsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/browsers", produces = { MediaType.APPLICATION_JSON_VALUE })
public class BrowserResourceImpl extends AbstractResourceImpl<Integer, Browser, BrowsersRepository, BrowsersService> {

    @Autowired
    private BrowsersService browsersService;

    @Override
    public BrowsersService getService() {
        return browsersService;
    }
}
