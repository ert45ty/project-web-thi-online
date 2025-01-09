package t3h.vn.testonline.repository;

import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t3h.vn.testonline.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity getById(Long id);

    Page<UserEntity> findAllByUsernameContaining(String query, Pageable pageable);
}
