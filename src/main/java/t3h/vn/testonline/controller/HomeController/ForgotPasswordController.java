package t3h.vn.testonline.controller.HomeController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.request.CustomerDto;
import t3h.vn.testonline.dto.groupValidation.EmailActiveAccount;
import t3h.vn.testonline.service.CustomerService;
import t3h.vn.testonline.service.SubjectService;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final CustomerService customerService;
    private final SubjectService subjectService;

    @GetMapping("/forgotPassword")
    public String formForgotPassword(Model model){
        model.addAttribute("user", new CustomerDto());
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Validated(EmailActiveAccount.class) @ModelAttribute("user") CustomerDto customerDto,
                                 BindingResult result, Model model,
                                 RedirectAttributes redirectAttributes){
        if (result.hasErrors()) return "forgotPassword";

        if (!customerService.forgotPassword(customerDto)){
            model.addAttribute("message", "*Email hiện tại chưa được đăng kí");
            return "forgotPassword";
        }
        redirectAttributes.addFlashAttribute("message", "*Đặt lại mật khẩu thành công! Vui lòng kiếm tra hòm thư.");
        return "redirect:/login";
    }
}
