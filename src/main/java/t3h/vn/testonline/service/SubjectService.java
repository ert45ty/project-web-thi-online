package t3h.vn.testonline.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import t3h.vn.testonline.dto.request.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;

import java.util.List;

public interface SubjectService {

     List<SubjectEntity> getAllAndStatusIsLike();

     List<SubjectEntity> getAll();
     Page<SubjectEntity> getPageAll(int page, int perpage);

     Page<SubjectEntity> search(String query, Integer page, Integer perpage);

     void save(SubjectDto subjectDto);

     void update(SubjectEntity subjectEntity, MultipartFile multipartFile);

     SubjectEntity getById(Long id);

     void delete(Long id);
}
