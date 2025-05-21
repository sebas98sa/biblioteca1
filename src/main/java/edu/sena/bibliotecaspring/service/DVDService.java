package edu.sena.bibliotecaspring.service;

import edu.sena.bibliotecaspring.model.DVD;
import edu.sena.bibliotecaspring.repository.DVDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DVDService {

    @Autowired
    private DVDRepository dvdRepository;

    public List<DVD> findAll() {
        return dvdRepository.findAll();
    }

    public DVD findById(Integer id) {
        return dvdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DVD no encontrado con id: " + id));
    }

    public DVD save(DVD dvd) {
        // Asegurar que el tipo siempre sea "DVD"
        dvd.setTipo("DVD");

        // Asegurar que género nunca sea nulo
        if (dvd.getGenero() == null || dvd.getGenero().trim().isEmpty()) {
            dvd.setGenero("Sin clasificar");
        }

        return dvdRepository.save(dvd);
    }

    public void deleteById(Integer id) {
        dvdRepository.deleteById(id);
    }

    public List<DVD> findByGenero(String genero) {
        return dvdRepository.findByGeneroContaining(genero);
    }

    public List<DVD> findByDirector(String director) {
        return dvdRepository.findByDirectorContaining(director);
    }

    public List<DVD> findByAutor(String autor) {
        return dvdRepository.findByAutorContaining(autor);
    }
}