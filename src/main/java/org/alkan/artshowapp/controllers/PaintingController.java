package org.alkan.artshowapp.controllers;

import org.alkan.artshowapp.repositories.PaintingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/artworks/paintings")
@Controller
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

}
