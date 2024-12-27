package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<TopicEntity> get10perpage(Integer page, Integer perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        Page<TopicEntity> pageResult = topicRepo.findAll(pageable);
        return pageResult;
    }

    public List<TopicEntity> getAllBySubjectId(Long id){
        return topicRepo.findAllBySubject_Id(id);
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
        SubjectEntity subject = subjectService.getById(topicDto.getSubject_id());
        BeanUtils.copyProperties(topicDto, topicEntity);
        topicEntity.setSubject(subject);
        topicRepo.save(topicEntity);
    }
}
