package t3h.vn.testonline.controller.HomeController.LoginController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.UserDto;
import t3h.vn.testonline.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("newUser", new UserDto());
        return "customer/login/register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("newUser") UserDto userDto,
                          BindingResult result, Model model,
                          RedirectAttributes redirectAttributes,
                          @RequestParam("repassword") String repassword){

        if (result.hasErrors()){
            if (!userDto.getPassword().equals(repassword)){
                model.addAttribute("errorMessage", "*Nhập lại mật khẩu không đúng.");
            }
            return "customer/login/register";
        }
        if (!userDto.getPassword().equals(repassword)){
            model.addAttribute("errorMessage", "*Nhập lại mật khẩu không đúng.");
            return "customer/login/register";
        }
        userDto.setRole("USER");
        userService.save(userDto);
        redirectAttributes.addFlashAttribute("message","Đăng ký thành công");
        return "redirect:/login";
    }
}
