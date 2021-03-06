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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "painting") // same as default @Table
public class Painting extends Artwork{

    @Min(value = 0, message = "Length can not be less than 1.")
    @Max(value = 1000, message = "Length can not be more than 100")
//    @Digits(fraction = 0, integer = 3)
    private float length;

    @Min(value = 0, message = "Width can not be less than 1.")
    @Max(value = 1000, message = "Width can not be more than 100")
    private float width;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    public Painting(Long id, String name, Style style, float length, float width, Artist artist) {
        super(id, name, style);
        this.length = length;
        this.width = width;
        this.artist = artist;
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