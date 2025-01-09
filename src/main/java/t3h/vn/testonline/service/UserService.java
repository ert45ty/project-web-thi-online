package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import t3h.vn.testonline.dto.UserDto;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.repository.UserRepo;
import t3h.vn.testonline.utils.FileUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    FileUtils fileUtils;

    public List<UserEntity> getAll(){
        return userRepo.findAll();
    }

    public Page<UserEntity> search(String name, Integer page, Integer perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        Page<UserEntity> result;
        if (name != null && !name.isEmpty()){
            result = userRepo.findAllByUsernameContaining(name, pageable);
            return result;
        }
        result = userRepo.findAll(pageable);
        return result;
    }

    public void save(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        if (userDto.getFileImage() != null && !userDto.getFileImage().isEmpty()){
            try {
                String image_name = fileUtils.saveFile(userDto.getFileImage());
                userEntity.setImage_name(image_name);
            }catch (Exception e){}

        }
        userRepo.save(userEntity);
    }


    public UserEntity getById(Long id){
        return userRepo.getById(id);
    }

    public void update(UserEntity userEntity, MultipartFile multipartFile){
        if (multipartFile != null && !multipartFile.isEmpty()){
            try {
                String image_name = fileUtils.saveFile(multipartFile);
                userEntity.setImage_name(image_name);
            }catch (Exception e){}
        }
        userRepo.save(userEntity);
    }

    public void delete(Long id){
        userRepo.deleteById(id);
    }
}
