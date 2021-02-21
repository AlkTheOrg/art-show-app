package org.alkan.artshowapp.controllers.people;

import org.alkan.artshowapp.models.people.Artist;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping({"/{id}", "/{id}/"})
    public String showArtist(@PathVariable("id") Long id, Model model) {
        Artist artist = artists.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        model.addAttribute("artist", artist);
        return "/people/artists/show";
    }
}
