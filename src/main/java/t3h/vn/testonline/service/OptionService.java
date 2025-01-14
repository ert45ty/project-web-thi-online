package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.OptionDto;
import t3h.vn.testonline.entities.OptionEntity;
import t3h.vn.testonline.repository.OptionRepo;

@Service
public class OptionService {

    @Autowired
    OptionRepo optionRepo;
    @Autowired
    QuestionService questionService;

    public void save(OptionDto optionDto, Long id){
        OptionEntity optionEntity = new OptionEntity();
        BeanUtils.copyProperties(optionDto, optionEntity);
        optionEntity.setQuestion(questionService.getQuestionById(id));
        optionRepo.save(optionEntity);
    }

}
