package edu.sena.bibliotecaspring.controller;

import edu.sena.bibliotecaspring.model.Revista;
import edu.sena.bibliotecaspring.service.RevistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/revistas")
public class RevistaController {

    @Autowired
    private RevistaService revistaService;

    @GetMapping
    public String listarRevistas(Model model) {
        model.addAttribute("revistas", revistaService.findAll());
        return "revistas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        // Inicializar una nueva revista con valores por defecto
        Revista revista = new Revista();
        revista.setAnoPublicacion(LocalDate.now().getYear());
        revista.setCategoria("General");
        revista.setNumero(1); // Número predeterminado

        model.addAttribute("revista", revista);
        return "revistas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarRevista(@ModelAttribute Revista revista) {
        // Validación adicional para asegurar que categoría nunca sea nula
        if (revista.getCategoria() == null || revista.getCategoria().trim().isEmpty()) {
            revista.setCategoria("General");
        }

        try {
            revistaService.save(revista);
            return "redirect:/revistas";
        } catch (Exception e) {
            // Registrar el error
            System.err.println("Error al guardar revista: " + e.getMessage());
            e.printStackTrace();

            // Redirigir a una página de error
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("revista", revistaService.findById(id));
        return "revistas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRevista(@PathVariable Integer id) {
        revistaService.deleteById(id);
        return "redirect:/revistas";
    }

    @GetMapping("/buscar")
    public String buscarPorCriterio(@RequestParam(required = false) String categoria,
                                    @RequestParam(required = false) String editorial,
                                    @RequestParam(required = false) String autor,
                                    Model model) {
        if (categoria != null && !categoria.isEmpty()) {
            model.addAttribute("revistas", revistaService.findByCategoria(categoria));
        } else if (editorial != null && !editorial.isEmpty()) {
            model.addAttribute("revistas", revistaService.findByEditorial(editorial));
        } else if (autor != null && !autor.isEmpty()) {
            model.addAttribute("revistas", revistaService.findByAutor(autor));
        } else {
            model.addAttribute("revistas", revistaService.findAll());
        }
        return "revistas/lista";
    }
}