package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.ExamEntity;

import java.util.List;

@Repository
public interface ExamRepo extends JpaRepository<ExamEntity, Long> {
    List<ExamEntity> getExamEntityByTopic_Id(Long id);

    ExamEntity getById(Long id);

    Page<ExamEntity> findAllByTopic_IdAndTitle(Long id, String query, Pageable pageable);
}
