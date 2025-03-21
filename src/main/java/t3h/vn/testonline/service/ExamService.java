package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.ExamDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.repository.ExamRepo;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    ExamRepo examRepo;
    @Autowired
    TopicService topicService;

    public List<ExamEntity> getAllByTopicId(Long id){
        return examRepo.getExamEntityByTopic_Id(id);
    }

    public Page<ExamEntity> search(Long id,String query, Integer page, Integer perpage){
        TopicEntity exampleTopic = new TopicEntity();
        exampleTopic.setId(id);
        ExamEntity exampleExam = new ExamEntity();
        exampleExam.setTopic(exampleTopic);
        Example<ExamEntity> exam = Example.of(exampleExam);
        Pageable pageable = PageRequest.of(page - 1, perpage);
        Page<ExamEntity> result;
        if (query != null && !query.isEmpty()){
            result = examRepo.findAllByTopic_IdAndTitle(id, query, pageable);
            return result;
        }
        result = examRepo.findAll(exam, pageable);
        return result;
    }

    public ExamEntity getExamById(Long id){
        return examRepo.getById(id);
    }

    public ExamEntity save(ExamDto examDto){
        ExamEntity examEntity = new ExamEntity();
        examDto.setStatus(1);
        TopicEntity topic = topicService.getById(examDto.getTopic_id());
        BeanUtils.copyProperties(examDto, examEntity);
        examEntity.setTopic(topic);
        examRepo.save(examEntity);
        return examEntity;
    }

    public void delete(Long id){
        examRepo.deleteById(id);
    }

    public Page<ExamEntity> searchInCustomerView(Long subjectId, Integer duration,
                                                 String title, int page, int perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return examRepo.findExamEntitiesByDurationAndTitleContainingAndSubject_Id(subjectId, duration, title, pageable);
    }

    public void update(ExamEntity examEntity){
        examRepo.save(examEntity);
    }
}
