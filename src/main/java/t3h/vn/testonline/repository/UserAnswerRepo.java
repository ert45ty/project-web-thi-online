package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.UserAnswerEntity;

public interface UserAnswerRepo extends JpaRepository<UserAnswerEntity, Long> {
}
