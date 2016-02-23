package com.cba.sdgui.rest;

import com.cba.sdgui.model.PageModel;
import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.repository.PageRepository;
import com.cba.sdgui.service.ElementService;
import com.cba.sdgui.service.PageService;

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
@RequestMapping(value = "/pages", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PageResourceImpl extends AbstractResourceImpl<Integer, Page, PageRepository, PageService> {

    @Autowired
    private PageService pageService;

    @Override
    public PageService getService() {
        return pageService;
    }

    @Autowired
    private ElementService elementService;

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

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<PageModel> getAllPages() {
        List<Page> all = super.getAll();
        List<PageModel> all1 = new ArrayList<PageModel>();
        for (Page page : all) {
            PageModel model = getService().toPageModel(page);
            all1.add(model);
        }
        return all1;
    }

    @Override
    public List<Page> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<Page> save(Page entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Page>(entity, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/savePage")
    @ResponseBody
    public ResponseEntity<PageModel> savePage(@RequestBody PageModel model, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        try {
            model = getService().save(model);
            responseStatus = HttpStatus.OK;
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<PageModel>(model, headers, responseStatus);
    }
}
