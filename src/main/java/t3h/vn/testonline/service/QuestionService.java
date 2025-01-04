package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.QuestionDto;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.repository.QuestionRepo;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    ExamService examService;

    public QuestionEntity save(QuestionDto questionDto, Long id){
        QuestionEntity questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(questionDto, questionEntity);
        questionEntity.setExam(examService.getExamById(id));
        QuestionEntity question = questionRepo.save(questionEntity);
        return question;
    }

    public QuestionEntity getQuestionById(Long id){
        return questionRepo.getById(id);
    }


}
