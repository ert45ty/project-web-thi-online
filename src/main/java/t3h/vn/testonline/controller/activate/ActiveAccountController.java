package t3h.vn.testonline.controller.activate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.service.UserService;

@Controller
public class ActiveAccountController {
    @Autowired
    UserService userService;

    @GetMapping("/{code}")
    public String activeAccount(@PathVariable String code, RedirectAttributes redirectAttributes){
        UserEntity userEntity = userService.getByCode(code);
        userEntity.setStatus(1);
        userService.update(userEntity, null);
        redirectAttributes.addFlashAttribute("message","Kích hoạt tài khoản thành công!");
        return "redirect:/login";
    }
}
