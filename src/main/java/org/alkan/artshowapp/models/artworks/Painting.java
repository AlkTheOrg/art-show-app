package org.alkan.artshowapp.models.artworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Painting(Long id, String name, String style, float length, float width) {
        super(id, name, style);
        this.length = length;
        this.width = width;
    }

    @Override
    public int calculateCost() {
        String style = getStyle();
        double uniquePrice = uniquePrice(style);
        int cost = (int) (length * width * uniquePrice);

        return cost;
    }

    private double uniquePrice(String style) {
        double result = 0.0;

        switch (style) {
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
