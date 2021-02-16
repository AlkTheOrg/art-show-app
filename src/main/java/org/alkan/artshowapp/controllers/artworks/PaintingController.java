package org.alkan.artshowapp.controllers.artworks;

import lombok.extern.slf4j.Slf4j;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/artworks/paintings")
@Controller
@Slf4j
public class PaintingController {

    private final PaintingRepository paintings;

    public PaintingController(PaintingRepository paintings) {
        this.paintings = paintings;
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

}
