package org.alkan.artshowapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.people.Artist;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Period extends BaseEntity {

    @Column(name = "name")
    String name;

    @ManyToMany
    @JoinTable(name = "artist_period",
            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "period_id",
                    referencedColumnName = "id"))
    private Set<Artist> artists = new HashSet<>();
}