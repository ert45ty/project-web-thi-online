package t3h.vn.testonline.controller.HomeController.admin.TopicController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.request.TopicDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.entities.TopicEntity;
import t3h.vn.testonline.service.ExamService;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.service.TopicService;

import java.util.List;

@Controller
@RequestMapping("/admin/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final SubjectService subjectService;
    private final ExamService examService;


    @GetMapping
    public String topicList(Model model,
                            @RequestParam(defaultValue = "0") Integer subject,
                            @RequestParam(defaultValue = "") String query,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer perpage){

        List<SubjectEntity> subjectList = subjectService.getAll();
        Long id = subjectList.get(subject).getId();
        String message = (String) model.asMap().get("message");
        if (query.isEmpty()){
            model.addAttribute("response", topicService.getAllBySubjectId(id, page, perpage));
        }else {
            model.addAttribute("response", topicService.search(id,query, page, perpage));
        }
        model.addAttribute("message", message);
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

    //CREATE
    @GetMapping("/create")
    public String create(Model model){
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("topic", new TopicDto());
        return "admin/topic/createTopic";
    }

    @PostMapping("/create")
    public String createTopic(@Valid @ModelAttribute("topic")TopicDto topic,
                              BindingResult result,
                              Model model, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            model.addAttribute("subjectList", subjectService.getAll());
            return "admin/topic/createTopic";
        }
        topicService.save(topic);
        redirectAttributes.addFlashAttribute("message", "*Thêm chủ đề thành công");
        return "redirect:/admin/topic";
    }

    //UPDATE
    @GetMapping("/update")
    public String updateTopic(Model model, @RequestParam(value = "topicId") Long topicId){
        TopicEntity existTopic = topicService.getsById(topicId);
        TopicDto topicDto = new TopicDto();;
        topicDto.setId(existTopic.getId());
        topicDto.setName(existTopic.getName());
        topicDto.setDescription(existTopic.getDescription());
        topicDto.setSubject_id(existTopic.getSubject().getId());
        List<SubjectEntity> subjectList = subjectService.getAll();
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("topic", topicDto);
        model.addAttribute("topicId", topicId);
        model.addAttribute("is_update", "update");
        return "admin/topic/createTopic";
    }

    @PostMapping("/update")
    @Transactional
    public String update(@Valid @ModelAttribute("topic") TopicDto topic,
                         Model model, RedirectAttributes redirectAttributes,
                         BindingResult result, @RequestParam(value = "topicId") Long topicId){
        if(result.hasErrors()){
            model.addAttribute("is_update", "update");
            return "admin/topic/createTopic";
        }
        SubjectEntity subjectEntity = subjectService.getById(topic.getSubject_id());
        if (subjectEntity.getStatus() == 0 && topic.getStatus() == 1){
            model.addAttribute("is_update", "update");
            model.addAttribute("error", "Không thể cập nhật trạng thái khi môn học bạn chọn đang ở trạng thái không kích hoạt");
            return "admin/topic/createTopic";
        }
        TopicEntity topicEntity = topicService.getsById(topicId);
        topicEntity.setName(topic.getName());
        topicEntity.setDescription(topic.getDescription());
        topicEntity.setSubject(subjectEntity);
        topicEntity.setStatus(topic.getStatus());
        topicService.update(topicEntity);
        redirectAttributes.addFlashAttribute("message", "Cập nhật chủ đề thành công");
        return "redirect:/admin/topic";
    }

    //DELETE
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        TopicEntity topicEntity = topicService.getById(id);
        for (int i = 0; i< topicEntity.getExams().size(); i++){
            ExamEntity examEntity = topicEntity.getExams().get(i);
            if (examEntity.getStatus() == 0) continue;
            examEntity.setStatus(0);
            examService.update(examEntity);
        }
        topicEntity.setStatus(0);
        topicService.update(topicEntity);
        redirectAttributes.addFlashAttribute("message", "Hủy chủ đề thành công");
        return "redirect:/admin/topic";
    }

}
