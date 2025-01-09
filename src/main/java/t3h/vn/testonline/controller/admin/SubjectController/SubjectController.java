package t3h.vn.testonline.controller.admin.SubjectController;

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

@RequestMapping("/admin/subject/list")
@Controller
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping
    public String subjectList(Model model,
                              @RequestParam(defaultValue = "") String query,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer perpage){

        model.addAttribute("response", subjectService.search(query, page, perpage));
        model.addAttribute("subject", new SubjectDto());
        return "admin/subject/subjectList";
    }

    @PostMapping
    public String querySearch(Model model, @RequestParam String query){
        return subjectList(model, query, 1, 10);
    }

    @PostMapping("/create")
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
        return "redirect:/admin/subject/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(Model model, @PathVariable Long id){
        subjectService.delete(id);
        return "redirect:/admin/subject/list";
    }

    @GetMapping("/update/{id}")
    public String updateSubject(Model model, @PathVariable Long id){
        List<SubjectEntity> subjectList = subjectService.getAll();
        SubjectEntity existSubject = subjectService.getById(id);
        model.addAttribute("existSubject", existSubject);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("is_update", "update");
        return "admin/subject/subjectList";
    }

    @PostMapping("/update/{id}")
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
        return "redirect:/admin/subject/list";
    }
}
