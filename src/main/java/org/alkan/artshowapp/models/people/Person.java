package org.alkan.artshowapp.models.people;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Year;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Person { // extends BaseEntity

    @Column(name = "name")
    private String name;

    @Column(name = "bornYear")
    private Year bornYear;

    @Column(name = "deathYear")
    private Year deathYear;

    @Column(name = "nationality")
    private String nationality;
}
