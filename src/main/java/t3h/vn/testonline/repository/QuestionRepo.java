package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.QuestionEntity;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionEntity, Long> {
    QuestionEntity getById(Long id);
}
