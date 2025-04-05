package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t3h.vn.testonline.dto.request.QuestionDto;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.repository.OptionRepo;
import t3h.vn.testonline.repository.QuestionRepo;
import t3h.vn.testonline.service.QuestionService;
import t3h.vn.testonline.utils.FileUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepo questionRepo;
    private final ExamServiceImpl examService;
    private final OptionRepo optionRepo;
    private final FileUtils fileUtils;

    public List<QuestionEntity> getAllByExamId(Long id){
       return questionRepo.getAllByExam_Id(id);
    }

    public QuestionEntity save(QuestionDto questionDto, Long id){
        QuestionEntity questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(questionDto, questionEntity);
        if (questionDto.getFileImage() != null && !questionDto.getFileImage().isEmpty()){
            try {
                String image_name = fileUtils.saveFile(questionDto.getFileImage());
                questionEntity.setImage_name(image_name);
            }catch (Exception e){ }
        }
        questionEntity.setExam(examService.getExamById(id));
        QuestionEntity question = questionRepo.save(questionEntity);
        return question;
    }

    public QuestionEntity getQuestionById(Long id){
        return questionRepo.getById(id);
    }

    @Transactional
    public void deleteByExamId(Long id){
        List<QuestionEntity> questionList = getAllByExamId(id);
        for (QuestionEntity question : questionList) {
            optionRepo.deleteAllByQuestion_Id(question.getId());
        }
        questionRepo.deleteAllByExam_Id(id);
    }


}
