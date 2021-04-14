package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.artworks.Sculpture;
import org.alkan.artshowapp.repositories.artworks.SculptureRepository;
import org.alkan.artshowapp.services.SculptureService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SculptureServiceImp implements SculptureService {

    private final SculptureRepository sculptureRepository;

    public SculptureServiceImp(SculptureRepository sculptureRepository) {
        this.sculptureRepository = sculptureRepository;
    }

    @Override
    public Set<Sculpture> findAll() {
        return new HashSet<>(sculptureRepository.findAll());
    }

    @Override
    public Sculpture findById(Long id) {
        return sculptureRepository.findById(id).orElse(null);
    }

    @Override
    public Sculpture findByName(String name) {
        return sculptureRepository.findByName(name);
    }

    @Override
    public Sculpture save(Sculpture object) {
        return sculptureRepository.save(object);
    }

    @Override
    public void delete(Sculpture object) {
        sculptureRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        sculptureRepository.deleteById(id);
    }
}
