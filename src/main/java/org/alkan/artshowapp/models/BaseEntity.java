package org.alkan.artshowapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(name = "name")
    private String name;

    public BaseEntity(Long id, @NotBlank @Size(min = 2, max = 255) String name) {
        this.id = id;
        this.name = name;
    }

    // Useful when deciding the text of submit buttons on forms.
    public boolean isNew() {
        return this.id == null;
    }
}
