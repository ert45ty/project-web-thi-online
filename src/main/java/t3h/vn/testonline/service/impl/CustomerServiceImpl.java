package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import t3h.vn.testonline.dto.request.CustomerDto;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.repository.UserRepo;
import t3h.vn.testonline.service.CustomerService;
import t3h.vn.testonline.service.email.EmailServiceImpl;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final EmailServiceImpl emailService;

    public String registerCustomer(CustomerDto customerDto){

        String randomCode = UUID.randomUUID().toString();

        if (userRepo.findFirstByUsername(customerDto.getUsername()) != null
            || userRepo.findFirstByEmail(customerDto.getEmail()) != null){
            UserEntity userEntity = userRepo.findFirstByEmail(customerDto.getEmail());
            if (userEntity.getStatus() == 0) return "unactivated";
            return "fail";
        }
        UserEntity user = new UserEntity();
        user.setUsername(customerDto.getUsername());
        user.setFullname(customerDto.getFullname());
        user.setEmail(customerDto.getEmail());
        user.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        user.setRole("USER");
        user.setCreated_at(LocalDateTime.now());
        user.setStatus(0);

        user.setCode(randomCode);

        userRepo.save(user);

        Context context = new Context();

        context.setVariable("name", customerDto.getFullname());
        context.setVariable("username", customerDto.getUsername());
        context.setVariable("message", randomCode);
        context.setVariable("subject", "Kích hoạt tài khoản");
        try {
            emailService.sendMail(customerDto.getEmail(), "Kích hoạt tài khoản", "emailTemplate", context);
            return "success";
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Boolean reActivateAccount(CustomerDto customerDto){
        UserEntity userEntity = userRepo.findFirstByEmail(customerDto.getEmail());
        if (userEntity == null) return false;
        if (userEntity.getStatus() == 1) return false;

        String randomCode = UUID.randomUUID().toString();

        Context context = new Context();

        context.setVariable("name", userEntity.getFullname());
        context.setVariable("username", userEntity.getUsername());
        context.setVariable("message", randomCode);
        context.setVariable("subject", "Kích hoạt tài khoản");
        try {
            userEntity.setCode(randomCode);
            userRepo.save(userEntity);
            emailService.sendMail(customerDto.getEmail(), "Kích hoạt tài khoản", "emailTemplate", context);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Boolean forgotPassword(CustomerDto customerDto){
        UserEntity userEntity = userRepo.findFirstByEmail(customerDto.getEmail());
        if (userEntity == null) return false;

        int newPassword = new Random().nextInt(100000, 999999);

        Context context = new Context();

        context.setVariable("name", userEntity.getFullname());
        context.setVariable("username", userEntity.getUsername());
        context.setVariable("message", newPassword);
        context.setVariable("subject", "forgotPassword");
        try {
            userEntity.setPassword(passwordEncoder.encode(String.valueOf(newPassword)));
            userRepo.save(userEntity);
            emailService.sendMail(customerDto.getEmail(), "Đặt lại mật khẩu", "emailTemplate", context);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
