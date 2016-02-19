package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.SDTestStep;
import com.cba.sdgui.repository.SDTestStepRepository;
import com.cba.sdgui.service.SDTestService;
import com.cba.sdgui.service.SDTestStepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/steps", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StepsResourceImpl extends AbstractResourceImpl<Integer, SDTestStep, SDTestStepRepository, SDTestStepService> {

    @Autowired
    private SDTestStepService sdTestStepService;

    @Autowired
    private SDTestService sdTestService;

    @Override
    public SDTestStepService getService() {
        return sdTestStepService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/byTest/{id}")
    @ResponseBody
    public ResponseEntity<SDTestStep> saveStep(SDTestStep entity, @PathVariable("id") Integer id, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        try {
            if (null != id) {
                SDTest test = sdTestService.findById(id);
                if (null != test) {
                    entity.setTest(test);
                    if (null == entity.getId()) {
                        entity.setStepOrder(test.getSteps().size() + entity.getStepOrder());
                    }
                    entity = getService().save(entity);
                    responseStatus = HttpStatus.OK;
                } else {
                    responseStatus = HttpStatus.BAD_REQUEST;
                }
            } else {
                responseStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<SDTestStep>(entity, headers, responseStatus);
    }

    @Override
    public ResponseEntity<SDTestStep> save(SDTestStep entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<SDTestStep>(entity, headers, responseStatus);
    }

    @Override
    public List<SDTestStep> getAll() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byTest/{id}")
    @ResponseBody
    public List<SDTestStep> getTestSteps(@PathVariable("id") Integer id) {
        List<SDTestStep> steps = null;
        if (null != id) {
            SDTest test = sdTestService.findById(id);
            if (null != test) {
                steps = test.getSteps();
                Collections.sort(steps);
                for (SDTestStep sdTestStep : steps) {
                    sdTestStep.setTest(null);
                }
            }
        }
        return steps;
    }

    @Override
    public ResponseEntity<String> removeById(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("errorMessage", "BAD REQUEST, without test Id");
        return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/byTest/{id}/{stepId}")
    public ResponseEntity<String> removeStepById(@PathVariable("id") Integer id, @PathVariable("stepId") Integer stepId) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        if (null != id) {
            SDTest test = sdTestService.findById(id);
            if (null != test) {
                SDTestStep step = getService().findById(stepId);
                if (null == step) {
                    headers.add("errorMessage", "Entity with ID " + id + " Not Found");
                    responseStatus = HttpStatus.NOT_FOUND;
                } else {
                    try {
                        getService().delete(step);
                        responseStatus = HttpStatus.OK;
                    } catch (Exception e) {
                        headers.add("errorMessage", "Step with ID " + id + " cannot be Deleted");
                        responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    }
                }
            } else {
                headers.add("errorMessage", "Test with ID " + id + " Not Found");
                responseStatus = HttpStatus.NOT_FOUND;
            }
        }
        return new ResponseEntity<String>(headers, responseStatus);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/byTest/{id}/reorder")
    @ResponseBody
    public ResponseEntity<List<SDTestStep>> reorderSteps(HashMap<Integer, Integer> order, @PathVariable("id") Integer id, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        List<SDTestStep> stepsFromDb = null;
        try {
            if (null != id) {
                SDTest test = sdTestService.findById(id);
                if (null != test) {
                    stepsFromDb = test.getSteps();
                    for (SDTestStep sdTestStep : stepsFromDb) {
                        Integer orderFromClient = getStep(order, sdTestStep.getId());
                        if (null != orderFromClient) {
                            sdTestStep.setStepOrder(orderFromClient);
                        }
                    }
                    stepsFromDb = getService().saveAll(stepsFromDb);
                    for (SDTestStep sdTestStep : stepsFromDb) {
                        sdTestStep.setTest(null);
                    }
                    responseStatus = HttpStatus.OK;
                } else {
                    responseStatus = HttpStatus.BAD_REQUEST;
                }
            } else {
                responseStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<SDTestStep>>(stepsFromDb, headers, responseStatus);
    }

    private Integer getStep(HashMap<Integer, Integer> order, Integer id) {
        Set<Integer> ids = order.keySet();
        for (Integer stepId : ids) {
            if (stepId == id) {
                return order.get(stepId);
            }
        }
        return null;
    }
}
