package com.cba.sdgui.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.repository.ElementRepository;
import com.cba.sdgui.service.ElementService;

@RestController
@RequestMapping(value = "/elements", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ElementResourceImpl extends AbstractResourceImpl<Integer, Element, ElementRepository, ElementService> {

	@Autowired
	private ElementService elementService;

	@Override
	public ElementService getService() {
		return elementService;
	}
}
