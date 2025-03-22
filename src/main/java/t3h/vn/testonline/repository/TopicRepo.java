package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.TopicEntity;

import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<TopicEntity, Long> {
    List<TopicEntity> findAllBySubject_IdAndStatus(Long id, int status);

    List<TopicEntity> findAllBySubject_Id(Long id);

    Page<TopicEntity> findAllBySubjectIdAndStatus(Long id,int status, Pageable pageable);

    Page<TopicEntity> findAllBySubjectId(Long id, Pageable pageable);

    TopicEntity findByIdAndStatus(Long id, int status);

    Page<TopicEntity> findAllBySubject_IdAndNameContaining(Long id, String query, Pageable pageable);
}
