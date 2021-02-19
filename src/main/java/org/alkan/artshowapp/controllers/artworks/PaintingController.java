package org.alkan.artshowapp.controllers.artworks;

import lombok.extern.slf4j.Slf4j;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequestMapping("/artworks/paintings")
@Controller
@Slf4j
public class PaintingController {

    private final PaintingRepository paintings;
    private final StyleRepository styles;

    public PaintingController(PaintingRepository paintings, StyleRepository styles) {
        this.paintings = paintings;
        this.styles = styles;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listPaintings(Model model) {
        model.addAttribute("paintings", paintings.findAll());

        return "artworks/paintings/index";
    }

    @GetMapping({"/{paintingId}", "/{paintingId}/"})
    public ModelAndView showPainting(@PathVariable("paintingId") Long paintingId) {
        ModelAndView mav = new ModelAndView("/artworks/paintings/show");
        mav.addObject(paintings.findById(paintingId).orElse(null));
        return mav;
    }


    @GetMapping({"/new", "/new/"})
    public String initCreationForm(Model model) {
        model.addAttribute("painting", new Painting());
        model.addAttribute("styles", styles.findAll());
        return "artworks/paintings/new";
    }

    @PostMapping({"", "/"})
    public String processCreationForm(@Valid Painting painting, BindingResult result) {
        if (result.hasErrors()){
            for(ObjectError err : result.getAllErrors())
                System.out.println(err.getDefaultMessage());
            return "redirect:/artworks/paintings/new";
        }
        else {
//            styles.save(painting.getStyle());
            Painting savedPainting = paintings.save(painting);
            return "redirect:/artworks/paintings/" + savedPainting.getId();
        }
    }
}
