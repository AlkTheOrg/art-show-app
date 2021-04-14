package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.repositories.artworks.PaintingRepository;
import org.alkan.artshowapp.services.PaintingService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PaintingServiceImp implements PaintingService {

    private final PaintingRepository paintingRepository;

    public PaintingServiceImp(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    @Override
    public Set<Painting> findAll() {
        return new HashSet<>(paintingRepository.findAll());
    }

    @Override
    public Painting findById(Long id) {
        return paintingRepository.findById(id).orElse(null);
    }

    @Override
    public Painting findByName(String name) {
        return paintingRepository.findByName(name);
    }

    @Override
    public Painting save(Painting object) {
        return paintingRepository.save(object);
    }

    @Override
    public void delete(Painting object) {
        paintingRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        paintingRepository.deleteById(id);
    }
}
