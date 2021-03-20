package org.alkan.artshowapp.repositories;

import org.alkan.artshowapp.models.Period;
import org.alkan.artshowapp.models.artworks.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
    Period findByName(String name);
}
