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
@RequestMapping("/admin/exam")
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
                           @RequestParam(defaultValue = "0") Integer topic,
                           @RequestParam(defaultValue = "") String query,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer perpage){

        List<SubjectEntity> subjectList = subjectService.getAll();
        if (subject >= subjectList.size()) {
            subject = subjectList.size() - 1;
        }
        List<TopicEntity> topicList;
        Integer totalTopic = 0;
        if (subjectList == null) topicList = Collections.emptyList();
        else {
            topicList = topicService.getAllBySubjectId(subjectList.get(subject).getId());
            totalTopic = topicList.size();
        }
        if (topic >= totalTopic) {
            topic = topicList.size() - 1;
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

        if (query.isEmpty()){
            model.addAttribute("response", examService.getAllByTopicId(topicId, page, perpage));
        }else {
            model.addAttribute("response", examService.search(topicId,query, page, perpage));
        }

        model.addAttribute("message", message);
        model.addAttribute("currentTopic", currentTopic);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("response", examService.getAllByTopicId(topicId, page, perpage));
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

    @GetMapping("/create")
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
        redirectAttributes.addAttribute("totalQuestion", examEntity.getTotal_question());
        redirectAttributes.addAttribute("examId", examId);
        return "redirect:/admin/exam/createQandA";
    }

    @GetMapping("/update")
    public String formUpdateExam(Model model, HttpSession session, @RequestParam Long examId){
        ExamEntity examEntity = examService.getExamById(examId);
        ExamDto existExam = new ExamDto();
        existExam.setId(examEntity.getId());
        existExam.setStatus(examEntity.getStatus());
        existExam.setTitle(examEntity.getTitle());
        existExam.setTopic_id(examEntity.getTopic().getId());
        model.addAttribute("existExam", existExam);
        model.addAttribute("is_update", "update");
        model.addAttribute("topicList", session.getAttribute("topicList"));
        return "admin/exam/createExam";
    }

    @PostMapping("/update")
    public String updateExam(@Valid @ModelAttribute("existExam") ExamDto existExam,
                             BindingResult result, RedirectAttributes redirectAttributes,
                             Model model){
        if (result.hasErrors()){
            return "admin/exam/createExam";
        }
        ExamEntity examEntity = new ExamEntity();
        examEntity.setTopic(topicService.getById(existExam.getTopic_id()));
        if (examEntity.getTopic().getStatus() == 0 && existExam.getStatus() == 1){
            model.addAttribute("error", "Không thể kích hoạt bài thi khi chủ đề bạn chọn đang không được kích hoạt");
            return "admin/exam/createExam";
        }
        examEntity.setStatus(existExam.getStatus());
        examEntity.setTitle(existExam.getTitle());
        examEntity.setTotal_question(existExam.getTotal_question());
        examEntity.setDuration(duration(existExam.getTotal_question()));
        examService.update(examEntity);
        redirectAttributes.addFlashAttribute("message", "Cập nhật bài thi thành công");
        return "redirect:/admin/exam";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long examId, RedirectAttributes redirectAttributes){
        ExamEntity examEntity = examService.getExamById(examId);
        if (examEntity.getStatus() == 0){
            redirectAttributes.addFlashAttribute("message", "Đề thi đã được hủy rồi");
            return "redirect:/admin/exam";
        }
        examEntity.setStatus(0);
        examService.update(examEntity);
        redirectAttributes.addFlashAttribute("message", "Hủy đề thi thành công");
        return "redirect:/admin/exam";
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
