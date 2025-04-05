package t3h.vn.testonline.service;

import org.springframework.data.domain.Page;
import t3h.vn.testonline.dto.request.ExamDto;
import t3h.vn.testonline.entities.ExamEntity;

public interface ExamService {

     Page<ExamEntity> getAllByTopicIdAndStatus(Long id,int page, int perpage);

     Page<ExamEntity> getAllByTopicId(Long id,int page, int perpage);

     Page<ExamEntity> search(Long id,String query, Integer page, Integer perpage);

     ExamEntity getExamByIdAndStatusIsLike(Long id);

     ExamEntity getExamById(Long id);

     ExamEntity save(ExamDto examDto);

     void delete(Long id);

     Page<ExamEntity> searchInCustomerView(Long subjectId, Integer duration,
                                                 String title, int page, int perpage);

     void update(ExamEntity examEntity);
}
