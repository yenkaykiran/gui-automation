package com.cba.sdgui.service;

import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.repository.ElementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementService extends AbstractServiceImpl<Integer, Element, ElementRepository> {

    @Autowired
    private ElementRepository elementRepository;

    @Override
    public ElementRepository repository() {
        return elementRepository;
    }
}
