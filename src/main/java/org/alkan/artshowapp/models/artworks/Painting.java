package org.alkan.artshowapp.models.artworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.Style;
import org.alkan.artshowapp.models.people.Artist;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "painting") // same as default @Table
public class Painting extends Artwork{

    private float length;
    private float width;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Painting(Long id, String name, Style style, float length, float width) {
        super(id, name, style);
        this.length = length;
        this.width = width;
    }

    @Override
    public int calculateCost() {
        double uniquePrice = uniquePrice();
        int cost = (int) (length * width * uniquePrice);

        return cost;
    }

    private double uniquePrice() {
        double result = 0.0;
        try {
            switch (getStyle().getName()) {
                case ("Renaissance"):
                    result = 7.0;
                    break;
                case ("Baroque"):
                    result = 5.5;
                    break;
                default:
                    result = 4.5;
                    break;
            }
        } catch (Exception e) {
            result = 4.5;
        }
        return result;
    }
}