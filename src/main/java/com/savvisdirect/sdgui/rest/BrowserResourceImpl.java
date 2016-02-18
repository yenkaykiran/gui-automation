package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.model.entity.Browser;
import com.savvisdirect.sdgui.repository.BrowsersRepository;
import com.savvisdirect.sdgui.service.BrowsersService;

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
