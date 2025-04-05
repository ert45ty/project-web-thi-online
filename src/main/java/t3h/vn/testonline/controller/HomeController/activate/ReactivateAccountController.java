package t3h.vn.testonline.controller.HomeController.activate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.request.CustomerDto;
import t3h.vn.testonline.dto.groupValidation.EmailActiveAccount;
import t3h.vn.testonline.service.CustomerService;
import t3h.vn.testonline.service.SubjectService;

@Controller
@SessionAttributes("subjectList")
@RequiredArgsConstructor
public class ReactivateAccountController {

    private final SubjectService subjectService;
    private final CustomerService customerService;

    @GetMapping("/reactivate")
    public String reActivate(Model model){

        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        model.addAttribute("user", new CustomerDto());
        return "activateAccount";
    }

    @PostMapping("/reactivate")
    public String activate(@Validated(EmailActiveAccount.class) @ModelAttribute("user") CustomerDto customerDto,
                           BindingResult result, Model model,
                           RedirectAttributes redirectAttributes,
                           SessionStatus sessionStatus){
        if (result.hasErrors()){
            return "activateAccount";
        }
        if (!customerService.reActivateAccount(customerDto)){
            model.addAttribute("message", "*Email chưa được đăng kí hoặc đã kích hoạt.");
            return "activateAccount";
        }
        redirectAttributes.addFlashAttribute("message",
                "Vui lòng kiểm tra hòm thư để kích hoạt tài khoản");

        sessionStatus.setComplete();
        return "redirect:/login";
    }
}
