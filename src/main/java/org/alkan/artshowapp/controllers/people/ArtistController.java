package org.alkan.artshowapp.controllers.people;

import lombok.extern.slf4j.Slf4j;
import org.alkan.artshowapp.models.people.Artist;
import org.alkan.artshowapp.repositories.PeriodRepository;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/people/artists")
@Controller
@Slf4j
public class ArtistController {

    private final ArtistRepository artists;
    private final PeriodRepository periods;

    public ArtistController(ArtistRepository artists, PeriodRepository periods) {
        this.artists = artists;
        this.periods = periods;
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

    @GetMapping({"/new", "/new/"})
    public String showCreationForm(Model model) {
        model.addAttribute("periods", periods.findAll());
        model.addAttribute("artist", new Artist());
        return "people/artists/new";
    }

    @PostMapping({"", "/"})
    public String processCreationForm(@Valid Artist artist, BindingResult result,
                  @RequestParam(name = "artist-is-alive", defaultValue = "off") String isAlive) {
        if(result.hasErrors()) {
            for(ObjectError err : result.getAllErrors())
                System.out.println(err.getDefaultMessage());
            return "people/artists/new";
        }
        else {
            if(isAlive.equals("on"))
                artist.setDeathYear(null);
            artist.getPeriods().forEach((period) -> period.getArtists().add(artist));
            Artist savedArtist = artists.save(artist);
            return "redirect:/people/artists/" + savedArtist.getId();
        }
    }
}
