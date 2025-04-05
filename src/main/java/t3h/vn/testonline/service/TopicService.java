package t3h.vn.testonline.service;

import org.springframework.data.domain.Page;
import t3h.vn.testonline.dto.request.TopicDto;
import t3h.vn.testonline.entities.TopicEntity;

import java.util.List;


public interface TopicService {

     List<TopicEntity> getAll();
     Page<TopicEntity> search(Long id, String query, Integer page, Integer perpage);

     Page<TopicEntity> getAllBySubjectIdAndStatus(Long subjectId, int status, int page, int perpage);

     List<TopicEntity> getAllBySubjectIdAndStatusIsLike(Long id);

     Page<TopicEntity> getAllBySubjectId(Long id, int page, int perpage);

     List<TopicEntity> getAllBySubjectId(Long id);

     int getTotalExams();

     void delete(Long id);

     TopicEntity getById(Long id);

     TopicEntity getsById(Long id);

     void update(TopicEntity topicEntity);


     void save(TopicDto topicDto);
}
