package t3h.vn.testonline.controller.admin.ExamController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.ExamDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.QuestionService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/exam/list")
public class ExamController {

    @Autowired
    ExamService examService;
    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    QuestionService questionService;

    @GetMapping
    public String examList(Model model, HttpSession session,
                           @RequestParam(defaultValue = "0") Integer subject,
                           @RequestParam(defaultValue = "0") Integer topic,
                           @RequestParam(defaultValue = "") String query,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer perpage){

        List<SubjectEntity> subjectList = subjectService.getAll();
        if (subject >= subjectList.size()) {
            model.addAttribute("error", "Subject không hợp lệ");
            return "admin/exam/examList";
        }
        List<TopicEntity> topicList;
        Integer totalTopic = 0;
        if (subjectList == null) topicList = Collections.emptyList();
        else {
            topicList = topicService.getAllBySubjectId(subjectList.get(subject).getId());
            totalTopic = topicList.size();
        }
        if (topic >= totalTopic) {
            model.addAttribute("error", "Topic không hợp lệ");
            return "admin/exam/examList";
        }
        TopicEntity currentTopic;
        Long topicId = 0L;
        if(topicList == null){
            currentTopic = null;
        }else {
            currentTopic = topicList.get(topic);
            topicId = currentTopic.getId();
        }

        String message = (String) model.asMap().get("message");

        model.addAttribute("message", message);
        model.addAttribute("currentTopic", currentTopic);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("response", examService.search(topicId, query, page, perpage));
        model.addAttribute("subjectIndex", subject);
        model.addAttribute("topicIndex", topic);
        session.setAttribute("topicList", topicList);
        model.addAttribute("totalTopic", totalTopic);
        return "admin/exam/examList";
    }

    @PostMapping
    public String querySearch(Model model, HttpSession session,
                              @RequestParam Integer subject,
                              @RequestParam Integer topic,
                              @RequestParam String query){
        model.addAttribute("subjectIndex", subject);
        model.addAttribute("topicIndex", topic);
        return examList(model, session, subject, topic, query, 1, 10);
    }

    @GetMapping("create")
    public String formCreateExam(Model model, HttpSession session){
        model.addAttribute("exam", new ExamDto());
        model.addAttribute("topicList", session.getAttribute("topicList"));
        return "admin/exam/createExam";
    }

    @PostMapping("/create")
    public String createExam(@Valid @ModelAttribute("exam") ExamDto exam,
                             BindingResult result, Model model,
                             RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "admin/exam/createExam";
        }
        Integer total = exam.getTotal_question();
        exam.setDuration(duration(total));
        ExamEntity examEntity = examService.save(exam);
        Long examId = examEntity.getId();
        redirectAttributes.addFlashAttribute("exam", examEntity);
        redirectAttributes.addAttribute("examId", examId);
        return "redirect:/admin/exam/createQandA";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long examId, RedirectAttributes redirectAttributes){
        questionService.deleteByExamId(examId);
        examService.delete(examId);
        redirectAttributes.addFlashAttribute("message", "Xóa đề thi thành công");
        return "redirect:/admin/exam/list";
    }

    @GetMapping("/detail")
    public String detail(Model model,
                         @RequestParam Integer subject,
                         @RequestParam Integer topic,
                         @RequestParam Integer exam){
        List<SubjectEntity> subjectList = subjectService.getAll();
        List<TopicEntity> topicList = subjectList.get(subject).getTopics();
        List<ExamEntity> examList = topicList.get(topic).getExams();
        ExamEntity currentExam = examList.get(exam);
        List<QuestionEntity> questionList = currentExam.getQuestions();

        model.addAttribute("subjectIndex", subject);
        model.addAttribute("topicIndex", topic);
        model.addAttribute("questionList", questionList);
        return "admin/exam/examDetail";
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
