package t3h.vn.testonline.controller.SubjectController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.service.SubjectService;

import java.util.List;

//@RequestMapping("")
@Controller
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/list")
    public String subjectList(Model model){
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("subject", new SubjectDto());
        return "admin/subject/subjectList";
    }

    @PostMapping("/list")
    public String createSubject(Model model, @Valid @ModelAttribute("subject") SubjectDto subject,
                              BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            List<SubjectEntity> subjectList = subjectService.getAll();
            model.addAttribute("subjectList", subjectList);
            return "admin/subject/subjectList";
        }else {
                subjectService.save(subject);
        }
        redirectAttributes.addAttribute("message", "Thêm môn thi thành công");
        return "redirect:/list";
    }

    @GetMapping("/list/delete/{id}")
    public String deleteSubject(Model model, @PathVariable Long id){
        subjectService.delete(id);
        return "redirect:/list";
    }

    @GetMapping("/list/update/{id}")
    public String updateSubject(Model model, @PathVariable Long id){
        List<SubjectEntity> subjectList = subjectService.getAll();
        SubjectEntity existSubject = subjectService.getById(id);
        model.addAttribute("existSubject", existSubject);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("is_update", "update");
        return "admin/subject/subjectList";
    }

    @PostMapping("/list/update/{id}")
    public String update(Model model, @Valid @ModelAttribute("subject") SubjectDto subject,
                                BindingResult result, RedirectAttributes redirectAttributes, @PathVariable Long id){
        if (result.hasErrors()){
            List<SubjectEntity> subjectList = subjectService.getAll();
            SubjectEntity existSubject = subjectService.getById(id);
            model.addAttribute("existSubject", existSubject);
            model.addAttribute("subjectList", subjectList);
            model.addAttribute("is_update", "update");
            return "admin/subject/subjectList";
        }else {
            subjectService.save(subject);
        }
        redirectAttributes.addAttribute("message", "Thêm môn thi thành công");
        return "redirect:/list";
    }
}
