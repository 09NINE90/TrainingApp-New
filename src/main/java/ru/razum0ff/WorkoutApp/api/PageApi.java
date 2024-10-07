package ru.razum0ff.WorkoutApp.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageApi {
    @GetMapping("/user/create")
    public String userCreate(){
        return "signup-form";
    }
}
