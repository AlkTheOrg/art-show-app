package org.alkan.artshowapp.controllers.artworks;

import lombok.extern.slf4j.Slf4j;
import org.alkan.artshowapp.exceptions.NotFoundException;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
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
    private final ArtistRepository artists;

    public PaintingController(PaintingRepository paintings, StyleRepository styles, ArtistRepository artists) {
        this.paintings = paintings;
        this.styles = styles;
        this.artists = artists;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listPaintings(Model model) {
        model.addAttribute("paintings", paintings.findAll());

        return "artworks/paintings/index";
    }

    @GetMapping({"/{paintingId}", "/{paintingId}/"})
    public ModelAndView showPainting(@PathVariable("paintingId") Long paintingId) {
        ModelAndView mav = new ModelAndView("artworks/paintings/show");
        mav.addObject(paintings.findById(paintingId)
                .orElseThrow(() -> new NotFoundException("Id " + paintingId + " is an invalid Painting id.")));
        return mav;
    }

    @GetMapping({"/new", "/new/"})
    public String showCreationForm(Model model) {
        model.addAttribute("painting", new Painting());
        model.addAttribute("styles", styles.findAll());
        model.addAttribute("artists", artists.findAll());
        return "artworks/paintings/new";
    }

    @PostMapping({"", "/"})
    public String processCreationForm(@Valid Painting painting, BindingResult result) {
        if (result.hasErrors()){
            for(ObjectError err : result.getAllErrors())
                System.out.println(err.getDefaultMessage());
            return "artworks/paintings/new";
        }
        else {
            Painting savedPainting = paintings.save(painting);
            return "redirect:/artworks/paintings/" + savedPainting.getId();
        }
    }

    @GetMapping({"/update/{paintingId}", "/update/{paintingId}/"})
    public String showUpdatePaintingForm(@PathVariable("paintingId") Long paintingId, Model model) {
        Painting painting = paintings.findById(paintingId)
                .orElseThrow(() -> new NotFoundException("Id " + paintingId + " is an invalid Painting id."));

        model.addAttribute("painting", painting);
        model.addAttribute("styles", styles.findAll());
        model.addAttribute("artists", artists.findAll());
        return "artworks/paintings/update";
    }

    @PostMapping({"/update/{paintingId}", "/update/{paintingId}/"})
    public String updatePainting(@PathVariable("paintingId") Long paintingId, @Valid Painting painting,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            painting.setId(paintingId);
            return "artworks/paintings/update";
        }
        Painting updatedPainting = paintings.save(painting);
        return "redirect:/artworks/paintings/" + updatedPainting.getId();
    }

    @GetMapping("/delete/{paintingId}")
    public String deletePainting(@PathVariable("paintingId") Long paintingId, Model model) {
        Painting painting = paintings.findById(paintingId)
                .orElseThrow(() -> new NotFoundException("Id " + paintingId + " is an invalid Painting id."));
        paintings.delete(painting);
        return "redirect:/artworks/paintings";
    }
}
