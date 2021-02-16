package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @Override
    public int calculateCost() {
        return 0;
    }
}
