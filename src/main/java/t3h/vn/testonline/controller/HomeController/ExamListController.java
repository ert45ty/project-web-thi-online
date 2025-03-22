package t3h.vn.testonline.controller.HomeController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.CommentDto;
import t3h.vn.testonline.entities.CommentEntity;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/subject/exam")
@SessionAttributes({"subjectList", "subject"})
public class ExamListController {

    @Autowired
    ExamService examService;
    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    CommentService commentService;
    @Autowired
    ResultService resultService;


    @GetMapping
    public String examList(Model model, @RequestParam("topicId") Long topicId,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "5") Integer perpage){

        String notification = (String) model.asMap().get("message");
        if (notification != null && !notification.isEmpty()){
            model.addAttribute("message", notification);
        }
        TopicEntity topicEntity = topicService.getById(topicId);
        List<TopicEntity> topicList = topicEntity.getSubject().getTopics();
        List<ExamEntity> examList  = topicEntity.getExams();
        List<CommentEntity> commentList = topicEntity.getComments();
        List<Float> highestScore = new ArrayList<>();
        for (int i = 0; i< examList.size(); i++){
            Float hs = resultService.findHighestScoreByExamId(examList.get(i).getId());
            highestScore.add(hs);
        }

        model.addAttribute("newComment", new CommentDto());
        model.addAttribute("comments", commentList);
        model.addAttribute("currentTopic", topicEntity);
        model.addAttribute("topicList", topicList);
        model.addAttribute("response", examService.getAllByTopicIdAndStatus(topicId, page, perpage));
        model.addAttribute("subject", topicEntity.getSubject());
        model.addAttribute("subjectList", subjectService.getAllAndStatusIsLike());
        model.addAttribute("highestScore", highestScore);

        return "customer/examList";
    }

    @PostMapping
    public String createComment(@Valid @ModelAttribute("newComment") CommentDto commentDto,
                                BindingResult result, Model model,
                                @RequestParam("topicId") Long topicId,
                                @AuthenticationPrincipal UserDetails user){
        if (user == null){
            model.addAttribute("message", "Bạn cần phải đăng nhập để có thể sử dụng phần bình luận");
            return examList(model, topicId, 1, 5);
        }
        if (result.hasErrors()){
            model.addAttribute("message", result.getFieldError().getDefaultMessage());
            return examList(model, topicId, 1, 5);
        }
        commentService.createComment(commentDto, topicId, user.getUsername());
        return "redirect:/subject/exam?topicId=" + topicId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("topicId") Long topicId,
                         @RequestParam("commentId") Long commentId,
                         RedirectAttributes redirectAttributes){
        commentService.delete(commentId);
        redirectAttributes.addFlashAttribute("message", "*Xóa bình luận thành công");
        return "redirect:/subject/exam?topicId=" + topicId;
    }

}
