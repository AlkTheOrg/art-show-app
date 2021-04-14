package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.Period;
import org.alkan.artshowapp.repositories.PeriodRepository;
import org.alkan.artshowapp.services.PeriodService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PeriodServiceImp implements PeriodService {

    private final PeriodRepository periodRepository;

    public PeriodServiceImp(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    @Override
    public Set<Period> findAll() {
        return new HashSet<>(periodRepository.findAll());
    }

    @Override
    public Period findById(Long id) {
        return periodRepository.findById(id).orElse(null);
    }

    @Override
    public Period findByName(String name) {
        return periodRepository.findByName(name);
    }

    @Override
    public Period save(Period object) {
        return periodRepository.save(object);
    }

    @Override
    public void delete(Period object) {
        periodRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        periodRepository.deleteById(id);
    }
}
