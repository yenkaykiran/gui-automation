package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.repository.PageRepository;
import com.cba.sdgui.service.PageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public ResponseEntity<Page> save(@RequestBody Page entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        if (null != entity.getId()) {
            Page fromDb = getService().findById(entity.getId());
            if (null != fromDb) {
                entity.setElements(fromDb.getElements());
            }
        }
        try {
            entity = getService().save(entity);
            entity.setElements(null);
            responseStatus = HttpStatus.OK;
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Page>(entity, headers, responseStatus);
    }

    @Override
    public Page getById(Integer id) {
        Page fromDb = super.getById(id);
        fromDb.setElements(null);
        return fromDb;
    }

    @Override
    public List<Page> getAll() {
        List<Page> all = super.getAll();
        for (Page page : all) {
            page.setElements(null);
        }
        return all;
    }
}
