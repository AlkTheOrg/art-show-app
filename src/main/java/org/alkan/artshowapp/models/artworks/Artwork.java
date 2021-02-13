package org.alkan.artshowapp.models.artworks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Artwork extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "style")
    private String style;

    //  private ARTIST TODO;

    public Artwork(Long id, String name, String style) {
        super(id);
        this.name = name;
        this.style = style;
    }

    public abstract int calculateCost();
}
