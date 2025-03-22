package t3h.vn.testonline.controller.HomeController.ProfileController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.CustomerDto;
import t3h.vn.testonline.dto.UserDto;
import t3h.vn.testonline.dto.groupValidation.UpdateAccountInformation;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.UserService;

@Controller
@SessionAttributes("subjectList")
@RequestMapping("/profile/update")
public class UpdateProfileController {

    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;

    @GetMapping
    public String formUpdate(Model model, @AuthenticationPrincipal UserDetails user){

        UserEntity userEntity = userService.findByUsername(user.getUsername());

        UserDto userDto = new UserDto();
        userDto.setEmail(userEntity.getEmail());
        userDto.setFullname(userEntity.getFullname());
        userDto.setImage_name(userEntity.getImage_name());

        model.addAttribute("existUser", userDto);
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        return "customer/profile/updateProfile";
    }

    @PostMapping
    public String update(@Validated(UpdateAccountInformation.class) @ModelAttribute("existUser") UserDto userDto,
                         BindingResult result, Model model,
                         @RequestParam("fileImage")MultipartFile fileImage,
                         @RequestParam("img")String action,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal UserDetails user){
        if (result.hasErrors()){
            return "customer/profile/updateProfile";
        }
        UserEntity userEntity = userService.findByUsername(user.getUsername());
        userEntity.setFullname(userDto.getFullname().trim());
        userEntity.setEmail(userDto.getEmail().replaceAll("\\s+", ""));
        if ("yes".equals(action)){
            userEntity.setImage_name(null);
            userService.update(userEntity, null);
        }else userService.update(userEntity, fileImage);

        redirectAttributes.addFlashAttribute("message", "Thay đổi thông tin thành công");

        return "redirect:/profile";
    }
}
