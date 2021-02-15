package org.alkan.artshowapp.repositories;

import org.alkan.artshowapp.models.people.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
