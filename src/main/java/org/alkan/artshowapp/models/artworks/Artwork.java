package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
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
@Table(name = "artworks")
@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass
public abstract class Artwork extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    //  private ARTIST TODO;

    public Artwork(Long id, String name, Style style) {
        super(id);
        this.name = name;
        this.style = style;
    }

    public abstract int calculateCost();
}