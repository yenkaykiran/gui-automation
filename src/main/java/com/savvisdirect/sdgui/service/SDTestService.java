package com.savvisdirect.sdgui.service;

import com.savvisdirect.sdgui.model.entity.SDTest;
import com.savvisdirect.sdgui.repository.SDTestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SDTestService extends AbstractServiceImpl<Integer, SDTest, SDTestRepository> {

    @Autowired
    private SDTestRepository sdTestRepository;

    @Override
    public SDTestRepository repository() {
        return sdTestRepository;
    }
}
