package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.people.Architect;
import org.alkan.artshowapp.repositories.people.ArchitectRepository;
import org.alkan.artshowapp.services.ArchitectService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ArchitectServiceImp implements ArchitectService {

    private final ArchitectRepository architectRepository;

    public ArchitectServiceImp(ArchitectRepository architectRepository) {
        this.architectRepository = architectRepository;
    }

    @Override
    public Set<Architect> findAll() {
        return new HashSet<>(architectRepository.findAll());
    }

    @Override
    public Architect findById(Long id) {
        return architectRepository.findById(id).orElse(null);
    }

    @Override
    public Architect findByName(String name) {
        return architectRepository.findByName(name);
    }

    @Override
    public Architect save(Architect object) {
        return architectRepository.save(object);
    }

    @Override
    public void delete(Architect object) {
        architectRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        architectRepository.deleteById(id);
    }
}
