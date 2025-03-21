package t3h.vn.testonline.controller.HomeController.LoginController;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import t3h.vn.testonline.service.SubjectService;

@Controller
@SessionAttributes("subjectList")
public class LoginController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletRequest request){
        String errorMessage = (String) request.getSession().getAttribute("error");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            // Xóa lỗi sau khi đã hiển thị
            request.getSession().removeAttribute("error");
        }
        model.addAttribute("subjectList", subjectService.getAll());
        return "customer/login/login";
    }

}
