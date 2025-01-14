package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.QuestionEntity;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> getAllByExam_Id(Long id);

    QuestionEntity getById(Long id);

    void deleteAllByExam_Id(Long id);


}
