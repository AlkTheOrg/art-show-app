package org.alkan.artshowapp.controllers.people;

import org.alkan.artshowapp.exceptions.NotFoundException;
import org.alkan.artshowapp.models.people.Architect;
import org.alkan.artshowapp.repositories.people.ArchitectRepository;
import org.alkan.artshowapp.services.ArchitectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/people/architects")
@Controller
public class ArchitectController {

    private final ArchitectService architects;

    public ArchitectController(ArchitectService architects) {
        this.architects = architects;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listArchitects(Model model) {
        model.addAttribute("architects", architects.findAll());
        return "people/architects/index";
    }

    @GetMapping({"/{architectId}", "/{architectId}/"})
    public String showArchitect(@PathVariable("architectId") Long architectId, Model model) {
        Architect architect = architects.findById(architectId);
        model.addAttribute("architect", architect);
        return "people/architects/show";
    }

    @GetMapping({"/new", "/new/"})
    public String initCreationForm(Model model) {
        model.addAttribute("architect", new Architect());
        return "people/architects/new";
    }

    @PostMapping({"", "/"})
    public String processCreationForm(@Valid Architect architect, BindingResult result,
                  @RequestParam(name = "architect-is-alive", defaultValue = "off") String isAlive) {
        if (result.hasErrors()) {
            for(ObjectError err : result.getAllErrors())
                System.out.println(err.getDefaultMessage());
            return "people/architects/new";
        }
        else {
            if(isAlive.equals("on"))
                architect.setDeathYear(null);
            Architect savedArchitect = architects.save(architect);
            return "redirect:/people/architects/" + savedArchitect.getId();
        }
    }

    @GetMapping({"/update/{architectId}", "/update/{architectId}/"})
    public String showUpdateArchitectForm(Model model, @PathVariable Long architectId) {
        Architect architect = architects.findById(architectId);
        model.addAttribute("architect", architect);
        return "people/architects/update";
    }

    @PostMapping({"/update/{architectId}", "/update/{architectId}/"})
    public String updateArchitect(@PathVariable Long architectId, @Valid Architect architect,
                                  BindingResult result) {
        if (result.hasErrors()) {
            architect.setId(architectId);
            return "people/architects/update";
        }
        architect.setId(architectId);
        Architect updatedArchitect = architects.save(architect);
        return "redirect:/people/architects/" + updatedArchitect.getId();
    }

    @GetMapping({"/delete/{architectId}", "/delete/{architectId}/"})
    public String deleteArchitect(@PathVariable Long architectId) {
        Architect architect = architects.findById(architectId);
        architects.delete(architect);
        return "redirect:/people/architects";
    }
}
