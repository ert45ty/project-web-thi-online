package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.TopicEntity;

import java.util.List;

public interface TopicRepo extends JpaRepository<TopicEntity, Long> {
    List<TopicEntity> findAllBySubject_Id(Long id);

    TopicEntity getById(Long id);


}
