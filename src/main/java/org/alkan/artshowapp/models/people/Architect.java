package org.alkan.artshowapp.models.people;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.artworks.Architecture;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Architect extends Person{

    @OneToMany(mappedBy = "architect", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Architecture> architectures = new HashSet<>();
}
