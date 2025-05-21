package edu.sena.bibliotecaspring.service;

import edu.sena.bibliotecaspring.model.Libro;
import edu.sena.bibliotecaspring.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Libro findById(Integer id) {  // Cambiado a Integer
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
    }

    public Libro save(Libro libro) {
        // Asegurar que el tipo siempre sea "LIBRO"
        libro.setTipo("LIBRO");

        // Asegurar que género nunca sea nulo
        if (libro.getGenero() == null || libro.getGenero().trim().isEmpty()) {
            libro.setGenero("Sin clasificar");
        }

        return libroRepository.save(libro);
    }

    public void deleteById(Integer id) {  // Cambiado a Integer
        libroRepository.deleteById(id);
    }

    public List<Libro> findByTitulo(String titulo) {
        return libroRepository.findByTituloContaining(titulo);
    }

    public List<Libro> findByAutor(String autor) {
        return libroRepository.findByAutorContaining(autor);
    }
}