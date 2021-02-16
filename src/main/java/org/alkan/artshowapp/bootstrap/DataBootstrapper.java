package org.alkan.artshowapp.bootstrap;

import org.alkan.artshowapp.models.Period;
import org.alkan.artshowapp.models.Style;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.models.people.Artist;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.alkan.artshowapp.repositories.PeriodRepository;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataBootstrapper implements CommandLineRunner {

    private final PaintingRepository paintings;
    private final StyleRepository styles;
    private final ArtistRepository artists;
    private final PeriodRepository periods;

    public DataBootstrapper(PaintingRepository paintings, StyleRepository styles, ArtistRepository artists, PeriodRepository periods) {
        this.paintings = paintings;
        this.styles = styles;
        this.artists = artists;
        this.periods = periods;
    }

    @Override
    public void run(String... args) throws Exception {
        Style baroque = new Style();
        baroque.setName("baroq");
        baroque.setId(1L);
        styles.save(baroque);

        Style renosance = new Style();
        renosance.setName("renosance");
        renosance.setId(2L);
        styles.save(renosance);

        paintings.save(new Painting(1L, "name1", renosance, 12.72f, 24.3f));
        paintings.save(new Painting(2L, "name2", baroque, 13.72f, 24.3f));
        paintings.save(new Painting(3L, "name3", renosance, 14.72f, 24.3f));
        paintings.save(new Painting(4L, "name4", baroque, 15.72f, 24.3f));
        paintings.save(new Painting(5L, "name5", renosance, 16.72f, 24.3f));

        Period period1 = new Period();
        period1.setId(1L);
        period1.setName("period1");

        Period period2 = new Period();
        period2.setId(2L);
        period2.setName("period2");

        periods.save(period1);
        periods.save(period2);

        Set<Period> periods1 = new HashSet<>();
        periods1.add(period1);

        Set<Period> periods2 = new HashSet<>();
        periods2.add(period1);
        periods2.add(period2);

        artists.save(Artist.builder().id(1L).bornYear(Year.of(1999)).deathYear(Year.now()).name("alkan").periods(periods1).build());
        artists.save(Artist.builder().id(2L).bornYear(Year.of(1999)).deathYear(Year.now()).name("alkan").periods(periods2).build());
        artists.save(Artist.builder().id(3L).bornYear(Year.of(1999)).deathYear(Year.now()).name("alkan").periods(periods1).build());
        artists.save(Artist.builder().id(4L).bornYear(Year.of(1999)).deathYear(Year.now()).name("alkan").periods(periods2).build());

        Artist artist1 = artists.findById(1L).orElse(null);
        Artist artist2 = artists.findById(2L).orElse(null);
        Artist artist3 = artists.findById(3L).orElse(null);
        Artist artist4 = artists.findById(4L).orElse(null);

        Set<Artist> allArtists = new HashSet<>();
        allArtists.add(artist1);
        allArtists.add(artist2);
        allArtists.add(artist3);
        allArtists.add(artist4);

        Set<Artist> artistsDuble = new HashSet<>();
        artistsDuble.add(artist2);
        artistsDuble.add(artist4);

        period1.setArtists(allArtists);
        period2.setArtists(artistsDuble);

        periods.save(period1);
        periods.save(period2);
    }
}
