package org.alkan.artshowapp.controllers.people;

import org.alkan.artshowapp.models.people.Architect;
import org.alkan.artshowapp.repositories.people.ArchitectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/people/architects")
@Controller
public class ArchitectController {

    private final ArchitectRepository architects;

    public ArchitectController(ArchitectRepository architects) {
        this.architects = architects;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listArchitects(Model model) {
        model.addAttribute("architects", architects.findAll());
        return "people/architects/index";
    }

    @GetMapping({"/{architectId}", "/{architectId}/"})
    public String showArchitect(@PathVariable("architectId") Long architectId, Model model) {
        Architect architect = architects.findById(architectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + architectId));
        model.addAttribute("architect", architect);
        return "people/architects/show";
    }
}
