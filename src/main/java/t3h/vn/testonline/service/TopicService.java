package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.TopicDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.repository.TopicRepo;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepo topicRepo;
    @Autowired
    SubjectService subjectService;

    public List<TopicEntity> getAll(){
        return topicRepo.findAll();
    }
    public Page<TopicEntity> search(Long id, String query, Integer page, Integer perpage){
        SubjectEntity subject = new SubjectEntity();
        subject.setId(id);
        TopicEntity exampleTopic = new TopicEntity();
        exampleTopic.setSubject(subject);
        Example<TopicEntity> topic = Example.of(exampleTopic);
        Pageable pageable = PageRequest.of(page - 1, perpage);
        Page<TopicEntity> pageResult;
        if (query != null && !query.isEmpty()){
            pageResult = topicRepo.findAllBySubject_IdAndNameContaining(id, query, pageable);
            return pageResult;
        }
        pageResult = topicRepo.findAll(topic, pageable);
        return pageResult;
    }

    public List<TopicEntity> getAllBySubjectId(Long id){
        return topicRepo.findAllBySubject_Id(id);
    }

    public int getTotalExams(){
        int total = 0;
        List<TopicEntity> topics = getAll();
        for (TopicEntity topic : topics) {
            total += topic.getExams().size();
        }
        return total;
    }

    public void delete(Long id){
        topicRepo.deleteById(id);
    }

    public TopicEntity getById(Long id){
        return topicRepo.getById(id);
    }

    public void update(TopicEntity topicEntity){
        topicRepo.save(topicEntity);
    }


    public void save(TopicDto topicDto){
        TopicEntity topicEntity = new TopicEntity();
        topicDto.setStatus(1);
        SubjectEntity subject = subjectService.getById(topicDto.getSubject_id());
        BeanUtils.copyProperties(topicDto, topicEntity);
        topicEntity.setSubject(subject);
        topicRepo.save(topicEntity);
    }
}
