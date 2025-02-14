package t3h.vn.testonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.ResultEntity;

import java.util.List;

@Repository
public interface ResultRepo extends JpaRepository<ResultEntity, Long> {
    ResultEntity getById(Long id);

    Page<ResultEntity> findResultEntitiesByUser_Id(Long id, Pageable pageable);
}
