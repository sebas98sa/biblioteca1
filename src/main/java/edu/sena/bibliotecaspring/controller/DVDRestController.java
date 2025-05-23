package edu.sena.bibliotecaspring.controller;

import edu.sena.bibliotecaspring.model.DVD;
import edu.sena.bibliotecaspring.service.DVDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dvds")
@CrossOrigin(origins = "*") // Permitir CORS para consumo desde diferentes dominios
public class DVDRestController {

    @Autowired
    private DVDService dvdService;

    /**
     * Obtener todos los DVDs
     * GET /api/dvds
     */
    @GetMapping
    public ResponseEntity<List<DVD>> listarDVDs() {
        try {
            List<DVD> dvds = dvdService.findAll();
            return ResponseEntity.ok(dvds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener un DVD por ID
     * GET /api/dvds/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DVD> obtenerDVDPorId(@PathVariable Integer id) {
        try {
            DVD dvd = dvdService.findById(id);
            if (dvd != null) {
                return ResponseEntity.ok(dvd);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear un nuevo DVD
     * POST /api/dvds
     */
    @PostMapping
    public ResponseEntity<DVD> crearDVD(@RequestBody DVD dvd) {
        try {
            // Validación y valores por defecto
            if (dvd.getGenero() == null || dvd.getGenero().trim().isEmpty()) {
                dvd.setGenero("Sin clasificar");
            }

            if (dvd.getAnoPublicacion() == null || dvd.getAnoPublicacion() == 0) {
                dvd.setAnoPublicacion(LocalDate.now().getYear());
            }

            if (dvd.getDuracion()  <= 0) {
                dvd.setDuracion(0);
            }

            DVD dvdGuardado = dvdService.save(dvd);
            return ResponseEntity.status(HttpStatus.CREATED).body(dvdGuardado);
        } catch (Exception e) {
            System.err.println("Error al guardar DVD: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualizar un DVD existente
     * PUT /api/dvds/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<DVD> actualizarDVD(@PathVariable Integer id, @RequestBody DVD dvd) {
        try {
            DVD dvdExistente = dvdService.findById(id);
            if (dvdExistente == null) {
                return ResponseEntity.notFound().build();
            }

            // Establecer el ID para la actualización
            dvd.setId(id);

            // Validación y valores por defecto
            if (dvd.getGenero() == null || dvd.getGenero().trim().isEmpty()) {
                dvd.setGenero("Sin clasificar");
            }

            DVD dvdActualizado = dvdService.save(dvd);
            return ResponseEntity.ok(dvdActualizado);
        } catch (Exception e) {
            System.err.println("Error al actualizar DVD: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Eliminar un DVD
     * DELETE /api/dvds/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDVD(@PathVariable Integer id) {
        try {
            DVD dvdExistente = dvdService.findById(id);
            if (dvdExistente == null) {
                return ResponseEntity.notFound().build();
            }

            dvdService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("Error al eliminar DVD: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar DVDs por género
     * GET /api/dvds/buscar/genero?genero={genero}
     */
    @GetMapping("/buscar/genero")
    public ResponseEntity<List<DVD>> buscarPorGenero(@RequestParam String genero) {
        try {
            if (genero == null || genero.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<DVD> dvds = dvdService.findByGenero(genero);
            return ResponseEntity.ok(dvds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar DVDs por director
     * GET /api/dvds/buscar/director?director={director}
     */
    @GetMapping("/buscar/director")
    public ResponseEntity<List<DVD>> buscarPorDirector(@RequestParam String director) {
        try {
            if (director == null || director.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<DVD> dvds = dvdService.findByDirector(director);
            return ResponseEntity.ok(dvds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar DVDs por autor
     * GET /api/dvds/buscar/autor?autor={autor}
     */
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<DVD>> buscarPorAutor(@RequestParam String autor) {
        try {
            if (autor == null || autor.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<DVD> dvds = dvdService.findByAutor(autor);
            return ResponseEntity.ok(dvds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Búsqueda general por múltiples criterios
     * GET /api/dvds/buscar?genero={genero}&director={director}&autor={autor}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<DVD>> buscarPorCriterios(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String autor) {
        try {
            List<DVD> dvds;

            if (genero != null && !genero.isEmpty()) {
                dvds = dvdService.findByGenero(genero);
            } else if (director != null && !director.isEmpty()) {
                dvds = dvdService.findByDirector(director);
            } else if (autor != null && !autor.isEmpty()) {
                dvds = dvdService.findByAutor(autor);
            } else {
                dvds = dvdService.findAll();
            }

            return ResponseEntity.ok(dvds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint para obtener template de DVD con valores por defecto
     * GET /api/dvds/template
     */
    @GetMapping("/template")
    public ResponseEntity<DVD> obtenerTemplateDVD() {
        DVD dvdTemplate = new DVD();
        dvdTemplate.setAnoPublicacion(LocalDate.now().getYear());
        dvdTemplate.setGenero("Sin clasificar");
        dvdTemplate.setDuracion(0);

        return ResponseEntity.ok(dvdTemplate);
    }
}