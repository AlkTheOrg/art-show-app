package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Material extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "material")
    private Set<Sculpture> sculptures = new HashSet<>();
}
