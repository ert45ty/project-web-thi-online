package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.TopicEntity;

public interface TopicRepo extends JpaRepository<TopicEntity, Long> {
}
