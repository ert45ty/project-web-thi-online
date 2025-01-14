package t3h.vn.testonline.controller.admin.TopicController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.TopicDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.List;

@Controller
@RequestMapping("/admin/topic/list")
public class TopicController {

    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;

    @GetMapping
    public String topicList(Model model,
                            @RequestParam(defaultValue = "0") Integer subject,
                            @RequestParam(defaultValue = "") String query,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer perpage){

        List<SubjectEntity> subjectList = subjectService.getAll();
        Long id = subjectList.get(subject).getId();
        String message = (String) model.asMap().get("message");

        model.addAttribute("message", message);
        model.addAttribute("response", topicService.search(id, query, page, perpage));
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("subjectIndex", subject);

        return "admin/topic/topicList";
    }

    @PostMapping
    public String querySearch(Model model,
                              @RequestParam(defaultValue = "0") Integer subject,
                              @RequestParam String query){
        model.addAttribute("subjectIndex", subject);
        return topicList(model, subject, query, 1, 10);
    }

    @GetMapping("/create")
    public String create(Model model){
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("topic", new TopicDto());
        return "admin/topic/createTopic";
    }

    @PostMapping("/create")
    public String createTopic(@Valid @ModelAttribute("topic")TopicDto topic,
                              Model model, RedirectAttributes redirectAttributes,
                              BindingResult result){

        if(result.hasErrors()){
            model.addAttribute("subjectList", subjectService.getAll());
            return "admin/topic/createTopic";
        }
        topicService.save(topic);
        redirectAttributes.addFlashAttribute("message", "*Thêm chủ đề thành công");
        return "redirect:/admin/topic/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        topicService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa chủ đề thành công");
        return "redirect:/admin/topic/list";
    }

    @GetMapping("/update/{id}")
    public String updateTopic(Model model, @PathVariable Long id){
        TopicEntity existTopic = topicService.getById(id);
        TopicDto topicDto = new TopicDto();;
        topicDto.setId(existTopic.getId());
        topicDto.setName(existTopic.getName());
        topicDto.setDescription(existTopic.getDescription());
        topicDto.setSubject_id(existTopic.getSubject().getId());
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("topic", topicDto);
        model.addAttribute("is_update", "update");
        model.addAttribute("ID", id);
        return "admin/topic/createTopic";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String update(@Valid @ModelAttribute("topic") TopicDto topic,
                         Model model, RedirectAttributes redirectAttributes,
                         BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            model.addAttribute("is_update", "update");
            return "admin/topic/createTopic";
        }
        TopicEntity topicEntity = topicService.getById(topic.getId());
        topicEntity.setName(topic.getName());
        topicEntity.setDescription(topic.getDescription());
        topicEntity.setSubject(subjectService.getById(topic.getSubject_id()));
        topicService.update(topicEntity);
        redirectAttributes.addFlashAttribute("message", "Cập nhật chủ đề thành công");
        return "redirect:/admin/topic/list";
    }
}
