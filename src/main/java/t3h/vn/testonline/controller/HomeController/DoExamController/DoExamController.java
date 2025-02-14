package t3h.vn.testonline.controller.HomeController.DoExamController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.QuestionDto;
import t3h.vn.testonline.dto.ResultDto;
import t3h.vn.testonline.dto.UserAnswerDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.entities.UserAnswerEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.OptionService;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.UserAnswerService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doExam")
@SessionAttributes({"answers", "examId", "exam"})
public class DoExamController {

    @Autowired
    ExamService examService;
    @Autowired
    ResultService resultService;
    @Autowired
    OptionService optionService;
    @Autowired
    UserAnswerService userAnswerService;

    @GetMapping()
    public String formDoExam(@RequestParam Long examId,
                             @RequestParam(defaultValue = "0", required = false) Integer currentIndex,
                             Model model){
        if (currentIndex == null || currentIndex < 0) {
            currentIndex = 0;
        }
        ExamEntity examEntity = examService.getExamById(examId);
        if (!model.containsAttribute("answers")){
            List<UserAnswerDto> answers = new ArrayList<>();
            for (int i = 0; i < examEntity.getTotal_question(); i++) {
                UserAnswerDto answer = new UserAnswerDto();
                answer.setQuestion_id(examEntity.getQuestions().get(i).getId());
                answer.setOption_id(null);
                answers.add(answer);
            }
//            LocalDateTime endTime = LocalDateTime.now().plusMinutes(examEntity.getDuration()/60);
            long currentTime = System.currentTimeMillis();
            long examDuration = examEntity.getDuration() * 1000;
            long endTime = currentTime + examDuration;
            model.addAttribute("endTime", endTime);
            model.addAttribute("examId", examId);
            model.addAttribute("answers", answers);
            model.addAttribute("exam", examEntity);
        }

        List<QuestionEntity> questionEntityList = examEntity.getQuestions();
        if (questionEntityList.size() <= currentIndex){
            currentIndex = questionEntityList.size() - 1;
        }
        QuestionEntity question = questionEntityList.get(currentIndex);
//        UserAnswerDto currentAnswer = new UserAnswerDto();

        model.addAttribute("question", question);
//        model.addAttribute("currentAnswer", currentAnswer);
        model.addAttribute("currentIndex", currentIndex);
        return "customer/doExam";
    }

    @PostMapping()
    public String doExam(@RequestParam Long examId,
//                         @ModelAttribute UserAnswerDto currentAnswer,
                         @RequestParam(value = "optionId", required = false) Long optionId,
                         @RequestParam(required = false) String finish,
                         @RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer currentIndex,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         Model model, SessionStatus sessionStatus,
                         RedirectAttributes redirectAttributes){
        List<UserAnswerDto> answers = (List<UserAnswerDto>) model.getAttribute("answers");
        if (optionId != null){ answers.get(currentIndex).setOption_id(optionId);}
        if ("next".equals(action)) {
            currentIndex = currentIndex < answers.size() ? currentIndex + 1 : currentIndex;
        }else if ("back".equals(action)){
            currentIndex = currentIndex > 0 ? currentIndex - 1 : currentIndex;
        }else if (action != null) {
            for (int i = 1; i <= answers.size(); i++) {
                String iStr = i + "";
                if (iStr.equals(action)) {
                    currentIndex = i - 1;
                }
            }
        }

        if ("true".equals(finish)){
            ResultEntity resultEntity = new ResultEntity();
//            Integer remainingTime = Integer.valueOf(endTime);
            resultEntity.setExam_duration(0);
            Float score = 0f;
            for (int i = 0; i < answers.size(); i++) {

                Long option_id = answers.get(i).getOption_id();
                if (option_id != null){
                Boolean is_correct = optionService.getById(option_id).getIs_correct();
                if (is_correct) score += 10/answers.size();}
            }
            resultEntity.setScore(score);
            ResultEntity result = resultService.save(6L, examId, resultEntity);
            for (int i = 0; i < answers.size(); i++) {
                userAnswerService.save(answers.get(i), result);
            }
            sessionStatus.setComplete();
            redirectAttributes.addFlashAttribute("message","Nộp bài thành công");
            redirectAttributes.addFlashAttribute("score", score);
            redirectAttributes.addFlashAttribute("resultId", result.getId());
            return "redirect:/home";
        }
//        model.addAttribute("examId", examId);
//        model.addAttribute("currentIndex", currentIndex);
        return formDoExam(examId, currentIndex, model);
    }
}
