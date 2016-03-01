package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Configuration;
import com.cba.sdgui.repository.ConfigurationRepository;
import com.cba.sdgui.service.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/settings", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SettingsResourceImpl extends AbstractResourceImpl<Integer, Configuration, ConfigurationRepository, ConfigurationService> {

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public ConfigurationService getService() {
        return configurationService;
    }
}
