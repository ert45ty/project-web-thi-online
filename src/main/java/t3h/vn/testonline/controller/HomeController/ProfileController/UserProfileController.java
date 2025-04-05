package t3h.vn.testonline.controller.HomeController.ProfileController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.UserService;

@Controller
@RequestMapping("profile")
@RequiredArgsConstructor
public class UserProfileController {

   private final SubjectService subjectService;
   private final UserService userService;

    @GetMapping
    public String userProfile(Model model, @AuthenticationPrincipal UserDetails user){
        UserEntity userEntity = userService.findByUsername(user.getUsername());

        String message = (String) model.asMap().get("message");
        model.addAttribute("message", message);
        model.addAttribute("user", userEntity);
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        return "customer/profile/userProfile";
    }
}
