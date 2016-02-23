package com.cba.sdgui.rest;

import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.repository.ElementRepository;
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/elements", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ElementResourceImpl extends AbstractResourceImpl<Integer, Element, ElementRepository, ElementService> {

    @Autowired
    private ElementService elementService;

    @Override
    public ElementService getService() {
        return elementService;
    }

    @Autowired
    private PageService pageService;

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/byPage/{id}")
    @ResponseBody
    public ResponseEntity<Element> saveStep(@RequestBody Element entity, @PathVariable("id") Integer id, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        try {
            if (null != id) {
                Page page = pageService.findById(id);
                if (null != page) {
                    entity.setPage(page);
                    entity = getService().save(entity);
                    entity.setPage(null);
                    responseStatus = HttpStatus.OK;
                } else {
                    responseStatus = HttpStatus.BAD_REQUEST;
                }
            } else {
                responseStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Element>(entity, headers, responseStatus);
    }

    @Override
    public ResponseEntity<Element> save(Element entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<Element>(entity, headers, responseStatus);
    }

    @Override
    public List<Element> getAll() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byPage/{id}")
    @ResponseBody
    public List<Element> getTestSteps(@PathVariable("id") Integer id) {
        List<Element> steps = null;
        if (null != id) {
            Page page = pageService.findById(id);
            if (null != page) {
                steps = page.getElements();
                for (Element element : steps) {
                    element.setPage(null);
                }
            }
        }
        return steps;
    }

    @Override
    public ResponseEntity<String> removeById(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("errorMessage", "BAD REQUEST, without page Id");
        return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/byPage/{id}/{elementId}")
    public ResponseEntity<String> removeStepById(@PathVariable("id") Integer id, @PathVariable("elementId") Integer stepId) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        if (null != id) {
            Page page = pageService.findById(id);
            if (null != page) {
                Element element = getService().findById(stepId);
                if (null == element) {
                    headers.add("errorMessage", "Entity with ID " + id + " Not Found");
                    responseStatus = HttpStatus.NOT_FOUND;
                } else {
                    try {
                        getService().delete(element);
                        page.getElements().remove(element);
                        pageService.save(page);
                        responseStatus = HttpStatus.OK;
                    } catch (Exception e) {
                        headers.add("errorMessage", "Step with ID " + id + " cannot be Deleted");
                        responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    }
                }
            } else {
                headers.add("errorMessage", "Test with ID " + id + " Not Found");
                responseStatus = HttpStatus.NOT_FOUND;
            }
        }
        return new ResponseEntity<String>(headers, responseStatus);
    }
}
