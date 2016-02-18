package com.savvisdirect.sdgui.rest;

import com.savvisdirect.sdgui.model.entity.SDTestStep;
import com.savvisdirect.sdgui.repository.SDTestStepRepository;
import com.savvisdirect.sdgui.service.SDTestStepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/steps", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StepsResourceImpl extends AbstractResourceImpl<Integer, SDTestStep, SDTestStepRepository, SDTestStepService> {

    @Autowired
    private SDTestStepService sdTestStepService;

    @Override
    public SDTestStepService getService() {
        return sdTestStepService;
    }
}
