package org.alkan.artshowapp.bootstrap;

import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.repositories.PaintingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBootstrapper implements CommandLineRunner {

    private final PaintingRepository paintings;

    public DataBootstrapper(PaintingRepository paintings) {
        this.paintings = paintings;
    }

    @Override
    public void run(String... args) throws Exception {
        paintings.save(new Painting(1L, "name1", "horror1", 12.72f, 24.3f));
        paintings.save(new Painting(2L, "name2", "horror2", 13.72f, 24.3f));
        paintings.save(new Painting(3L, "name3", "horror3", 14.72f, 24.3f));
        paintings.save(new Painting(4L, "name4", "horror4", 15.72f, 24.3f));
        paintings.save(new Painting(5L, "name5", "horror5", 16.72f, 24.3f));
    }
}
