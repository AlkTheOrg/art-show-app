package org.alkan.artshowapp.repositories;

import org.alkan.artshowapp.models.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
