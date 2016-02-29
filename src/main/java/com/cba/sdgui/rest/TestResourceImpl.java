package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Browser;
import com.cba.sdgui.model.entity.Proxy;
import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.SDTestStep;
import com.cba.sdgui.model.entity.TestRun;
import com.cba.sdgui.model.entity.TestRunModel;
import com.cba.sdgui.model.entity.Url;
import com.cba.sdgui.repository.SDTestRepository;
import com.cba.sdgui.service.BrowsersService;
import com.cba.sdgui.service.ProxyService;
import com.cba.sdgui.service.RunHelperService;
import com.cba.sdgui.service.SDTestService;
import com.cba.sdgui.service.TestRunService;
import com.cba.sdgui.service.UrlService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/tests", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TestResourceImpl extends AbstractResourceImpl<Integer, SDTest, SDTestRepository, SDTestService> {

    @Autowired
    private SDTestService sdTestService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private BrowsersService browsersService;

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private RunHelperService runHelperService;

    @Override
    public SDTestService getService() {
        return sdTestService;
    }

    @Override
    public ResponseEntity<SDTest> save(@RequestBody SDTest entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        if (null != entity.getId()) {
            SDTest fromDb = getService().findById(entity.getId());
            if (null != fromDb) {
                entity.setSteps(fromDb.getSteps());
            }
        }
        try {
            entity = getService().save(entity);
            entity.setSteps(null);
            responseStatus = HttpStatus.OK;
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<SDTest>(entity, headers, responseStatus);
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

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/{id}/runner")
    @ResponseBody
    public ResponseEntity<List<SDTestStep>> runTest(@RequestBody TestRunModel testRunRequest, @PathVariable("id") Integer id, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        List<SDTestStep> stepsFromDb = null;
        try {
            if (null != id) {
                SDTest test = getService().findById(id);
                if (null != test) {
                    TestRun newRun = new TestRun();
                    Url url = urlService.findById(testRunRequest.getUrl());
                    if (null != url) {
                        newRun.setUrl(url);
                    }
                    Browser browser = browsersService.findById(testRunRequest.getBrowser());
                    if (null != browser) {
                        newRun.setBrowser(browser);
                    }
                    if (null != testRunRequest.getWithProxy() && testRunRequest.getWithProxy() == true) {
                        Proxy proxy = proxyService.findById(testRunRequest.getProxy());
                        if (null != proxy) {
                            newRun.setWithProxy(true);
                            newRun.setProxy(proxy);
                        }
                    }
                    newRun.setTest(test);
                    testRunService.save(newRun);
                    runHelperService.startTest(id, newRun, testRunRequest.getHeadless(), testRunRequest.getMaximizeWindow() != null && testRunRequest.getMaximizeWindow());
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

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/runner")
    @ResponseBody
    public List<TestRunModel> getTestSteps(@PathVariable("id") Integer id) {
        List<TestRunModel> runRequests = new ArrayList<TestRunModel>();
        if (null != id) {
            SDTest test = getService().findById(id);
            if (null != test) {
                List<TestRun> runs = testRunService.findByTest(test);
                if (null != runs && runs.size() > 0) {
                    for (TestRun run : runs) {
                        runRequests.add(toRunModel(run));
                    }
                }
            }
        }
        return runRequests;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/copy")
    @ResponseBody
    public ResponseEntity<SDTest> copyTestWithSteps(@PathVariable("id") Integer testId, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        SDTest entity = new SDTest();
        if (null != testId) {
            SDTest fromDb = getService().findById(testId);
            if (null != fromDb.getId()) {
                try {
                    BeanUtils.copyProperties(fromDb, entity, "steps");
                    entity.setId(null);
                    for (SDTestStep eachStep : fromDb.getSteps()) {
                        eachStep.setId(null);
                        eachStep.setTest(entity);
                        entity.getSteps().add(eachStep);
                    }

                    entity.setName(fromDb.getName() + "_COPIED");
                    entity = getService().save(entity);
                    entity.setSteps(null);
                    responseStatus = HttpStatus.OK;
                } catch (Exception e) {
                    responseStatus = HttpStatus.BAD_REQUEST;
                }
            }
        }
        return new ResponseEntity<SDTest>(entity, headers, responseStatus);
    }

    private TestRunModel toRunModel(TestRun run) {
        TestRunModel model = new TestRunModel();
        model.setBrowser(run.getBrowser().getId());
        model.setId(run.getId());
        model.setWithProxy(run.getWithProxy());
        if (null != model.getWithProxy() && model.getWithProxy() == true) {
            model.setProxy(run.getProxy().getId());
        }
        model.setUrl(run.getUrl().getId());
        return model;
    }
}
