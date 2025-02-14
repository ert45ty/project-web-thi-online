package t3h.vn.testonline.controller.HomeController.ProfileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import t3h.vn.testonline.entities.*;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.UserAnswerService;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"totalQuestion","resultId","answers","answerList"})
@RequestMapping("/profile/history/detail")
public class ExamDetailController {

    @Autowired
    ResultService resultService;
    @Autowired
    ExamService examService;
    @Autowired
    UserAnswerService userAnswerService;

    @Transactional
    @GetMapping
    public String showExamDetail(Model model,
                             @RequestParam Long resultId,
                             @RequestParam(defaultValue = "0") Integer currentIndex){

        List<QuestionEntity> questionList;
        ResultEntity resultEntity = resultService.getById(resultId);
        ExamEntity examEntity = examService.getExamById(resultEntity.getExam().getId());

        if (!model.containsAttribute("totalQuestion")){


            questionList = examEntity.getQuestions();

            List<UserAnswerEntity> answerList = userAnswerService.getAllByResultId(resultId);

            List<Boolean> answers = new ArrayList<>();
            for (int i = 0; i < questionList.size(); i++){
                if (answerList.get(i).getOption() != null){
                    for (int j = 0; j < questionList.get(0).getOptions().size(); j++) {
                        if (answerList.get(i).getOption().getId() == questionList.get(i).getOptions().get(j).getId()){
                            Boolean answer = questionList.get(i).getOptions().get(j).getIs_correct();
                            answers.add(answer);
                        }
                    }
                }else {
                    answers.add(false);
                }
            }
            model.addAttribute("totalQuestion",examEntity.getTotal_question());
            model.addAttribute("resultId", resultId);

            model.addAttribute("answers", answers);
            model.addAttribute("answerList", answerList);


        }
        List<UserAnswerEntity> answerList = (List<UserAnswerEntity>) model.getAttribute("answerList");
        questionList = examEntity.getQuestions();
        QuestionEntity currentQuestion = questionList.get(currentIndex);
        List<OptionEntity> currentOptionList = currentQuestion.getOptions();
        UserAnswerEntity currentAnswer = answerList.get(currentIndex);


        model.addAttribute("exam", examEntity);
        model.addAttribute("questionList", questionList);
        model.addAttribute("currentIndex", currentIndex);
        model.addAttribute("currentQuestion", currentQuestion);
        model.addAttribute("currentOptions", currentOptionList);
        model.addAttribute("currentAnswer", currentAnswer.getOption() != null ? currentAnswer.getOption().getId() : -1);
        return "customer/profile/examDetail";
    }

    @PostMapping
    public String examDetail(Model model,
                             @RequestParam Integer currentIndex,
                             @RequestParam(required = false) String action,
                             @RequestParam(required = false) String finish,
                             SessionStatus sessionStatus){
        Integer totalQuestion = (Integer) model.getAttribute("totalQuestion");
        Long resultId = (Long) model.getAttribute("resultId");
        if ("next".equals(action)) {
            currentIndex = currentIndex < totalQuestion ? currentIndex + 1 : currentIndex;
        }else if ("back".equals(action)){
            currentIndex = currentIndex > 0 ? currentIndex - 1 : currentIndex;
        }else if (action != null) {
            for (int i = 1; i <= totalQuestion; i++) {
                String iStr = i + "";
                if (iStr.equals(action)) {
                    currentIndex = i - 1;
                }
            }
        }
        if ("true".equals(finish)){
            sessionStatus.setComplete();
            return "redirect:/profile";
        }

        return showExamDetail(model,resultId,currentIndex);
    }

}
