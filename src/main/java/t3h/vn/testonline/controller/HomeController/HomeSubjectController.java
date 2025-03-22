package t3h.vn.testonline.controller.HomeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.List;

@Controller
@RequestMapping("/subject")
public class HomeSubjectController {
    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;

    @GetMapping
    public String topicList(Model model,
                            @RequestParam(defaultValue = "0") Integer subject,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer perpage){

        List<SubjectEntity> subjectList = subjectService.getAllAndStatusIsLike();
        Long id = subjectList.get(subject).getId();
        SubjectEntity currentSubject = subjectList.get(subject);

        model.addAttribute("response", topicService.getAllBySubjectIdAndStatus(id, 1, page, perpage));
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("currentSubject", currentSubject);
        model.addAttribute("subjectIndex", subject);

        return "customer/subjectHomeList";
    }

}
