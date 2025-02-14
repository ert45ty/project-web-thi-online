package t3h.vn.testonline.controller.HomeController.LoginController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(){
        return "customer/login/login";
    }
}
