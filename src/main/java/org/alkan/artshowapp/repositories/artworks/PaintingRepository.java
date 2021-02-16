package org.alkan.artshowapp.repositories.artworks;

import org.alkan.artshowapp.models.artworks.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
