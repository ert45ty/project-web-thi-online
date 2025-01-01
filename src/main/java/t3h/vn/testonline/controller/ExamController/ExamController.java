package t3h.vn.testonline.controller.ExamController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.ExamDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/exam/list")
public class ExamController {

    @Autowired
    ExamService examService;
    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;

    @GetMapping
    public String examList(Model model, HttpSession session,
                           @RequestParam(defaultValue = "0") Integer subject,
                           @RequestParam(defaultValue = "0") Integer topic){

        List<SubjectEntity> subjectList = subjectService.getAll();
        if (subject >= subjectList.size()) {
            model.addAttribute("error", "Subject không hợp lệ");
            return "admin/exam/examList";
        }
        List<TopicEntity> topicList;
        if (subjectList.isEmpty()) topicList = Collections.emptyList();
        else topicList = topicService.getAllBySubjectId(subjectList.get(subject).getId());
        if (topic >= topicList.size()) {
            model.addAttribute("error", "Topic không hợp lệ");
            return "admin/exam/examList";
        }
        List<ExamEntity> examList;
        if(topicList.isEmpty()) examList = Collections.emptyList();
        else examList = examService.getAllByTopicId(topicList.get(topic).getId());

        TopicEntity currentTopic;
        if(examList.isEmpty()){
            currentTopic = null;
        }else {
            currentTopic = examList.get(0).getTopic();
        }
        model.addAttribute("currentTopic", currentTopic);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("examList", examList);
        model.addAttribute("subjectIndex", subject);
        session.setAttribute("topicList", topicList);
        return "admin/exam/examList";
    }

    @GetMapping("create")
    public String formCreateExam(Model model, HttpSession session){
        model.addAttribute("exam", new ExamDto());
        model.addAttribute("topicList", session.getAttribute("topicList"));
        return "admin/exam/createExam";
    }

    @PostMapping("/create")
    public String createExam(@Valid @ModelAttribute("exam") ExamDto exam, Model model,
                             BindingResult result){
        if (result.hasErrors()){
            return "admin/exam/createExam";
        }
        Integer total = exam.getTotal_question();
        exam.setDuration(duration(total));
        examService.save(exam);
        return "redirect:/exam/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session){
        examService.delete(id);
        return examList(model, session, 0, 0);
    }




    public Integer duration(Integer integer){
        if(integer == 10){
            return 900;
        } else if (integer == 30){
            return 1800;
        } else if (integer == 40) return 2700;
        return 3600;
    }
}
