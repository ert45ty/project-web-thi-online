package t3h.vn.testonline.controller.admin.ExamController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.ListQuestionDto;
import t3h.vn.testonline.dto.OptionDto;
import t3h.vn.testonline.dto.QuestionDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.OptionService;
import t3h.vn.testonline.service.QuestionService;
import t3h.vn.testonline.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/exam")
public class CreateQandAController {

    private final QuestionService questionService;
    private final OptionService optionService;
    private final ExamService examService;

    public CreateQandAController(QuestionService questionService, OptionService optionService, ExamService examService){
        this.examService = examService;
        this.optionService = optionService;
        this.questionService = questionService;
    }

    @GetMapping("/createQandA")
    public String formQandA(Model model,
                            @RequestParam(value = "examId", required = false) Long examId,
                            @RequestParam(value = "totalQuestion") int totalQuestion){

        ListQuestionDto questions = new ListQuestionDto();
//        for (int i = 0; i < totalQuestion; i++) {
//            QuestionDto questionDto = new QuestionDto();
//            questionDto.setOptions(new ArrayList<>(4));
//            for (int j = 0; j < 4; j++) {
//                OptionDto optionDto = new OptionDto();
//                optionDto.setIs_correct(false);
//                questionDto.getOptions().add(optionDto);
//            }
//            questions.getQuestionDtoList().add(questionDto);
//        }
        model.addAttribute("questions", questions);
        model.addAttribute("totalQuestion", totalQuestion);
        model.addAttribute("examId", examId);
        return "admin/exam/createQandA";
    }

    @PostMapping("createQandA")
    public String createQandA(@Valid @ModelAttribute("questions") ListQuestionDto questions,
                              BindingResult result, Model model,
                              @RequestParam Long examId,@RequestParam int totalQuestion,
                              RedirectAttributes redirectAttributes){
        if(result.hasErrors()){

            model.addAttribute("totalQuestion", totalQuestion);
            return "admin/exam/createQandA";
        }
        List<QuestionDto> questionDtoList = questions.getQuestionDtoList();
        for (int i = 0; i < questionDtoList.size(); i++) {
            QuestionEntity questionEntity = questionService.save(questionDtoList.get(i), examId);
            for (int j = 0; j < 4; j++) {
                OptionDto option = questionDtoList.get(i).getOptions().get(j);
                if (questionDtoList.get(i).getCorrect() == j){
                    option.setIs_correct(true);
                }else option.setIs_correct(false);
                Long questionId = questionEntity.getId();
                optionService.save(option, questionId);
            }
        }
        ExamEntity examEntity = examService.getExamById(examId);
        examEntity.setStatus(1);
        examService.update(examEntity);
        redirectAttributes.addFlashAttribute("message", "Tạo bài thi thành công");
        return "redirect:/admin/exam";
    }
}
