package com.literalura.repository;

import com.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Para evitar guardar libros repetidos
    Optional<Book> findByTitleIgnoreCase(String title);

    // Derived Query para buscar libros por idioma
    List<Book> findByLanguages(String languages);

    Long countByLanguages(String language);

}

