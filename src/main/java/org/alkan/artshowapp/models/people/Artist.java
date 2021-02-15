package org.alkan.artshowapp.models.people;

import lombok.*;
import org.alkan.artshowapp.models.Period;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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

    @Builder
    public Artist(Long id, String name, Year bornYear, Year deathYear, String nationality, Set<Period> periods) {
        super(id, name, bornYear, deathYear, nationality);
        this.periods = periods;
    }
}