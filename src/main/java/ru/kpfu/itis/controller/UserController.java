package ru.kpfu.itis.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.kpfu.itis.model.User;
import ru.kpfu.itis.model.form.LoginForm;
import ru.kpfu.itis.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "Server is working";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @ModelAttribute("loginForm") LoginForm loginForm,
                        ModelMap map) {
        map.put("error", error);
        return "login";
    }

    @GetMapping("/register")
    public String register(ModelMap map) {
        map.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerHandler(@Valid @ModelAttribute("user") User user,
                                  BindingResult result,
                                  ModelMap map) {

        if (!result.hasErrors()) {

            if (userService.register(user)) {
                return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#list").build();
            }

        }

        map.put("user", user);
        map.put("message", "Username already taken");
        return "register";
    }
}
