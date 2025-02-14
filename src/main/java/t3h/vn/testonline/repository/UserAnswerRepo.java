package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.UserAnswerEntity;

import java.util.List;

@Repository
public interface UserAnswerRepo extends JpaRepository<UserAnswerEntity, Long> {
    UserAnswerEntity getById(Long id);

    List<UserAnswerEntity> getAllByResult_Id(Long id);
}
