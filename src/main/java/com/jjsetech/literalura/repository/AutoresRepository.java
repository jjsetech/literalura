package com.jjsetech.literalura.repository;
import com.jjsetech.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(Integer year1, Integer year2);

}
