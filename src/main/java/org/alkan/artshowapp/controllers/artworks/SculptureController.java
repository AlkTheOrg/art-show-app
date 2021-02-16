package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.repositories.artworks.SculptureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/artworks/sculptures")
@Controller
public class SculptureController {

    private final SculptureRepository sculptures;

    public SculptureController(SculptureRepository sculptures) {
        this.sculptures = sculptures;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listSculptures(Model model) {
        model.addAttribute("sculptures", sculptures.findAll());
        return "artworks/sculptures/index";
    }
}
