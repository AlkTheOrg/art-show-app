package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.people.Architect;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Architecture extends Artwork{

    @Column(name = "length")
    private double length; // m

    @Column(name = "width")
    private double width; // m

    @Column(name = "height")
    private double height; // m

    @ManyToOne
    @JoinColumn(name = "architect_it")
    private Architect architect;

    @Override
    public int calculateCost() {
        return 0;
    }
}
