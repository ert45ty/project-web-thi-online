package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.OptionEntity;

@Repository
public interface OptionRepo extends JpaRepository<OptionEntity, Long> {
    void deleteAllByQuestion_Id(Long id);

    OptionEntity getById(Long id);
}
