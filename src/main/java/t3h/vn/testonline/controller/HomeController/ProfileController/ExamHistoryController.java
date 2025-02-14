package t3h.vn.testonline.controller.HomeController.ProfileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.UserService;

import java.util.List;

@Controller
public class ExamHistoryController {

    @Autowired
    UserService userService;
    @Autowired
    ResultService resultService;

    @GetMapping("/profile/history")
    public String examHistory(@AuthenticationPrincipal User user, Model model,
                              @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer perpage){
        String username = user.getUsername();
        Long id = userService.findByUsername(username).getId();
        Page<ResultEntity> resultList = resultService.findResultByUserId(id, page, perpage);

        model.addAttribute("resultList", resultList);
        return "customer/profile/examHistory";
    }
}
