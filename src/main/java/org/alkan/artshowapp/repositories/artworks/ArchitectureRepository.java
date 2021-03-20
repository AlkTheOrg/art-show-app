package org.alkan.artshowapp.repositories.artworks;

import org.alkan.artshowapp.models.artworks.Architecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchitectureRepository extends JpaRepository<Architecture, Long> {
    Architecture findByName(String name);
}
