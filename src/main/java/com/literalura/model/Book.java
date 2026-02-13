package com.literalura.model;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "libros")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private String languages;
    private Double downloadCount;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    // 1. Constructor vacío (Obligatorio para Spring Boot/JPA)
    public Book() {}

    // 2. Constructor que "traduce" de GutendexBook a esta clase Book
    public Book(GutendexBook datos) {
        this.title = datos.titulo();

// Mapeo del autor: Tomamos el primero de la lista
        if (datos.autores() != null && !datos.autores().isEmpty()) {

            var datosAutor = datos.autores().get(0);

            Author nuevoAutor = new Author();
            nuevoAutor.setName(datosAutor.nombre());
            nuevoAutor.setBirthYear(datosAutor.fechaDeNacimiento());
            nuevoAutor.setDeathYear(datosAutor.fechaDeFallecimiento());

            this.author = nuevoAutor;

        } else {
            this.author = null;
        }

        // Mapeo del idioma: Tomamos el primero de la lista
        if (datos.idiomas() != null && !datos.idiomas().isEmpty()) {
            this.languages = datos.idiomas().get(0);
        } else {
            this.languages = "Desconocido";
        }

        this.downloadCount = datos.numeroDeDescargas();
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLanguages() { return languages; }
    public void setLanguages(String languages) { this.languages = languages; }

    public Double getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Double downloadCount) { this.downloadCount = downloadCount; }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}