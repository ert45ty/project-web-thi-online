package t3h.vn.testonline.controller.TopicController;

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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/topic/list")
public class TopicController {

    @Autowired
    TopicService topicService;
    @Autowired
    SubjectService subjectService;

    @GetMapping
    public String topicList(Model model,
                            @RequestParam(defaultValue = "0") Integer subject){
        List<TopicEntity> topicList;
        List<SubjectEntity> subjectList = subjectService.getAll();

        topicList = topicService.getAllBySubjectId(subjectList.get(subject).getId());

        model.addAttribute("topicList", topicList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("subjectIndex", subject);

        return "admin/topic/topicList";
    }

    @GetMapping("/create")
    public String create(Model model){
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("topic", new TopicDto());
        return "admin/topic/createTopic";
    }

    @PostMapping("/create")
    public String createTopic(@Valid @ModelAttribute("topic") TopicDto topic, Model model, RedirectAttributes redirectAttributes, BindingResult result){

        if(result.hasErrors()){
            return "admin/topic/createTopic";
        }
        topicService.save(topic);
        redirectAttributes.addAttribute("message", "Thêm chủ đề thành công");
        return "redirect:/topic/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id){
        topicService.delete(id);
        return topicList(model, 0);
    }

    @GetMapping("/update/{id}")
    public String updateTopic(Model model, @PathVariable Long id){
        TopicEntity existTopic = topicService.getById(id);
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("existTopic", existTopic);
        model.addAttribute("is_update", "update");
        model.addAttribute("ID", id);
        return "admin/topic/createTopic";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String update(@Valid @ModelAttribute("topic") TopicEntity topic, Model model, RedirectAttributes redirectAttributes, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            model.addAttribute("is_update", "update");
            return "admin/topic/createTopic";
        }
        topicService.update(topic);
        redirectAttributes.addAttribute("message", "Cập nhật chủ đề thành công");
        return "redirect:/topic/list";
    }
}
