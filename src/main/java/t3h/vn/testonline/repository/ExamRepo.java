package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.ExamEntity;

import java.util.List;

public interface ExamRepo extends JpaRepository<ExamEntity, Long> {
    List<ExamEntity> getExamEntityByTopic_Id(Long id);
}
