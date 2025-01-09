package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.ResultEntity;

@Repository
public interface ResultRepo extends JpaRepository<ResultEntity, Long> {
}
