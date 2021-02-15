package org.alkan.artshowapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.artworks.Artwork;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "styles")
public class Style extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "style")
    private Set<Artwork> artworks = new HashSet<>();
}