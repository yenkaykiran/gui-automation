package com.cba.sdgui.service;

import com.cba.sdgui.model.ElementModel;
import com.cba.sdgui.model.PageModel;
import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.model.entity.PageElement;
import com.cba.sdgui.repository.PageRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PageService extends AbstractServiceImpl<Integer, Page, PageRepository> {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageRepository repository() {
        return pageRepository;
    }

    @Autowired
    private ElementService elementService;

    public void removePageElementById(Page item, int elementId) {
        Element element = elementService.findById(elementId);
        if (null != element && null != item) {
            for (PageElement pageElement : item.getPageElements()) {
                if (pageElement.getElement().getId() == elementId) {
                    item.getPageElements().remove(pageElement);
                    break;
                }
            }
            repository().save(item);
        }
    }

    @Override
    public Page save(Page entity) {
        if (null != entity.getId()) {
            Page fromDb = repository().findOne(entity.getId());
            Set<PageElement> existing = fromDb.getPageElements();
            entity.getPageElements().addAll(existing);
        }
        return super.save(entity);
    }

    public PageModel save(PageModel model) {
        Page fromDb = null;
        if (null != model && null != model.getId()) {
            fromDb = repository().findById(model.getId());
            if (null != fromDb) {
                fromDb.setName(model.getName());
                fromDb.getPageElements().clear();
                Set<PageElement> pageElements = fromDb.getPageElements();
                for (ElementModel element : model.getElements()) {
                    if (null != element.getId()) {
                        PageElement pageElement = new PageElement();
                        Element ele = elementService.findById(element.getId());
                        pageElement.setElement(ele);
                        pageElement.setPage(fromDb);
                        if (!pageElements.contains(pageElement)) {
                            pageElements.add(pageElement);
                        }
                    }
                }
                repository().save(fromDb);
            }
        }
        return toPageModel(fromDb);
    }

    public PageModel toPageModel(Page page) {
        PageModel model = new PageModel();
        if (null != page) {
            BeanUtils.copyProperties(page, model, "pageElements");
            Set<PageElement> pageElements = page.getPageElements();
            int size = pageElements.size();
            for (PageElement pageElement : pageElements) {
                model.getElements().add(elementService.getElementModel(pageElement.getElement()));
            }
        }
        return model;
    }
}
