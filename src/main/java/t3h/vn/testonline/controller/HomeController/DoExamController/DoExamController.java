package t3h.vn.testonline.controller.HomeController.DoExamController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.request.ListUserAnswerDto;
import t3h.vn.testonline.dto.request.ResultDto;
import t3h.vn.testonline.dto.request.UserAnswerDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/doExam")
@SessionAttributes({"examId", "answers"})
@RequiredArgsConstructor
public class DoExamController {

    private final ExamService examService;
    private final ResultService resultService;
    private final OptionService optionService;
    private final UserAnswerService userAnswerService;
    private final UserService userService;

    @GetMapping()
    public String formDoExam(@RequestParam Long examId,
                             Model model){

        ExamEntity examEntity = examService.getExamByIdAndStatusIsLike(examId);

        List<QuestionEntity> questionList = examEntity.getQuestions();

        ListUserAnswerDto answers = new ListUserAnswerDto();

        LocalDateTime endTime = LocalDateTime.now().plusSeconds(examEntity.getDuration());

        // Định dạng thời gian kết thúc thành chuỗi (ví dụ: "2023-12-31T23:59:59")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedEndTime = endTime.format(formatter);

        // Truyền giá trị endTime vào model
        model.addAttribute("endTime", formattedEndTime);
        model.addAttribute("exam", examEntity);
        model.addAttribute("questionList", questionList);
        model.addAttribute("index", 1);
        model.addAttribute("answers", answers);
        model.addAttribute("examId", examId);
        return "customer/doExam";
    }

    @PostMapping()
    public String doExam(@RequestParam("examId") Long examId,
                         @AuthenticationPrincipal UserDetails user,
                         @ModelAttribute("answers") ListUserAnswerDto answerList,
                         @RequestParam String submitTime,
                         Model model, SessionStatus sessionStatus,
                         HttpSession session,
                         RedirectAttributes redirectAttributes){
            ResultDto resultDto = new ResultDto();
            ExamEntity examEntity = examService.getExamByIdAndStatusIsLike(examId);
            int duration = examEntity.getDuration() - (int)(Long.parseLong(submitTime)/1000);
            resultDto.setExam_duration(duration);
            Float score = 0f;
            if (answerList != null) {
                for (UserAnswerDto answer : answerList.getAnswerDtoList()) {

                    Long option_id = option_id = answer.getOption_id();
                    if (option_id != null) {
                        Boolean is_correct = optionService.getById(option_id).getIs_correct();
                        if (is_correct) score += 10 / examEntity.getTotal_question();
                    }
                }
            }
            resultDto.setScore(score);
            Long userId = userService.findByUsername(user.getUsername()).getId();
            resultDto.setCreated_at(LocalDateTime.now());
            ResultEntity result = resultService.save(resultDto, examId, userId);

            for (int i = 0; i < examEntity.getTotal_question(); i++) {
            userAnswerService.save(new UserAnswerDto(), result.getId(), examEntity.getQuestions().get(i).getId(),
                    answerList.getAnswerDtoList().get(i).getOption_id());
            }
            sessionStatus.setComplete();
            redirectAttributes.addFlashAttribute("message","Nộp bài thành công");
            return "redirect:/profile/history";
    }
}
