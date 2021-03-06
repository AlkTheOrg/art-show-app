package org.alkan.artshowapp.models.people;

import lombok.*;
import org.alkan.artshowapp.models.Period;
import org.alkan.artshowapp.models.artworks.Painting;
import org.alkan.artshowapp.models.artworks.Sculpture;

import javax.persistence.*;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Artist extends Person {

    @ManyToMany(mappedBy = "artists")
    private Set<Period> periods = new HashSet<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Painting> paintings = new HashSet<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Sculpture> sculptures = new HashSet<>();

    @Builder
    public Artist(Long id, String name, Year bornYear, Year deathYear, String nationality, Set<Period> periods) {
        super(id, name, bornYear, deathYear, nationality);
        this.periods = periods;
    }

    public int getNumOfArtworks() {
        return paintings.size() + sculptures.size();
    }
}
