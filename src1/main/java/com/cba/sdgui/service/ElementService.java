package com.cba.sdgui.service;

import com.cba.sdgui.model.ElementModel;
import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.repository.ElementRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElementService extends AbstractServiceImpl<Integer, Element, ElementRepository> {

    @Autowired
    private ElementRepository elementRepository;

    @Override
    public ElementRepository repository() {
        return elementRepository;
    }

    public ElementModel save(ElementModel model) {
        Element fromDb = null;
        if (null != model.getId()) {
            fromDb = repository().findById(model.getId());
            //            Set<PageElement> existing = fromDb.getPageElements();
            //            if (null != existing && existing.size() > 0) {
            //                entity.getPageElements().addAll(existing);
            //            }
            if (null != fromDb) {
                fromDb.setName(model.getName());
                fromDb.setIdentity(model.getIdentity());
                fromDb.setIdentificationType(model.getIdentificationType());
            }
        }
        return getElementModel(repository().save(fromDb));
    }

    @Override
    public List<Element> findAll() {
        List<Element> all = super.findAll();
        return all;
    }

    public List<ElementModel> getElementModels(List<Element> all) {
        List<ElementModel> all1 = new ArrayList<ElementModel>();
        for (Element element : all) {
            ElementModel model = getElementModel(element);
            all1.add(model);
        }
        return all1;
    }

    public ElementModel getElementModel(Element element) {
        ElementModel model = new ElementModel();
        BeanUtils.copyProperties(element, model, "pageElements");
        return model;
    }
}
