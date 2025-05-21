package edu.sena.bibliotecaspring.repository;

import edu.sena.bibliotecaspring.model.DVD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DVDRepository extends JpaRepository<DVD, Integer> {
    List<DVD> findByGeneroContaining(String genero);
    List<DVD> findByDirectorContaining(String director);
    // También se puede agregar búsqueda por autor si es necesario
    List<DVD> findByAutorContaining(String autor);
}