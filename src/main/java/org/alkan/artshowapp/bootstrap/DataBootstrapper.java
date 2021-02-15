package org.alkan.artshowapp.bootstrap;

import org.alkan.artshowapp.models.Style;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.repositories.PaintingRepository;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBootstrapper implements CommandLineRunner {

    private final PaintingRepository paintings;
    private final StyleRepository styles;

    public DataBootstrapper(PaintingRepository paintings, StyleRepository styles) {
        this.paintings = paintings;
        this.styles = styles;
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
    }
}
