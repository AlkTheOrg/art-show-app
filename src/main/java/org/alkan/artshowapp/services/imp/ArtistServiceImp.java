package org.alkan.artshowapp.services.imp;

import org.alkan.artshowapp.models.people.Artist;
import org.alkan.artshowapp.repositories.people.ArtistRepository;
import org.alkan.artshowapp.services.ArtistService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImp(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Set<Artist> findAll() {
        return new HashSet<>(artistRepository.findAll());
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public Artist findByName(String name) {
        return artistRepository.findByName(name);
    }

    @Override
    public Artist save(Artist object) {
        return artistRepository.save(object);
    }

    @Override
    public void delete(Artist object) {
        artistRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        artistRepository.deleteById(id);
    }
}
