package t3h.vn.testonline.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import t3h.vn.testonline.dto.request.UserDto;
import t3h.vn.testonline.entities.UserEntity;

import java.util.List;

public interface UserService {

     List<UserEntity> getAll();

     Page<UserEntity> search(String name, Integer page, Integer perpage);

     UserEntity findByUsername(String username);

     void save(UserDto userDto);

     UserEntity getById(Long id);

     void update(UserEntity userEntity, MultipartFile multipartFile);

     UserEntity getByCode(String code);

     void delete(Long id);
}
