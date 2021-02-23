package org.alkan.artshowapp.models.artworks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.BaseEntity;
import org.alkan.artshowapp.models.Style;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "artwork") // same as default @Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Artwork extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    public Artwork(Long id, String name, Style style) {
        super(id, name);
        this.style = style;
    }

    public abstract int calculateCost();
}