package com.cba.sdgui.service;

import com.cba.sdgui.model.StepModel;
import com.cba.sdgui.model.entity.Element;
import com.cba.sdgui.model.entity.Page;
import com.cba.sdgui.model.entity.SDTest;
import com.cba.sdgui.model.entity.SDTestStep;
import com.cba.sdgui.repository.SDTestStepRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SDTestStepService extends AbstractServiceImpl<Integer, SDTestStep, SDTestStepRepository> {

    @Autowired
    private SDTestStepRepository sdTestStepRepository;

    @Override
    public SDTestStepRepository repository() {
        return sdTestStepRepository;
    }

    @Autowired
    private SDTestService sdTestService;

    @Autowired
    private PageService pageService;

    @Autowired
    private ElementService elementService;

    public StepModel saveStep(StepModel stepModel, Integer id) {
        SDTest test = sdTestService.findById(id);

        SDTestStep stepEntity = null;
        if (null != stepModel.getId()) {
            stepEntity = repository().findById(stepModel.getId());
        } else {
            stepEntity = new SDTestStep();
            stepEntity.setTest(test);
            stepEntity.setStepOrder(test.getSteps().size() + stepModel.getStepOrder());
        }
        try {
            BeanUtils.copyProperties(stepModel, stepEntity, new String[] { "page", "element", "enabled" });
        } catch (Exception e) {
            e.printStackTrace();
        }
        stepEntity.setEnabled(stepModel.getEnabled() != null && stepModel.getEnabled());
        if (null != stepModel.getPage() && null != stepModel.getElement()) {
            Page page = pageService.findById(stepModel.getPage());
            stepEntity.setPage(page);
            Element element = elementService.findById(stepModel.getElement());
            stepEntity.setElement(element);
        }
        stepModel = toStepModel(repository().save(stepEntity));
        return stepModel;
    }

    public StepModel toStepModel(SDTestStep save) {
        StepModel model = new StepModel();
        if (null != save) {
            try {
                BeanUtils.copyProperties(save, model, new String[] { "page", "element", "test" });
                model.setPage(save.getPage().getId());
                model.setElement(save.getElement().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return model;
    }
}
