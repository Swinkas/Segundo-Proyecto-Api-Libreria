package com.literalura.repository;

import com.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameIgnoreCase(String name);

    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(
            Integer year1, Integer year2);
}

