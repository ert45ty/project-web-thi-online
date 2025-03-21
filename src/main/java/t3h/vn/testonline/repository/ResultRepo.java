package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.ResultEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepo extends JpaRepository<ResultEntity, Long> {
    ResultEntity getById(Long id);

    Page<ResultEntity> findResultEntitiesByUser_IdOrderByIdDesc(Long id, Pageable pageable);

    @Query("SELECT r FROM ResultEntity r WHERE r.exam.id = :examId AND r.id IN " +
            "(SELECT MIN(r2.id) FROM ResultEntity r2 WHERE r2.exam.id = :examId AND " +
            "r2.score = (SELECT MAX(r3.score) FROM ResultEntity r3 WHERE r3.user.id = r2.user.id AND r3.exam.id = :examId)) " +
            "ORDER BY r.score DESC")
    List<ResultEntity> findResultEntitiesByExamIdOrderByScoreDesc(@Param("examId")Long examId, Pageable pageable);

    Optional<ResultEntity> findFirstByExamIdOrderByScoreDesc(Long id);
}
