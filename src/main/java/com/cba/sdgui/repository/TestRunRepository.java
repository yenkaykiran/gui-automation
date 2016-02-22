package com.cba.sdgui.repository;

import java.util.List;

import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.TestRun;

public interface TestRunRepository extends BaseRepository<TestRun, Integer> {

	List<TestRun> findByTest(SDTest test);
}
