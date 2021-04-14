package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.Style;
import org.alkan.artshowapp.repositories.StyleRepository;
import org.alkan.artshowapp.services.StyleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StyleServiceImp implements StyleService {

    private final StyleRepository styleRepository;

    public StyleServiceImp(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public Set<Style> findAll() {
        return new HashSet<>(styleRepository.findAll());
    }

    @Override
    public Style findById(Long id) {
        return styleRepository.findById(id).orElse(null);
    }

    @Override
    public Style findByName(String name) {
        return styleRepository.findByName(name);
    }

    @Override
    public Style save(Style object) {
        return styleRepository.save(object);
    }

    @Override
    public void delete(Style object) {
        styleRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        styleRepository.deleteById(id);
    }
}
