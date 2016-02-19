package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.repository.SDTestRepository;
import com.cba.sdgui.service.SDTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/tests", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TestResourceImpl extends AbstractResourceImpl<Integer, SDTest, SDTestRepository, SDTestService> {

    @Autowired
    private SDTestService sdTestService;

    @Override
    public SDTestService getService() {
        return sdTestService;
    }

    @Override
    public ResponseEntity<SDTest> save(SDTest entity, HttpServletRequest httpRequest) {
        if (null != entity.getId()) {
            SDTest fromDb = getService().findById(entity.getId());
            if (null != fromDb) {
                entity.setSteps(fromDb.getSteps());
            }
        }
        ResponseEntity<SDTest> superResponse = super.save(entity, httpRequest);
        entity.setSteps(null);
        return superResponse;
    }

    @Override
    public SDTest getById(Integer id) {
        SDTest fromDb = super.getById(id);
        fromDb.setSteps(null);
        return fromDb;
    }

    @Override
    public List<SDTest> getAll() {
        List<SDTest> all = super.getAll();
        for (SDTest sdTest : all) {
            sdTest.setSteps(null);
        }
        return all;
    }
}
