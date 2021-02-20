package org.alkan.artshowapp.bootstrap;

import org.alkan.artshowapp.models.Period;
import org.alkan.artshowapp.models.Style;
import org.alkan.artshowapp.models.artworks.Architecture;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.models.artworks.Sculpture;
import org.alkan.artshowapp.models.people.Artist;
import org.alkan.artshowapp.repositories.artworks.ArchitectureRepository;
import org.alkan.artshowapp.repositories.artworks.SculptureRepository;
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
    private final ArchitectureRepository architectures;
    private final SculptureRepository sculptures;

    public DataBootstrapper(PaintingRepository paintings, StyleRepository styles, ArtistRepository artists, PeriodRepository periods, ArchitectureRepository architectures, SculptureRepository sculptures) {
        this.paintings = paintings;
        this.styles = styles;
        this.artists = artists;
        this.periods = periods;
        this.architectures = architectures;
        this.sculptures = sculptures;
    }

    @Override
    public void run(String... args) throws Exception {
        Style ren = new Style();
        ren.setName("Renaissance");
        ren.setId(1L);
        styles.save(ren);

        Style bar = new Style();
        bar.setName("Baroque");
        bar.setId(2L);
        styles.save(bar);

        Style got = new Style();
        got.setName("Gothic");
        got.setId(3L);
        styles.save(got);

        paintings.save(new Painting(1L, "name1", ren, 12.72f, 24.3f));
        paintings.save(new Painting(2L, "name2", bar, 13.72f, 24.3f));
        paintings.save(new Painting(3L, "name3", ren, 14.72f, 24.3f));
        paintings.save(new Painting(4L, "name4", bar, 15.72f, 24.3f));
        paintings.save(new Painting(5L, "name5", ren, 16.72f, 24.3f));

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

        Architecture architecture;
        for (long i = 1; i<10; i++) {
            architecture = new Architecture();
            architecture.setId(i);
            architecture.setName("Arch" + i);
            architecture.setWidth(12);
            architecture.setHeight(6);
            architecture.setLength(63);
            architectures.save(architecture);
        }

        Sculpture sculpture;
        for (long i = 1; i<10; i++) {
            sculpture = new Sculpture();
            sculpture.setId(i);
            sculpture.setName("Arch" + i);
            sculpture.setWeight((int) (500 * i));
            sculptures.save(sculpture);
        }
    }
}
