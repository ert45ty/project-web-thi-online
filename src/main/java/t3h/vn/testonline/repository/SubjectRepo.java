package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.SubjectEntity;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity, Long> {
    SubjectEntity getById(Long id);

    List<SubjectEntity> findAllByStatus(int status);


    Page<SubjectEntity> findAllByNameContaining(String query, Pageable pageable);
}
