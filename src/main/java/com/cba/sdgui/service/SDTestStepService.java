package com.cba.sdgui.service;

import com.cba.sdgui.model.entity.SDTestStep;
import com.cba.sdgui.repository.SDTestStepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SDTestStepService extends AbstractServiceImpl<Integer, SDTestStep, SDTestStepRepository> {

    @Autowired
    private SDTestStepRepository sdTestStepRepository;

    @Override
    public SDTestStepRepository repository() {
        return sdTestStepRepository;
    }
}
