package org.alkan.artshowapp.repositories.artworks;

import org.alkan.artshowapp.models.artworks.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
