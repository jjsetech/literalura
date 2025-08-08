package com.jjsetech.literalura.repository;

import com.jjsetech.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface LivrosRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloAndAutorNome(String titulo, String autorNome);

    List<Livro> findByIdioma(String idioma);

    Integer countByIdioma(String idioma);

    List<Livro> findTop10ByOrderByDownloadsDesc();

}
