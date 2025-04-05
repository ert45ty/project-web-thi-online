package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.request.TopicDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.repository.TopicRepo;
import t3h.vn.testonline.service.TopicService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepo topicRepo;
    private final SubjectServiceImpl subjectService;

    public List<TopicEntity> getAll(){
        return topicRepo.findAll();
    }
    public Page<TopicEntity> search(Long id, String query, Integer page, Integer perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return topicRepo.findAllBySubject_IdAndNameContaining(id, query, pageable);
    }

    public Page<TopicEntity> getAllBySubjectIdAndStatus(Long subjectId, int status, int page, int perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return topicRepo.findAllBySubjectIdAndStatus(subjectId, status, pageable);
    }

    public List<TopicEntity> getAllBySubjectIdAndStatusIsLike(Long id){

        return topicRepo.findAllBySubject_IdAndStatus(id, 1);
    }

    public Page<TopicEntity> getAllBySubjectId(Long id, int page, int perpage){
        Pageable pageable = PageRequest.of(page-1, perpage);
        return topicRepo.findAllBySubjectId(id, pageable);
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
        return topicRepo.findByIdAndStatus(id, 1);
    }

    public TopicEntity getsById(Long id){
        return topicRepo.findTopicEntityById(id);
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
