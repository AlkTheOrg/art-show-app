package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.Style;
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
        double uniquePrice = uniquePrice();
        int cost = (int) (length * width * height * uniquePrice);

        return cost;
    }

    private double uniquePrice() {
        double result = 0.0;
        try {
            switch (getStyle().getName()) {
                case ("Gothic"):
                    result = 1.0;
                    break;
                case ("Baroque"):
                    result = 0.8;
                    break;
                default:
                    result = 0.6;
                    break;
            }
        } catch (Exception e){
            result = 0.6;
        }

        return result;
    }
}
