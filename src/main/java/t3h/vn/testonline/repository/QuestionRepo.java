package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.QuestionEntity;

public interface QuestionRepo extends JpaRepository<QuestionEntity, Long> {
}
