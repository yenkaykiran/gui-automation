package com.cba.sdgui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cba.sdgui.model.entity.Browser;
import com.cba.sdgui.repository.BrowsersRepository;

@Service
public class BrowsersService extends AbstractServiceImpl<Integer, Browser, BrowsersRepository> {

	@Autowired
	private BrowsersRepository browsersRepository;

	@Override
	public BrowsersRepository repository() {
		return browsersRepository;
	}
}
