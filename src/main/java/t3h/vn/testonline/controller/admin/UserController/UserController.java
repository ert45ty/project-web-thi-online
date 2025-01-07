package t3h.vn.testonline.controller.admin.UserController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.UserDto;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user/list")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String userList(Model model){
        List<UserEntity> userList = userService.getAll();
        model.addAttribute("userList", userList);
        return "admin/user/userList";
    }

    @GetMapping("/create")
    public String formCreateUser(Model model){
        model.addAttribute("user", new UserDto());
        return "admin/user/createUser";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user")UserDto user, BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()){
            return "admin/user/createUser";
        }
        userService.save(user);
        redirectAttributes.addAttribute("message", "Thêm mới người dùng thành công");
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id){
        userService.delete(id);
        return userList(model);
    }

    @GetMapping("update/{id}")
    public String formUpdateUser(Model model, @PathVariable Long id){
        UserEntity existUser = userService.getById(id);
        model.addAttribute("existUser", existUser);
        model.addAttribute("is_update", "update");
        model.addAttribute("ID", id);
        return "admin/user/createUser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserEntity user, Model model,
                                 BindingResult result, RedirectAttributes redirectAttributes,
                                 @PathVariable Long id, @RequestParam("fileImage")MultipartFile fileImage){
        if (result.hasErrors()){
            model.addAttribute("is_update", "update");
            return "admin/user/createUser";
        }
        userService.update(user, fileImage);
        redirectAttributes.addAttribute("message", "cập nhật người dùng thành công");
        return "redirect:/user/list";
    }

    @GetMapping("detail/{id}")
    public String formDetailUser(Model model, @PathVariable Long id){
        UserEntity existUser = userService.getById(id);
        model.addAttribute("userName", existUser.getUsername());
        model.addAttribute("existUser", existUser);
        model.addAttribute("is_update", "detail");
        model.addAttribute("ID", id);
        return "admin/user/createUser";
    }
}
