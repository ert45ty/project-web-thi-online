package t3h.vn.testonline.controller.admin.CommentController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.service.CommentService;

@Controller
@RequestMapping("/admin/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public String commentList(Model model,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer perpage){

        String message = (String) model.asMap().get("message");
        model.addAttribute("message", message);
        model.addAttribute("response", commentService.getAllComments(page, perpage));
        return "admin/comment/commentList";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("commentId") Long id,
                         RedirectAttributes redirectAttributes){
        commentService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa bình luận thành công");
        return "redirect:/admin/comment";
    }
}
