package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.SubjectEntity;

public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
    SubjectEntity getById(Long id);
}
