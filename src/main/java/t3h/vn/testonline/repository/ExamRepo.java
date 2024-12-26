package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.ExamEntity;

public interface ExamRepo extends JpaRepository<ExamEntity, Long> {
}
