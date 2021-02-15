package org.alkan.artshowapp.controllers;

import org.alkan.artshowapp.repositories.ArtistRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/people/artists")
@Controller
public class ArtistController {

    private final ArtistRepository artists;

    public ArtistController(ArtistRepository artists) {
        this.artists = artists;
    }

    @GetMapping({"", "/", "/index", "/index/"})
    public String listArtists(Model model) {
        model.addAttribute("artists", artists.findAll());

        return "people/artists/index";
    }
}
