package t3h.vn.testonline.controller.HomeController.ProfileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import t3h.vn.testonline.entities.*;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.UserAnswerService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile/history/detail")
public class ExamDetailController {

    @Autowired
    ResultService resultService;
    @Autowired
    ExamService examService;
    @Autowired
    UserAnswerService userAnswerService;

    @GetMapping
    public String showExamDetail(Model model,
                             @RequestParam Long resultId){


        ResultEntity resultEntity = resultService.getById(resultId);
        ExamEntity examEntity = examService.getExamById(resultEntity.getExam().getId());
        List<QuestionEntity> questionList = examEntity.getQuestions();

        Float score = resultService.getById(resultId).getScore();
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
            model.addAttribute("score", score);
            model.addAttribute("questionList", questionList);
            model.addAttribute("exam", examEntity);
            model.addAttribute("resultId", resultId);
            model.addAttribute("answers", answers);
            model.addAttribute("answerList", answerList);

        return "customer/profile/examDetail";
    }


}
