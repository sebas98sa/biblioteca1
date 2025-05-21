package edu.sena.bibliotecaspring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "revista")
public class Revista extends ElementoBiblioteca {

    private String categoria;
    private int numero;
    private String editorial;

    // Constructor vacío requerido por JPA
    public Revista() {
        super();
        this.setTipo("REVISTA");
    }

    // Constructor para crear nuevas revistas
    public Revista(String titulo, String autor, Integer anoPublicacion,
                   String categoria, int numero, String editorial) {
        super(titulo, autor, anoPublicacion, "REVISTA");
        this.categoria = categoria != null ? categoria : "General";
        this.numero = numero;
        this.editorial = editorial;
    }

    // Getters y setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria != null && !categoria.trim().isEmpty() ? categoria : "General";
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    // Método para garantizar que categoría nunca sea nula
    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (categoria == null || categoria.trim().isEmpty()) {
            categoria = "General";
        }
    }

    @Override
    public String toString() {
        return super.toString() + " - Edición: " + numero + " - " + categoria + " - Editorial: " + editorial;
    }
}