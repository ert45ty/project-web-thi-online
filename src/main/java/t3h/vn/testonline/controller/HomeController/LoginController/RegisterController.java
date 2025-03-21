package t3h.vn.testonline.controller.HomeController.LoginController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.CustomerDto;
import t3h.vn.testonline.dto.UserDto;
import t3h.vn.testonline.service.CustomerService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.UserService;
import t3h.vn.testonline.service.email.EmailService;

@Controller
@SessionAttributes("subjectList")
public class RegisterController {

    @Autowired
    CustomerService customerService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    EmailService emailService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("newUser", new CustomerDto());
        model.addAttribute("subjectList", subjectService.getAll());
        return "customer/login/register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("newUser") CustomerDto customerDto,
                          BindingResult result, Model model,
                          RedirectAttributes redirectAttributes,
                          @RequestParam("repassword") String repassword,
                          SessionStatus sessionStatus){

        if (result.hasErrors()){
            if (!customerDto.getPassword().equals(repassword)){
                model.addAttribute("errorMessage", "*Nhập lại mật khẩu không đúng.");
            }
            return "customer/login/register";
        }
        if (!customerDto.getPassword().equals(repassword)){
            model.addAttribute("errorMessage", "*Nhập lại mật khẩu không đúng.");
            return "customer/login/register";
        }
        String status = customerService.registerCustomer(customerDto);
        if (status.equals("fail")){
            model.addAttribute("message", "*Email hoặc tên đăng nhập đã tồn tại.");
            return "customer/login/register";
        }
        else if (status.equals("unactivated")){
            model.addAttribute("message","*Tài khoản đã được đăng kí nhưng chưa được kích hoạt, " +
                                            "vui lòng kích hoạt lại tài khoản");
            return "customer/login/register";
        }
        redirectAttributes.addFlashAttribute("message","Đăng ký thành công! " +
                                            "Vui lòng kiểm tra hòm thư để kích hoạt tài khoản");

        sessionStatus.setComplete();
        return "redirect:/login";
    }
}
