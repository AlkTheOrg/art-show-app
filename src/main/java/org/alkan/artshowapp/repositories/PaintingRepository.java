package org.alkan.artshowapp.repositories;

import org.alkan.artshowapp.models.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
