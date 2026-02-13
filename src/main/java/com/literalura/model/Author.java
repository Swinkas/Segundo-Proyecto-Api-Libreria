package com.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer birthYear;
    private Integer deathYear;

    public Author() {}

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    @Override
    public String toString() {
        return """
               Autor: %s
               Nacimiento: %s
               Fallecimiento: %s
               ------------------------
               """.formatted(
                name,
                birthYear != null ? birthYear : "Desconocido",
                deathYear != null ? deathYear : "Vivo"
        );
    }
}

