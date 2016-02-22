package com.cba.sdgui.repository;

import java.util.List;

import com.cba.sdgui.model.entity.StepInstance;
import com.cba.sdgui.model.entity.TestRun;

public interface SDStepInstanceRepository extends BaseRepository<StepInstance, Integer> {

	public List<StepInstance> findByRun(TestRun run);
}
