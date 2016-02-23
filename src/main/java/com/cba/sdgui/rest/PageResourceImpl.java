package com.cba.sdgui.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.repository.PageRepository;
import com.cba.sdgui.service.PageService;

@RestController
@RequestMapping(value = "/pages", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PageResourceImpl extends AbstractResourceImpl<Integer, Page, PageRepository, PageService> {

	@Autowired
	private PageService pageService;

	@Override
	public PageService getService() {
		return pageService;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{pageId}/element/{elementId}")
	public ResponseEntity<String> removePageElementById(@PathVariable("pageId") int pageId, @PathVariable("elementId") int elementId) {
		Page item = getService().findById(pageId);
		HttpHeaders headers = new HttpHeaders();
		if (null == item) {
			headers.add("errorMessage", "Page with ID " + pageId + " Not Found");
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} else {
			try {
				getService().removePageElementById(item, elementId);
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "Page Element with ID " + elementId + " cannot be Removed");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
}
