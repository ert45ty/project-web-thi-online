package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.ExamEntity;

import java.util.List;

@Repository
public interface ExamRepo extends JpaRepository<ExamEntity, Long> {
    Page<ExamEntity> getExamEntityByTopic_IdAndStatus(Long id, int status, Pageable pageable);

    Page<ExamEntity> getExamEntityByTopic_Id(Long id, Pageable pageable);

    ExamEntity findByIdAndStatus(Long id, int status);

    ExamEntity getById(Long id);

    Page<ExamEntity> findAllByTopic_IdAndTitleContaining(Long id, String query, Pageable pageable);

//    Page<ExamEntity> findAllByDurationAndTitleContaining(Integer duration, String query, Pageable pageable);

    @Query("SELECT e FROM ExamEntity e " +
            "JOIN e.topic t " +
            "WHERE (:subjectId IS NULL OR t.subject.id = :subjectId) " +
            "AND (:duration IS NULL OR e.duration = :duration) " +
            "AND (:title IS NULL OR e.title LIKE %:title%) " +
            "AND e.status = 1")
    Page<ExamEntity> findExamEntitiesByDurationAndTitleContainingAndSubject_Id(@Param("subjectId") Long subjectId,
                                                                               @Param("duration") Integer duration,
                                                                               @Param("title") String title,
                                                                               Pageable pageable);
}
