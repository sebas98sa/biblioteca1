package edu.sena.bibliotecaspring.controller;

import edu.sena.bibliotecaspring.model.Libro;
import edu.sena.bibliotecaspring.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*") // Permitir CORS para desarrollo
public class LibroRestController {

    @Autowired
    private LibroService libroService;

    /**
     * Obtener todos los libros
     * GET /api/libros
     */
    @GetMapping
    public ResponseEntity<List<Libro>> obtenerTodosLosLibros() {
        try {
            List<Libro> libros = libroService.findAll();
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener un libro por ID
     * GET /api/libros/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Integer id) {
        try {
            Libro libro = libroService.findById(id);
            return ResponseEntity.ok(libro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear un nuevo libro
     * POST /api/libros
     */
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) {
        try {
            // Validaciones adicionales
            if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Libro libroGuardado = libroService.save(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualizar un libro existente
     * PUT /api/libros/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Integer id, @RequestBody Libro libro) {
        try {
            // Verificar que el libro existe
            libroService.findById(id);

            // Asegurar que el ID coincida
            libro.setId(id);

            // Validaciones adicionales
            if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Libro libroActualizado = libroService.save(libro);
            return ResponseEntity.ok(libroActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Eliminar un libro
     * DELETE /api/libros/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Integer id) {
        try {
            // Verificar que el libro existe antes de eliminarlo
            libroService.findById(id);
            libroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar libros por título
     * GET /api/libros/buscar/titulo?q={titulo}
     */
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Libro>> buscarPorTitulo(@RequestParam("q") String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Libro> libros = libroService.findByTitulo(titulo);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar libros por autor
     * GET /api/libros/buscar/autor?q={autor}
     */
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Libro>> buscarPorAutor(@RequestParam("q") String autor) {
        try {
            if (autor == null || autor.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Libro> libros = libroService.findByAutor(autor);
            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Búsqueda general (título o autor)
     * GET /api/libros/buscar?titulo={titulo}&autor={autor}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscarLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor) {
        try {
            List<Libro> libros;

            if (titulo != null && !titulo.trim().isEmpty()) {
                libros = libroService.findByTitulo(titulo);
            } else if (autor != null && !autor.trim().isEmpty()) {
                libros = libroService.findByAutor(autor);
            } else {
                libros = libroService.findAll();
            }

            return ResponseEntity.ok(libros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}