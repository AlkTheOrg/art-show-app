package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.artworks.Material;
import org.alkan.artshowapp.repositories.artworks.MaterialRepository;
import org.alkan.artshowapp.services.MaterialService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MaterialServiceImp implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImp(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Set<Material> findAll() {
        return new HashSet<>(materialRepository.findAll());
    }

    @Override
    public Material findById(Long id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public Material findByName(String name) {
        return materialRepository.findByName(name);
    }

    @Override
    public Material save(Material object) {
        return materialRepository.save(object);
    }

    @Override
    public void delete(Material object) {
        materialRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        materialRepository.deleteById(id);
    }
}
