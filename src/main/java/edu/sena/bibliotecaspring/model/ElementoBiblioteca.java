package edu.sena.bibliotecaspring.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "elementobiblioteca")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementoBiblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Cambiado de Long a Integer para coincidir con la BD

    @Column(length = 200)
    private String titulo;

    @Column(length = 100)
    private String autor;

    @Column(name = "ano_publicacion")
    private Integer anoPublicacion;  // Cambiado para coincidir con la columna en la BD

    @Column(length = 20)
    private String tipo;

    // Constructor vacío requerido por JPA
    public ElementoBiblioteca() {
    }

    // Constructor para nuevos elementos
    public ElementoBiblioteca(String titulo, String autor, Integer anoPublicacion, String tipo) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.tipo = tipo;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(Integer anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Métodos para compatibilidad con plantillas que usan fechaPublicacion
    // Estos métodos convierten entre LocalDate y el entero anoPublicacion

    @Transient // No se mapea a una columna en la BD
    public LocalDate getFechaPublicacion() {
        return (anoPublicacion != null) ? LocalDate.of(anoPublicacion, 1, 1) : null;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.anoPublicacion = (fechaPublicacion != null) ? fechaPublicacion.getYear() : null;
    }

    @Override
    public String toString() {
        return titulo + " (" + anoPublicacion + ") - " + autor;
    }
}