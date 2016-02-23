package com.cba.sdgui.service;

import com.cba.sdgui.model.entity.Url;
import com.cba.sdgui.repository.UrlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService extends AbstractServiceImpl<Integer, Url, UrlRepository> {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public UrlRepository repository() {
        return urlRepository;
    }
}
