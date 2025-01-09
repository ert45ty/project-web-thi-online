package t3h.vn.testonline.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;
import t3h.vn.testonline.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    UserService userService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    TopicService topicService;
    @Autowired
    ExamService examService;

    @GetMapping
    public String dashboard(Model model){
        Integer totalUser = userService.getAll().size();
        Integer totalSubject = subjectService.getAll().size();
        List<TopicEntity> topicList = topicService.getAll();
        Integer totalTopic = topicList.size();
        Integer totalExam = 0;
        for (int i = 0; i < totalTopic; i++){
            totalExam += topicList.get(i).getExams().size();
        }

        model.addAttribute("totalUser",totalUser);
        model.addAttribute("totalSubject",totalSubject);
        model.addAttribute("totalTopic",totalTopic);
        model.addAttribute("totalExam",totalExam);
        return "admin/dashboard";
    }
}
