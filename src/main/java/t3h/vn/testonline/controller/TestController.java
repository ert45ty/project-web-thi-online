package t3h.vn.testonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class TestController {

    @GetMapping("/test")
    public String testForm(Model model){
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(60);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedEndTime = endTime.format(formatter);
        model.addAttribute("max", 15);
        model.addAttribute("endTime", formattedEndTime);
        return "test";
    }

    @PostMapping
    public String test(){
        return "";
    }
}
