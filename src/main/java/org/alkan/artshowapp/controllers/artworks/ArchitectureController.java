package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.exceptions.NotFoundException;
import org.alkan.artshowapp.models.artworks.Architecture;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.alkan.artshowapp.repositories.artworks.ArchitectureRepository;
import org.alkan.artshowapp.repositories.people.ArchitectRepository;
import org.alkan.artshowapp.services.ArchitectService;
import org.alkan.artshowapp.services.ArchitectureService;
import org.alkan.artshowapp.services.StyleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequestMapping("/artworks/architectures")
@Controller
@SessionAttributes({"styles", "architects"})
public class ArchitectureController {

    private final ArchitectureService architectures;
    private final StyleService styles;
    private final ArchitectService architects;

    public ArchitectureController(ArchitectureService architectures, StyleService styles, ArchitectService architects) {
        this.architectures = architectures;
        this.styles = styles;
        this.architects = architects;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listArchitectures(Model model) {
        model.addAttribute("architectures", architectures.findAll());
        return "artworks/architectures/index";
    }

    @GetMapping({"/{architectureId}", "/{architectureId}/"})
    public ModelAndView showArchitecture(@PathVariable("architectureId") Long architectureId) {
        ModelAndView mav = new ModelAndView("artworks/architectures/show");
        mav.addObject(architectures.findById(architectureId));
        return mav;
    }

    @GetMapping({"/new", "/new/"})
    public String showCreationForm(Model model) {
        model.addAttribute("architecture", new Architecture());
        model.addAttribute("styles", styles.findAll());
        model.addAttribute("architects", architects.findAll());
        return "artworks/architectures/new";
    }

    @PostMapping({"", "/"})
    public String processCreationForm(@Valid Architecture architecture, BindingResult result) {
        if (result.hasErrors()) {
            for(ObjectError err : result.getAllErrors())
                System.out.println(err.getDefaultMessage());
            return "artworks/architectures/new";
        }
        else {
            Architecture savedArchitecture = architectures.save(architecture);
            return "redirect:/artworks/architectures/" + savedArchitecture.getId();
        }
    }

    @GetMapping({"/update/{architectureId}", "/update/{architectureId}/"})
    public String showUpdateArchitectureForm(@PathVariable("architectureId") Long architectureId, Model model) {
        Architecture architecture = architectures.findById(architectureId);
        model.addAttribute("architecture", architecture);
        model.addAttribute("styles", styles.findAll());
        model.addAttribute("architects", architects.findAll());
        return "artworks/architectures/update";
    }

    @PostMapping({"/update/{architectureId}", "/update/{architectureId}/"})
    public String updateArchitecture(@PathVariable("architectureId") Long architectureId,
                                     @Valid Architecture architecture, BindingResult result, Model model) {
        if (result.hasErrors()) {
            architecture.setId(architectureId);
            return "artworks/architectures/update";
        }
        architecture.setId(architectureId);
        Architecture updatedArchitecture = architectures.save(architecture);
        return "redirect:/artworks/architectures/" + updatedArchitecture.getId();
    }

    @GetMapping({"/delete/{architectureId}", "/delete/{architectureId}/"})
    public String deleteArchitecture(@PathVariable("architectureId") Long architectureId, Model model) {
        Architecture architecture = architectures.findById(architectureId);
        architectures.delete(architecture);
        return "redirect:/artworks/architectures";
    }
}
