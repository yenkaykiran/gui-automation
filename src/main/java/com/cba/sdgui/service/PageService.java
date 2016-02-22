package com.cba.sdgui.service;

import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.repository.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService extends AbstractServiceImpl<Integer, Page, PageRepository> {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageRepository repository() {
        return pageRepository;
    }
}
