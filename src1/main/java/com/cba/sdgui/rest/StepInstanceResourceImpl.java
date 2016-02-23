package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.StepInstance;
import com.cba.sdgui.model.entity.TestRun;
import com.cba.sdgui.repository.SDStepInstanceRepository;
import com.cba.sdgui.service.SDTestService;
import com.cba.sdgui.service.StepInstanceService;
import com.cba.sdgui.service.TestRunService;

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

import java.util.Collections;
import java.util.List;

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

	@Override
	public StepInstanceService getService() {
		return stepInstanceService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/run/{id}")
	@ResponseBody
	public ResponseEntity<StepInstance> addStepInstance(@RequestBody StepInstance entity, @PathVariable("id") Integer id, HttpServletRequest httpRequest) {
		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
//		try {
//			if (null != id) {
//				SDTest test = sdTestService.findById(id);
//				if (null != test) {
//					entity.setTest(test);
//					if (null == entity.getId()) {
//						entity.setStepOrder(test.getSteps().size() + entity.getStepOrder());
//					}
//					entity = getService().save(entity);
//					entity.setTest(null);
//					responseStatus = HttpStatus.OK;
//				} else {
//					responseStatus = HttpStatus.BAD_REQUEST;
//				}
//			} else {
//				responseStatus = HttpStatus.BAD_REQUEST;
//			}
//		} catch (Exception e) {
//			responseStatus = HttpStatus.BAD_REQUEST;
//		}
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/byTest/{id}/{stepId}")
	public ResponseEntity<String> removeStepById(@PathVariable("id") Integer id, @PathVariable("stepId") Integer stepId) {
		HttpHeaders headers = new HttpHeaders();
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
		// if (null != id) {
		// SDTest test = sdTestService.findById(id);
		// if (null != test) {
		// StepInstance step = getService().findById(stepId);
		// if (null == step) {
		// headers.add("errorMessage", "Entity with ID " + id + " Not Found");
		// responseStatus = HttpStatus.NOT_FOUND;
		// } else {
		// try {
		// getService().delete(step);
		// test.getSteps().remove(step);
		// sdTestService.save(test);
		// responseStatus = HttpStatus.OK;
		// } catch (Exception e) {
		// headers.add("errorMessage", "Step with ID " + id +
		// " cannot be Deleted");
		// responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		// }
		// }
		// } else {
		// headers.add("errorMessage", "Test with ID " + id + " Not Found");
		// responseStatus = HttpStatus.NOT_FOUND;
		// }
		// }
		return new ResponseEntity<String>(headers, responseStatus);
	}
}
