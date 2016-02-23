package com.cba.sdgui.rest;

import com.cba.sdgui.model.ElementModel;
import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.repository.ElementRepository;
import com.cba.sdgui.service.ElementService;
import com.cba.sdgui.service.PageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @ResponseBody
    public List<ElementModel> getAllElements() {
        List<Element> all = super.getAll();
        List<ElementModel> all1 = getService().getElementModels(all);
        return all1;
    }

    @Override
    public List<Element> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<Element> save(Element entity, HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Element>(entity, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/saveElement")
    @ResponseBody
    public ResponseEntity<ElementModel> savePage(@RequestBody ElementModel model, HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus responseStatus = null;
        try {
            if (null != model && null != model.getId()) {
                model = getService().save(model);
                responseStatus = HttpStatus.OK;
            }
        } catch (Exception e) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<ElementModel>(model, headers, responseStatus);
    }
}
