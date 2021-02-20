package org.alkan.artshowapp.controllers.artworks;

import org.alkan.artshowapp.models.artworks.Sculpture;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.alkan.artshowapp.repositories.artworks.MaterialRepository;
import org.alkan.artshowapp.repositories.artworks.SculptureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/artworks/sculptures")
@Controller
public class SculptureController {

    private final SculptureRepository sculptures;
    private final StyleRepository styles;
    private final MaterialRepository materials;

    public SculptureController(SculptureRepository sculptures, StyleRepository styles, MaterialRepository materials) {
        this.sculptures = sculptures;
        this.styles = styles;
        this.materials = materials;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listSculptures(Model model) {
        model.addAttribute("sculptures", sculptures.findAll());
        return "artworks/sculptures/index";
    }

    @GetMapping({"/{sculptureId}", "/{sculptureId}/"})
    public String showPainting(@PathVariable("sculptureId") Long sculptureId, Model model) {
        model.addAttribute("sculpture", sculptures.findById(sculptureId).orElse(null));
        return "/artworks/sculptures/show";
    }

    @GetMapping({"/new", "/new/"})
    public String showCreationForm(Model model) {
        model.addAttribute("styles", styles.findAll());
        model.addAttribute("materials", materials.findAll());
        model.addAttribute("sculpture", new Sculpture());
        return "/artworks/sculptures/new";
    }

    @PostMapping({"", "/"})
    public String processCreationForm(@Valid Sculpture sculpture, BindingResult result, Model model) {
        if (result.hasErrors()) {
            for(ObjectError err : result.getAllErrors())
                System.out.println(err.getDefaultMessage());
            return "artworks/sculptures/new";
        }
        else {
            Sculpture savedSculpture = sculptures.save(sculpture);
            return "redirect:/artworks/sculptures/" + savedSculpture.getId();
        }
    }

    @GetMapping({"/update/{sculptureId}", "/update/{sculptureId}/"})
    public String showUpdateSculptureForm(@PathVariable("sculptureId") Long sculptureId, Model model) {
        Sculpture sculpture = sculptures.findById(sculptureId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sculpture Id: " + sculptureId));
        model.addAttribute("styles", styles.findAll());
        model.addAttribute("materials", materials.findAll());
        model.addAttribute("sculpture", sculpture);
        return "/artworks/sculptures/update";
    }

    @PostMapping({"/update/{sculptureId}", "/update/{sculptureId}/"})
    public String updateSculpture(@PathVariable("sculptureId") Long sculptureId, @Valid Sculpture sculpture,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            sculpture.setId(sculptureId);
            return "artworks/sculpture/update";
        }
        Sculpture updatedSculpture = sculptures.save(sculpture);
        return "redirect:/artworks/sculptures/" + updatedSculpture.getId();
    }
}
