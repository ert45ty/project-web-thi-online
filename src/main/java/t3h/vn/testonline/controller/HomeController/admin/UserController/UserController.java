package t3h.vn.testonline.controller.HomeController.admin.UserController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.dto.request.UserDto;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.UserService;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userList(Model model,
                           @RequestParam(defaultValue = "") String query,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer perpage){

        String message = (String) model.asMap().get("message");

        model.addAttribute("message", message);
        model.addAttribute("response", userService.search(query, page, perpage));
        return "admin/user/userList";
    }

    @PostMapping
    public String querySearch(Model model, @RequestParam String query){
        return userList(model, query, 1, 10);
    }

    @GetMapping("/create")
    public String formCreateUser(Model model){
        model.addAttribute("user", new UserDto());
        return "admin/user/createUser";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user")UserDto user,
                             BindingResult result, Model model,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("repassword") String repassword){
        if (!repassword.equals(user.getPassword())){
            model.addAttribute("message", "Mật khẩu và nhập lại mật khẩu không khớp");
            return "admin/user/createUser";
        }
        if (result.hasErrors()){
            return "admin/user/createUser";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Thêm mới người dùng thành công");
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id,
                         RedirectAttributes redirectAttributes){
        userService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa tài khoản thành công");
        return "redirect:/admin/user";
    }

    @GetMapping("update/{id}")
    public String formUpdateUser(Model model, @PathVariable Long id){
        UserEntity existUser = userService.getById(id);
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setFullname(existUser.getFullname());
        userDto.setUsername(existUser.getUsername());
        userDto.setPassword("******");
        userDto.setEmail(existUser.getEmail());
        userDto.setRole(existUser.getRole());
        model.addAttribute("user", userDto);
        model.addAttribute("is_update", "update");
        model.addAttribute("ID", id);
        return "admin/user/createUser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UserDto user, Model model,
                                 BindingResult result, RedirectAttributes redirectAttributes,
                                 @PathVariable Long id, @RequestParam("fileImage")MultipartFile fileImage){
        if (result.hasErrors()){
            model.addAttribute("is_update", "update");
            return "admin/user/createUser";
        }
        UserEntity userEntity = userService.getById(user.getId());
        userEntity.setFullname(user.getFullname());
        userEntity.setEmail(user.getEmail());
        userEntity.setRole(user.getRole());
        userService.update(userEntity, fileImage);
        redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công");
        return "redirect:/admin/user";
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
