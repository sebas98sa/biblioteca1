package edu.sena.bibliotecaspring.repository;

import edu.sena.bibliotecaspring.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {  // Cambiado a Integer
    List<Libro> findByTituloContaining(String titulo);
    List<Libro> findByAutorContaining(String autor);
    List<Libro> findByGeneroContaining(String genero);
}