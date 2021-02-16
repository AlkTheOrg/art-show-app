package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.people.Artist;

import javax.persistence.*;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sculpture")
public class Sculpture extends Artwork{

    // Material

    // weight



    @Override
    public int calculateCost() {
        return 0;
    }
}
