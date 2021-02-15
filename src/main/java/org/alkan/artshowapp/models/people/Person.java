package org.alkan.artshowapp.models.people;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Year;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Person extends BaseEntity { // extends BaseEntity

    @Column(name = "name")
    private String name;

    @Column(name = "bornYear")
    private Year bornYear;

    @Column(name = "deathYear")
    private Year deathYear;

    @Column(name = "nationality")
    private String nationality;

    public Person(Long id, String name, Year bornYear, Year deathYear, String nationality) {
        super(id);
        this.name = name;
        this.bornYear = bornYear;
        this.deathYear = deathYear;
        this.nationality = nationality;
    }
}
