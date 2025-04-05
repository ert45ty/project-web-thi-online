package t3h.vn.testonline.controller.HomeController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import t3h.vn.testonline.service.ResultService;
import t3h.vn.testonline.service.SubjectService;

@Controller
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankController {

    private final SubjectService subjectService;
    private final ResultService resultService;

    @GetMapping
    public String templateRanking(Model model, @RequestParam Long examId){

        model.addAttribute("resultList", resultService.getTop10ExamsHighestScore(examId, 1, 10));
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        return "customer/ranking";
    }
}
