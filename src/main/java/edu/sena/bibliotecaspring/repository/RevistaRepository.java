package edu.sena.bibliotecaspring.repository;

import edu.sena.bibliotecaspring.model.Revista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevistaRepository extends JpaRepository<Revista, Integer> {
    List<Revista> findByCategoriaContaining(String categoria);
    List<Revista> findByEditorialContaining(String editorial);
    List<Revista> findByAutorContaining(String autor);
}