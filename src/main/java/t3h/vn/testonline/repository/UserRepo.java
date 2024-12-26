package t3h.vn.testonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t3h.vn.testonline.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
