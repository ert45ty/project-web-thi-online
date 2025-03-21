package t3h.vn.testonline.controller.HomeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ResultService resultService;

    @GetMapping
    public String home(Model model,
                       @RequestParam(defaultValue = "0") Integer subject){

        List<SubjectEntity> subjectList = subjectService.getAll();
        Long id = subjectList.get(subject).getId();
        List<TopicEntity> topicList = topicService.getAllBySubjectId(id);
        List<ExamEntity> examList = new ArrayList<>();
        int count = 0;
        List<Float> highestScore = new ArrayList<>();
        for (int i = 0; i < topicList.size(); i++) {
            List<ExamEntity> exams  = topicList.get(i).getExams();
            for (int j = 0; j < exams.size(); j++) {
                if (count >5){
                    break;
                }
                ExamEntity exam  = exams.get(j);
                if (exam.getTotal_question() == 10){
                    Float hs = resultService.findHighestScoreByExamId(exam.getId());
                    highestScore.add(hs);
                    examList.add(exam);
                    count++;
                }
            }
            if (count > 5){
                break;
            }
        }

        model.addAttribute("examList", examList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("subjectIndex", subject);
        model.addAttribute("highestScore", highestScore);
        return "customer/home";
    }
}
