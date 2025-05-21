package edu.sena.bibliotecaspring.controller;

import edu.sena.bibliotecaspring.model.Libro;
import edu.sena.bibliotecaspring.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroService.findAll());
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        // Inicializar un nuevo libro con valores por defecto
        Libro libro = new Libro();
        libro.setAnoPublicacion(LocalDate.now().getYear());
        libro.setGenero("Sin clasificar");

        model.addAttribute("libro", libro);
        return "libros/formulario";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro) {
        // Validación adicional para asegurar que nunca sean nulos
        if (libro.getGenero() == null || libro.getGenero().trim().isEmpty()) {
            libro.setGenero("Sin clasificar");
        }

        try {
            libroService.save(libro);
            return "redirect:/libros";
        } catch (Exception e) {
            // Registrar el error
            System.err.println("Error al guardar libro: " + e.getMessage());
            e.printStackTrace();

            // Redirigir a una página de error
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {  // Cambiado a Integer
        model.addAttribute("libro", libroService.findById(id));
        return "libros/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Integer id) {  // Cambiado a Integer
        libroService.deleteById(id);
        return "redirect:/libros";
    }

    @GetMapping("/buscar")
    public String buscarPorTitulo(@RequestParam(required = false) String titulo,
                                  @RequestParam(required = false) String autor,
                                  Model model) {
        if (titulo != null && !titulo.isEmpty()) {
            model.addAttribute("libros", libroService.findByTitulo(titulo));
        } else if (autor != null && !autor.isEmpty()) {
            model.addAttribute("libros", libroService.findByAutor(autor));
        } else {
            model.addAttribute("libros", libroService.findAll());
        }
        return "libros/lista";
    }
}