package com.cba.sdgui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.TestRun;
import com.cba.sdgui.repository.TestRunRepository;

@Service
public class TestRunService extends AbstractServiceImpl<Integer, TestRun, TestRunRepository> {

	@Autowired
	private TestRunRepository testRunRepository;

	@Override
	public TestRunRepository repository() {
		return testRunRepository;
	}

	public List<TestRun> findByTest(SDTest test) {
		return repository().findByTest(test);
	}
}
