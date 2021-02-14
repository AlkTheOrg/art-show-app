package org.alkan.artshowapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"", "/"})
public class WelcomeController {

    @GetMapping
    public String welcome() {
        return "welcome";
    }

    @GetMapping({"about", "/about"})
    public String about() {
        return "about";
    }
}
