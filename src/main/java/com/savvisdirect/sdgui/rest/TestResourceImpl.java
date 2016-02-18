package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.model.entity.SDTest;
import com.savvisdirect.sdgui.repository.SDTestRepository;
import com.savvisdirect.sdgui.service.SDTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tests", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TestResourceImpl extends AbstractResourceImpl<Integer, SDTest, SDTestRepository, SDTestService> {

    @Autowired
    private SDTestService sdTestService;

    @Override
    public SDTestService getService() {
        return sdTestService;
    }
}
