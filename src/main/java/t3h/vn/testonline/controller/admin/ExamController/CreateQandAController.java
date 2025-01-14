package t3h.vn.testonline.controller.admin.ExamController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.OptionDto;
import t3h.vn.testonline.dto.QuestionDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.QuestionEntity;
import t3h.vn.testonline.service.OptionService;
import t3h.vn.testonline.service.QuestionService;
import t3h.vn.testonline.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/exam")
@SessionAttributes({"exam", "questions", "examId"})
public class CreateQandAController {

    @Autowired
    QuestionService questionService;
    @Autowired
    OptionService optionService;
    @Autowired
    FileUtils fileUtils;

    @GetMapping("/createQandA")
    public String formQandA(Model model,
                            @RequestParam(value = "examId", required = false) Long examId,
                            @RequestParam(value = "currentIndex", required = false, defaultValue = "0") Integer currentIndex){
        if (currentIndex == null || currentIndex < 0) {
            currentIndex = 0;
        }
        if (!model.containsAttribute("questions")){
            model.addAttribute("questions", new ArrayList<QuestionDto>());
            ExamEntity examEntity = (ExamEntity) model.asMap().get("exam");
            model.addAttribute("exam", examEntity);
        }
        List<QuestionDto> questions = (List<QuestionDto>) model.getAttribute("questions");
        if (questions == null) {
            questions = new ArrayList<>();
            model.addAttribute("questions", questions);
        }
        if (questions.size() < currentIndex) {
            currentIndex = questions.size() - 1;
        }
        QuestionDto question;
        if (questions.size() > currentIndex){
            question = questions.get(currentIndex);
        }else {
            question = new QuestionDto();
            question.setOptions(new ArrayList<>(Arrays.asList(new OptionDto(), new OptionDto(),
                    new OptionDto(),new OptionDto())));
        }
        model.addAttribute("examId", examId);
        model.addAttribute("currentQuestion", question);
        model.addAttribute("currentIndex", currentIndex);
        return "admin/exam/createQandA";
    }

    @PostMapping("createQandA")
    public String createQandA(@Valid @ModelAttribute QuestionDto currentQuestion,
                              @RequestParam(required = false) Long examId,
                              @RequestParam(required = false) String finish,
                              @RequestParam(required = false) String action,
                              @RequestParam(required = false) Integer currentIndex,
                              @RequestParam(required = false) Integer correct,
                              BindingResult result, Model model, SessionStatus sessionStatus,
                              RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "admin/exam/createQandA";
        }
        List<QuestionDto> questions = (List<QuestionDto>) model.getAttribute("questions");
        for (int i = 0; i < 4; i++){
            if (correct == i) currentQuestion.getOptions().get(i).setIs_correct(true);
            else currentQuestion.getOptions().get(i).setIs_correct(false);
        }
        if (currentQuestion.getFileImage() != null && !currentQuestion.getFileImage().isEmpty()){
            try {
                String image_name = fileUtils.saveFile(currentQuestion.getFileImage());
                currentQuestion.setImage_name(image_name);
            }catch (Exception e){}
        }

        if (currentIndex != null && currentIndex >= 0 && currentIndex < questions.size()) {
            questions.set(currentIndex, currentQuestion);
        } else {
            questions.add(currentQuestion);
        }

        if ("next".equals(action)) {
            currentIndex = currentIndex < questions.size() ? currentIndex + 1 : currentIndex;
        }else if ("back".equals(action)){
            currentIndex = currentIndex > 0 ? currentIndex - 1 : currentIndex;
        }

        if ("true".equals(finish)){
            for (QuestionDto question : questions){
                QuestionEntity questionEntity = questionService.save(question, examId);
                for (OptionDto option : question.getOptions()) {
                    Long questionId = questionEntity.getId();
                    optionService.save(option, questionId);
                }
            }
            sessionStatus.setComplete();
            redirectAttributes.addFlashAttribute("message", "Tạo bài thi thành công");
            return "redirect:/admin/exam/list";
        }
        QuestionDto question;
        if (questions.size() > currentIndex){
            question = questions.get(currentIndex);
        }else {
            question = new QuestionDto();
            question.setOptions(new ArrayList<>(Arrays.asList(new OptionDto(), new OptionDto(),
                    new OptionDto(),new OptionDto())));
        }
        model.addAttribute("questions", questions);
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("currentQuestion", question);
        return "admin/exam/createQandA";
    }
}
