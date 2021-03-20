package org.alkan.artshowapp.repositories;

import org.alkan.artshowapp.models.Style;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleRepository extends JpaRepository<Style, Long> {
    Style findByName(String name);
}
