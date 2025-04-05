package t3h.vn.testonline.controller.HomeController.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;
import t3h.vn.testonline.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final SubjectService subjectService;
    private final TopicService topicService;

    @GetMapping
    public String dashboard(Model model){
        Integer totalUser = userService.getAll().size();
        Integer totalSubject = subjectService.getAll().size();
        List<TopicEntity> topicList = topicService.getAll();
        Integer totalTopic = topicList.size();
        Integer totalExam = topicService.getTotalExams();

        model.addAttribute("totalUser",totalUser);
        model.addAttribute("totalSubject",totalSubject);
        model.addAttribute("totalTopic",totalTopic);
        model.addAttribute("totalExam",totalExam);
        return "admin/dashboard";
    }
}
