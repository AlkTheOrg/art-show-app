package org.alkan.artshowapp.repositories.people;

import org.alkan.artshowapp.models.people.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
}
