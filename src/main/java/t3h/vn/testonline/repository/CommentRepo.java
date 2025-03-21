package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByContentContainingOrderByIdDesc(String content, Pageable pageable);

    void deleteCommentEntityById(Long id);

    void deleteById(Long id);
}
