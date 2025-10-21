package com.apicursos.repository;

import com.apicursos.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Filtros customizados
    List<Curso> findByNameContainingIgnoreCase(String name);

    List<Curso> findByCategoryContainingIgnoreCase(String category);
}
