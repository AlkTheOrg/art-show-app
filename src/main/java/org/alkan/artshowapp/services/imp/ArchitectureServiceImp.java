package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.artworks.Architecture;
import org.alkan.artshowapp.repositories.artworks.ArchitectureRepository;
import org.alkan.artshowapp.services.ArchitectureService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ArchitectureServiceImp implements ArchitectureService {

    private final ArchitectureRepository architectureRepository;

    public ArchitectureServiceImp(ArchitectureRepository architectureRepository) {
        this.architectureRepository = architectureRepository;
    }

    @Override
    public Set<Architecture> findAll() {
        return new HashSet<>(architectureRepository.findAll());
    }

    @Override
    public Architecture findById(Long id) {
        return architectureRepository.findById(id).orElse(null);
    }

    @Override
    public Architecture findByName(String name) {
        return architectureRepository.findByName(name);
    }

    @Override
    public Architecture save(Architecture object) {
        return architectureRepository.save(object);
    }

    @Override
    public void delete(Architecture object) {
        architectureRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        architectureRepository.deleteById(id);
    }
}
