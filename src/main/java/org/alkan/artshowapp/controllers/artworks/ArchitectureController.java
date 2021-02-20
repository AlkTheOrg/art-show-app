package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.repositories.artworks.ArchitectureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/artworks/architectures")
@Controller
public class ArchitectureController {

    private final ArchitectureRepository architectures;

    public ArchitectureController(ArchitectureRepository architectures) {
        this.architectures = architectures;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listArchitectures(Model model) {
        model.addAttribute("architectures", architectures.findAll());
        return "artworks/architectures/index";
    }

    @GetMapping({"/{architectureId}", "/{architectureId}/"})
    public ModelAndView showArchitecture(@PathVariable("architectureId") Long architectureId) {
        ModelAndView mav = new ModelAndView("/artworks/architectures/show");
        mav.addObject(architectures.findById(architectureId).orElse(null));
        return mav;
    }
}
