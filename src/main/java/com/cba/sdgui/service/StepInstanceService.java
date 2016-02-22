package com.cba.sdgui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cba.sdgui.model.entity.StepInstance;
import com.cba.sdgui.model.entity.TestRun;
import com.cba.sdgui.repository.SDStepInstanceRepository;

@Service
public class StepInstanceService extends AbstractServiceImpl<Integer, StepInstance, SDStepInstanceRepository> {

	@Autowired
	private SDStepInstanceRepository stepInstanceRepository;

	@Override
	public SDStepInstanceRepository repository() {
		return stepInstanceRepository;
	}

	public List<StepInstance> findByRun(TestRun run) {
		return repository().findByRun(run);
	}
}
