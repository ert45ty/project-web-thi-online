package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
