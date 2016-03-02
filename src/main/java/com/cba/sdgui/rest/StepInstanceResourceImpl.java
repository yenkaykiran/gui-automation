package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Configuration;
import com.cba.sdgui.model.entity.StepInstance;
import com.cba.sdgui.model.entity.TestRun;
import com.cba.sdgui.repository.ConfigurationRepository;
import com.cba.sdgui.repository.SDStepInstanceRepository;
import com.cba.sdgui.service.SDTestService;
import com.cba.sdgui.service.StepInstanceService;
import com.cba.sdgui.service.TestRunService;

import org.apache.commons.lang3.StringUtils;
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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/stepInstances", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StepInstanceResourceImpl extends AbstractResourceImpl<Integer, StepInstance, SDStepInstanceRepository, StepInstanceService> {

    @Autowired
    private StepInstanceService stepInstanceService;

    @Autowired
    private SDTestService sdTestService;

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public StepInstanceService getService() {
        return stepInstanceService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/run/{id}")
    @ResponseBody
    public ResponseEntity<StepInstance> addStepInstance(@RequestBody StepInstance entity, @PathVariable("id") Integer id, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<StepInstance>(entity, headers, responseStatus);
    }

    @Override
    public ResponseEntity<StepInstance> save(StepInstance entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<StepInstance>(entity, headers, responseStatus);
    }

    @Override
    public List<StepInstance> getAll() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/run/{id}")
    @ResponseBody
    public List<StepInstance> getRunStepInstances(@PathVariable("id") Integer id) {
        List<StepInstance> stepInts = null;
        if (null != id) {
            TestRun run = testRunService.findById(id);
            if (null != run) {
                stepInts = stepInstanceService.findByRun(run);
                Collections.sort(stepInts);
                for (StepInstance sdTestStep : stepInts) {
                    sdTestStep.setTest(null);
                    sdTestStep.setRun(null);
                }
            }
        }
        return stepInts;
    }

    @Override
    public ResponseEntity<String> removeById(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("errorMessage", "BAD REQUEST, without test Id");
        return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/run/{runId}/{stepInstId}", produces = "image/png")
    @ResponseBody
    public BufferedImage getStepInstanceScreenshot(@PathVariable("runId") Integer runId, @PathVariable("stepInstId") Integer stepInstId) {
        List<StepInstance> stepInsts = null;
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;
        BufferedImage image = new BufferedImage(1, 1, 1);
        if (null != runId) {
            try {
                TestRun run = testRunService.findById(runId);
                if (null != run) {
                    stepInsts = stepInstanceService.findByRun(run);
                    int pos = getStepPosition(stepInsts, stepInstId);
                    if (pos >= 0) {
                        Configuration configurationScreenshotsBasePath = configurationRepository.findByName(Constants.SCREENSHOTS_BASE_PATH);
                        if (null != configurationScreenshotsBasePath && StringUtils.isNotBlank(configurationScreenshotsBasePath.getStrValue())) {
                            String path = configurationScreenshotsBasePath.getStrValue() + File.separator + run.getId() + File.separator + stepInsts.get(pos).getName() + ".png";
                            File file = new File(path);
                            if (file.exists()) {
                                headers.setContentType(MediaType.IMAGE_PNG);
                                InputStream is = new FileInputStream(file);

                                // Prepare buffered image.
                                BufferedImage img = ImageIO.read(is);

                                // Create a byte array output stream.
                                ByteArrayOutputStream bao = new ByteArrayOutputStream();

                                // Write to output stream
                                ImageIO.write(img, "png", bao);

                                image = ImageIO.read(file);
                                responseStatus = HttpStatus.OK;
                                is.close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                responseStatus = HttpStatus.BAD_REQUEST;
                headers.add("errorMessage", e.getMessage());
            }
        }
        return image;
    }

    private int getStepPosition(List<StepInstance> insts, Integer instId) {
        Integer position = -1;
        for (int i = 0; i < insts.size(); i++) {
            StepInstance eachStep = insts.get(i);
            if (eachStep.getId().equals(instId)) {
                position = i;
                break;
            }
        }
        return position;
    }
}
