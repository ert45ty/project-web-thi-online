package t3h.vn.testonline.controller.admin.SubjectController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.List;

@RequestMapping("/admin/subject")
@Controller
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @Autowired
    TopicService topicService;
    @Autowired
    ExamService examService;

    @GetMapping
    public String subjectList(Model model,
                              @RequestParam(defaultValue = "") String query,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer perpage){

        String message = (String) model.asMap().get("message");

        model.addAttribute("message", message);
        model.addAttribute("response", subjectService.search(query, page, perpage));
        model.addAttribute("subject", new SubjectDto());
        return "admin/subject/subjectList";
    }

    @PostMapping
    public String querySearch(Model model, @RequestParam String query){
        return subjectList(model, query, 1, 10);
    }

    @PostMapping("/create")
    public String createSubject(@Valid @ModelAttribute("subject") SubjectDto subject,
                                BindingResult result, Model model,
                                RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            List<SubjectEntity> subjectList = subjectService.getAll();
            model.addAttribute("subjectList", subjectList);
            return "admin/subject/subjectList";
        }else {
                subjectService.save(subject);
        }
        redirectAttributes.addFlashAttribute("message", "Thêm môn thi thành công");
        return "redirect:/admin/subject";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        SubjectEntity subjectEntity =  subjectService.getById(id);
        subjectEntity.setStatus(0);
        List<TopicEntity> topicEntities = subjectEntity.getTopics();
        for (int i=0; i < topicEntities.size(); i++){
            topicEntities.get(i).setStatus(0);
            for (int j = 0; j <topicEntities.get(i).getExams().size(); j ++){
                topicEntities.get(i).getExams().get(j).setStatus(0);
                examService.update(topicEntities.get(i).getExams().get(j));
            }
            topicService.update(topicEntities.get(i));
        }
        subjectService.update(subjectEntity, null);
        redirectAttributes.addFlashAttribute("message", "Hủy môn thi thành công");
        return "redirect:/admin/subject";
    }

    @GetMapping("/update/{id}")
    public String updateSubject(Model model, @PathVariable Long id){
        SubjectEntity existSubject = subjectService.getById(id);
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName(existSubject.getName());
        model.addAttribute("subject", subjectDto);
        model.addAttribute("ID", id);
//        model.addAttribute("is_update", "update");
        return "admin/subject/createSubject";
    }

    @PostMapping("/update/{id}")
    public String update(Model model, @Valid @ModelAttribute("subject") SubjectDto subject,
                         BindingResult result, RedirectAttributes redirectAttributes,
                         @PathVariable Long id, MultipartFile fileImage){
        if (result.hasErrors()){
            return "admin/subject/createSubject";
        }
        SubjectEntity subjectEntity = subjectService.getById(subject.getId());
        subjectEntity.setName(subject.getName());
        subjectService.update(subjectEntity, fileImage);

        redirectAttributes.addFlashAttribute("message", "Sửa môn thi thành công");
        return "redirect:/admin/subject";
    }
}
