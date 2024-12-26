package t3h.vn.testonline.controller.SubjectController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.service.SubjectService;

import java.util.List;

@Controller
//@RequestMapping("/admin/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/list")
    public String subjectList(Model model){
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        return "subjectList";
    }

    @PostMapping("/create")
    public String createSubject(Model model, @Valid @ModelAttribute("subject") SubjectDto subject,
                              BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "/list";
        }else {
                subjectService.save(subject);
        }
        redirectAttributes.addAttribute("message", "Thêm môn thi thành công");
        return "redirect:/list";
    }
}
