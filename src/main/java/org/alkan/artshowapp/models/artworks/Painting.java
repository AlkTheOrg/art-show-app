package org.alkan.artshowapp.models.artworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.Style;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "paintings")
public class Painting extends Artwork{

    private float length;
    private float width;

    public Painting(Long id, String name, Style style, float length, float width) {
        super(id, name, style);
        this.length = length;
        this.width = width;
    }

    @Override
    public int calculateCost() {
        double uniquePrice = uniquePrice(getStyle());
        int cost = (int) (length * width * uniquePrice);

        return cost;
    }

    private double uniquePrice(Style style) {
        double result = 0.0;

        switch (style.getName()) {
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
        return result;
    }
}