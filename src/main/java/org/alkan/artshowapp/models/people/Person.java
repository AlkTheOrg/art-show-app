package org.alkan.artshowapp.models.people;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alkan.artshowapp.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Person extends BaseEntity { // extends BaseEntity

    @Column(name = "bornYear")
    private Year bornYear;

    @Column(name = "deathYear")
    private Year deathYear;

    @Size(min = 3, max = 255)
    @Column(name = "nationality")
    private String nationality;

    public Person(Long id, String name, Year bornYear, Year deathYear, String nationality) {
        super(id, name);
        this.bornYear = bornYear;
        this.deathYear = deathYear;
        this.nationality = nationality;
    }
}
