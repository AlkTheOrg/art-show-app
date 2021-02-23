package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.people.Artist;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sculpture")
public class Sculpture extends Artwork{

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Max(value = 1000000, message = "Weight of the sculpture can not be more than 1000000 kg")
    @Min(value = 1, message = "Weight of the sculpture can not be less than 1kg")
    @Column(name = "weight")
    private int weight;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Override
    public int calculateCost() {
        double uniquePrice = uniquePrice();
        return (int) (weight * uniquePrice);
    }

    private double uniquePrice() {
        double result = 0.0;

        try {
            switch (getMaterial().getName()) {
                case ("Bronze"):
                    result = 180;
                    break;
                case ("Marble"):
                    result = 15;
                    break;
                default:
                    result = 100;
                    break;
            }
        } catch (Exception e) {
            result = 100;
        }
        return result;
    }
}
