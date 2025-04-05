package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.request.ExamDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.repository.ExamRepo;
import t3h.vn.testonline.service.ExamService;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepo examRepo;
    private final TopicServiceImpl topicService;

    public Page<ExamEntity> getAllByTopicIdAndStatus(Long id,int page, int perpage){
        Pageable pageable = PageRequest.of(page-1, perpage);
        return examRepo.getExamEntityByTopic_IdAndStatus(id, 1,pageable);
    }

    public Page<ExamEntity> getAllByTopicId(Long id,int page, int perpage){
        Pageable pageable = PageRequest.of(page-1, perpage);
        return examRepo.getExamEntityByTopic_Id(id,pageable);
    }

    public Page<ExamEntity> search(Long id,String query, Integer page, Integer perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return examRepo.findAllByTopic_IdAndTitleContaining(id, query, pageable);
    }

    public ExamEntity getExamByIdAndStatusIsLike(Long id){
        return examRepo.findByIdAndStatus(id, 1);
    }

    public ExamEntity getExamById(Long id){
        return examRepo.getById(id);
    }

    public ExamEntity save(ExamDto examDto){
        ExamEntity examEntity = new ExamEntity();
        examDto.setStatus(0);
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
