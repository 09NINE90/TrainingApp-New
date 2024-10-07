package ru.razum0ff.WorkoutApp.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.razum0ff.WorkoutApp.dto.UserDTO;

@Controller
public class PageApi {

    @GetMapping("/create")
    public String userCreate(Model model){
        model.addAttribute("user",new UserDTO());
        return "signup";
    }
}
