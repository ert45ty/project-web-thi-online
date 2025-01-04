package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.ExamDto;
import t3h.vn.testonline.entities.ExamEntity;
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

    public ExamEntity getExamById(Long id){
        return examRepo.getById(id);
    }

    public ExamEntity save(ExamDto examDto){
        ExamEntity examEntity = new ExamEntity();
        TopicEntity topic = topicService.getById(examDto.getTopic_id());
        BeanUtils.copyProperties(examDto, examEntity);
        examEntity.setTopic(topic);
        examRepo.save(examEntity);
        return examEntity;
    }

    public void delete(Long id){
        examRepo.deleteById(id);
    }
}
