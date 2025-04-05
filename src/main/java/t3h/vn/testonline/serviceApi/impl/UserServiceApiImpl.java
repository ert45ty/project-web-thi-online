package t3h.vn.testonline.serviceApi.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.request.UserDto;
import t3h.vn.testonline.dto.response.PageResponse;
import t3h.vn.testonline.dto.response.UserDetailResponse;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.exception.ResourceNotFoundException;
import t3h.vn.testonline.repository.UserRepo;
import t3h.vn.testonline.serviceApi.UserServiceApi;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceApiImpl implements UserServiceApi {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public long addUser(UserDto userDto) {
        String password = passwordEncoder.encode(userDto.getPassword());
        String randomCode = UUID.randomUUID().toString();
        if (userRepo.findFirstByUsername(userDto.getUsername()) != null){
            throw new ResourceNotFoundException("Username already exists");
        }
        UserEntity user = UserEntity.builder()
                .fullname(userDto.getFullname())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(password)
                .role(userDto.getRole())
                .code(randomCode)
                .status(0)
                .build();
        userRepo.save(user);
        log.info("Add user  successfully");
        return user.getId();
    }

    @Override
    public void updateUser(long userId, UserDto userDto) {
        UserEntity user = getUserById(userId);
        user.setFullname(userDto.getFullname());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());


        log.info("Update user successfully");
    }

    @Override
    public void deleteUser(long userId) {
        userRepo.deleteById(userId);
        log.info("Deleted user with userId={}", userId);
    }

    @Override
    public UserDetailResponse getUser(long userId) {
        UserEntity user = getUserById(userId);
        return UserDetailResponse.builder()
                .id(userId)
                .email(user.getEmail())
                .fullName(user.getFullname())
                .build();
    }

    @Override
    public PageResponse<?> getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts) {
        return null;
    }



    private UserEntity getUserById(long userId){
        return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
