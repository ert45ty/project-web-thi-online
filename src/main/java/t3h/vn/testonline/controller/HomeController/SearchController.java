package t3h.vn.testonline.controller.HomeController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.SubjectService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ExamService examService;
    private final SubjectService subjectService;
    private final ResultService resultService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "query", required = false) String query,
                         @RequestParam(value = "subject", required = false) Long subjectId,
                         @RequestParam(value = "time", required = false) Integer time,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer perpage){
        Page<ExamEntity> examEntities = examService.searchInCustomerView(subjectId, time, query, page, perpage);
        List<Float> highestScore = new ArrayList<>();
        if (examEntities.hasContent()){
            for (ExamEntity exam : examEntities.getContent()) {
                highestScore.add(resultService.findHighestScoreByExamId(exam.getId()));
            }
        }

        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("time", time);
        model.addAttribute("query", query);
        model.addAttribute("response", examEntities);
        model.addAttribute("highestScore", highestScore);
        return "customer/searchResult";
    }

}
