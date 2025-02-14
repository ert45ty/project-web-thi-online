package t3h.vn.testonline.controller.HomeController.ProfileController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("profile")
public class UserProfileController {
    @GetMapping
    public String userProfile(){
        return "customer/profile/userProfile";
    }
}
