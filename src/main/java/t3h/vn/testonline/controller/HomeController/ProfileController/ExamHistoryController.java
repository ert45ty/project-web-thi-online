package t3h.vn.testonline.controller.HomeController.ProfileController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExamHistoryController {

    private final UserService userService;
    private final ResultService resultService;
    private final SubjectService subjectService;

    @GetMapping("/profile/history")
    public String examHistory(@AuthenticationPrincipal UserDetails user, Model model,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "5") Integer perpage){
        String username = user.getUsername();
        String message =(String) model.asMap().get("message");
        Long id = userService.findByUsername(username).getId();
        model.addAttribute("message", message);
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        model.addAttribute("response", resultService.findResultByUserId(id, page, perpage));
        return "customer/profile/examHistory";
    }
}
