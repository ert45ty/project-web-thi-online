package t3h.vn.testonline.controller.HomeController.ProfileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.CustomerDto;
import t3h.vn.testonline.dto.groupValidation.ChangePassword;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.UserService;

@Controller
@SessionAttributes("subjectList")
public class ChangePasswordController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SubjectService subjectService;

    @GetMapping("/profile/changePassword")
    public String formChangePassword(Model model){

        model.addAttribute("password", new CustomerDto());
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        return "customer/profile/changePassword";
    }

    @PostMapping("/profile/changePassword")
    public String changePassword(@Validated(ChangePassword.class) @ModelAttribute("password") CustomerDto customerDto,
                                 BindingResult result, Model model,
                                 @RequestParam("repassword") String repassword,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @AuthenticationPrincipal UserDetails user,
                                 RedirectAttributes redirectAttributes){


        if (!passwordEncoder.matches(currentPassword, user.getPassword())){
            model.addAttribute("errorMessage1", "*Mật khẩu hiện tại không đúng");
            return "customer/profile/changePassword";
        }
        if (result.hasErrors()) return "customer/profile/changePassword";
        if (passwordEncoder.matches(customerDto.getPassword(), user.getPassword())){
            model.addAttribute("errorMessage2", "*Mật khẩu mới không được phép trùng với mật khẩu hiện tại");
            return "customer/profile/changePassword";
        }
        if (!repassword.equals(customerDto.getPassword())){
            model.addAttribute("errorMessage2", "*Nhập lại mật khẩu không khớp");
            return "customer/profile/changePassword";
        }
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        userService.update(userEntity, null);
        model.addAttribute("message", "Đổi mật khẩu thành công");
        return "customer/profile/changePassword";
    }
}
