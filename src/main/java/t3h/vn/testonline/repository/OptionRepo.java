package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.OptionEntity;

public interface OptionRepo extends JpaRepository<OptionEntity, Long> {
}
