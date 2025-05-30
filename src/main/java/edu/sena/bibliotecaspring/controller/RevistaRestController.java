package edu.sena.bibliotecaspring.controller;

import edu.sena.bibliotecaspring.model.Revista;
import edu.sena.bibliotecaspring.service.RevistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revistas")
@CrossOrigin(origins = "*") // Permitir CORS para desarrollo
public class RevistaRestController {

    @Autowired
    private RevistaService revistaService;

    /**
     * Obtener todas las revistas
     * GET /api/revistas
     */
    @GetMapping
    public ResponseEntity<List<Revista>> obtenerTodasLasRevistas() {
        try {
            List<Revista> revistas = revistaService.findAll();
            return ResponseEntity.ok(revistas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener una revista por ID
     * GET /api/revistas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Revista> obtenerRevistaPorId(@PathVariable Integer id) {
        try {
            Revista revista = revistaService.findById(id);
            return ResponseEntity.ok(revista);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear una nueva revista
     * POST /api/revistas
     */
    @PostMapping
    public ResponseEntity<Revista> crearRevista(@RequestBody Revista revista) {
        try {
            // Validaciones adicionales
            if (revista.getTitulo() == null || revista.getTitulo().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Validar que el número sea positivo
            if (revista.getNumero() <= 0) {
                revista.setNumero(1); // Valor por defecto
            }

            Revista revistaGuardada = revistaService.save(revista);
            return ResponseEntity.status(HttpStatus.CREATED).body(revistaGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualizar una revista existente
     * PUT /api/revistas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Revista> actualizarRevista(@PathVariable Integer id, @RequestBody Revista revista) {
        try {
            // Verificar que la revista existe
            revistaService.findById(id);

            // Asegurar que el ID coincida
            revista.setId(id);

            // Validaciones adicionales
            if (revista.getTitulo() == null || revista.getTitulo().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Validar que el número sea positivo
            if (revista.getNumero() <= 0) {
                revista.setNumero(1); // Valor por defecto
            }

            Revista revistaActualizada = revistaService.save(revista);
            return ResponseEntity.ok(revistaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Eliminar una revista
     * DELETE /api/revistas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRevista(@PathVariable Integer id) {
        try {
            // Verificar que la revista existe antes de eliminarla
            revistaService.findById(id);
            revistaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar revistas por categoría
     * GET /api/revistas/buscar/categoria?q={categoria}
     */
    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Revista>> buscarPorCategoria(@RequestParam("q") String categoria) {
        try {
            if (categoria == null || categoria.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Revista> revistas = revistaService.findByCategoria(categoria);
            return ResponseEntity.ok(revistas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar revistas por editorial
     * GET /api/revistas/buscar/editorial?q={editorial}
     */
    @GetMapping("/buscar/editorial")
    public ResponseEntity<List<Revista>> buscarPorEditorial(@RequestParam("q") String editorial) {
        try {
            if (editorial == null || editorial.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Revista> revistas = revistaService.findByEditorial(editorial);
            return ResponseEntity.ok(revistas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar revistas por autor
     * GET /api/revistas/buscar/autor?q={autor}
     */
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Revista>> buscarPorAutor(@RequestParam("q") String autor) {
        try {
            if (autor == null || autor.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            List<Revista> revistas = revistaService.findByAutor(autor);
            return ResponseEntity.ok(revistas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Búsqueda general por múltiples criterios
     * GET /api/revistas/buscar?categoria={categoria}&editorial={editorial}&autor={autor}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Revista>> buscarRevistas(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String autor) {
        try {
            List<Revista> revistas;

            if (categoria != null && !categoria.trim().isEmpty()) {
                revistas = revistaService.findByCategoria(categoria);
            } else if (editorial != null && !editorial.trim().isEmpty()) {
                revistas = revistaService.findByEditorial(editorial);
            } else if (autor != null && !autor.trim().isEmpty()) {
                revistas = revistaService.findByAutor(autor);
            } else {
                revistas = revistaService.findAll();
            }

            return ResponseEntity.ok(revistas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar revistas por número específico
     * GET /api/revistas/numero/{numero}
     */
    @GetMapping("/numero/{numero}")
    public ResponseEntity<List<Revista>> buscarPorNumero(@PathVariable int numero) {
        try {
            if (numero <= 0) {
                return ResponseEntity.badRequest().build();
            }

            // Filtrar todas las revistas por número
            List<Revista> todasRevistas = revistaService.findAll();
            List<Revista> revistasPorNumero = todasRevistas.stream()
                    .filter(revista -> revista.getNumero() == numero)
                    .toList();

            return ResponseEntity.ok(revistasPorNumero);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}