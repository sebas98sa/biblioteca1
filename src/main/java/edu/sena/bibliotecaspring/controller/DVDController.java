package edu.sena.bibliotecaspring.controller;

import edu.sena.bibliotecaspring.model.DVD;
import edu.sena.bibliotecaspring.service.DVDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/dvds")
public class DVDController {

    @Autowired
    private DVDService dvdService;

    @GetMapping
    public String listarDVDs(Model model) {
        model.addAttribute("dvds", dvdService.findAll());
        return "dvds/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        // Inicializar un nuevo DVD con valores por defecto
        DVD dvd = new DVD();
        dvd.setAnoPublicacion(LocalDate.now().getYear());
        dvd.setGenero("Sin clasificar");
        dvd.setDuracion(0); // Duración predeterminada

        model.addAttribute("dvd", dvd);
        return "dvds/formulario";
    }

    @PostMapping("/guardar")
    public String guardarDVD(@ModelAttribute DVD dvd) {
        // Validación adicional para asegurar que género nunca sea nulo
        if (dvd.getGenero() == null || dvd.getGenero().trim().isEmpty()) {
            dvd.setGenero("Sin clasificar");
        }

        try {
            dvdService.save(dvd);
            return "redirect:/dvds";
        } catch (Exception e) {
            // Registrar el error
            System.err.println("Error al guardar DVD: " + e.getMessage());
            e.printStackTrace();

            // Redirigir a una página de error
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("dvd", dvdService.findById(id));
        return "dvds/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDVD(@PathVariable Integer id) {
        dvdService.deleteById(id);
        return "redirect:/dvds";
    }

    @GetMapping("/buscar")
    public String buscarPorCriterio(@RequestParam(required = false) String genero,
                                    @RequestParam(required = false) String director,
                                    @RequestParam(required = false) String autor,
                                    Model model) {
        if (genero != null && !genero.isEmpty()) {
            model.addAttribute("dvds", dvdService.findByGenero(genero));
        } else if (director != null && !director.isEmpty()) {
            model.addAttribute("dvds", dvdService.findByDirector(director));
        } else if (autor != null && !autor.isEmpty()) {
            model.addAttribute("dvds", dvdService.findByAutor(autor));
        } else {
            model.addAttribute("dvds", dvdService.findAll());
        }
        return "dvds/lista";
    }
}