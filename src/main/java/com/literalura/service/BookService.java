package com.literalura.service;

import com.literalura.model.Book;
import com.literalura.model.Datosrespuesta;
import com.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; // IMPORTANTE: Añade este import
import java.util.Optional;

import com.literalura.repository.AuthorRepository;
import com.literalura.model.Author;


@Service
public class BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorRepository authorRepository;


    public void guardarLibro(Datosrespuesta datos) {
        if (datos.resultadoLibros() != null && !datos.resultadoLibros().isEmpty()) {
            var primerLibro = datos.resultadoLibros().get(0);
            Book libro = new Book(primerLibro);


            Optional<Book> libroExistente = repository.findByTitleIgnoreCase(libro.getTitle());


            if (libroExistente.isPresent()) {
                System.out.println("El libro ya está registrado en la base de datos.");
            } else {

                // 🔥 LÓGICA PARA EVITAR AUTORES DUPLICADOS
                Author autorDelLibro = libro.getAuthor();

                if (autorDelLibro != null) {

                    Optional<Author> autorExistente =
                            authorRepository.findByNameIgnoreCase(autorDelLibro.getName());

                    if (autorExistente.isPresent()) {
                        libro.setAuthor(autorExistente.get());
                    } else {
                        authorRepository.save(autorDelLibro);
                    }
                }

                repository.save(libro);
                System.out.println("Libro guardado exitosamente: " + libro.getTitle());
            }

        } else {
            System.out.println("No se encontraron libros con ese nombre.");
        }
    } // Aquí termina guardarLibro

    // --- ESTE ES EL NUEVO MÉTODO QUE AGREGAMOS ---
    public List<Book> obtenerTodosLosLibros() {
        return repository.findAll();
    }
    public List<Book> buscarPorIdioma(String idioma) {
        return repository.findByLanguages(idioma);
    }

    public Long contarLibrosPorIdioma(String idioma) {
        return repository.countByLanguages(idioma);
    }

    public List<Author> obtenerTodosLosAutores() {
        return authorRepository.findAll();
    }
    public List<Author> obtenerAutoresVivosEnAnio(Integer anio) {
        return authorRepository
                .findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(anio, anio);
    }


} // Esta es la llave final que cierra la clase