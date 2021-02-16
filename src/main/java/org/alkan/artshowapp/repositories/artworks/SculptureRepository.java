package org.alkan.artshowapp.repositories.artworks;

import org.alkan.artshowapp.models.artworks.Sculpture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SculptureRepository extends JpaRepository<Sculpture, Long> {
}
