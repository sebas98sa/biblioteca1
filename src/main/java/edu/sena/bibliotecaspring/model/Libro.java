package edu.sena.bibliotecaspring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro extends ElementoBiblioteca {

    private String isbn;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    private String genero;
    private String editorial;

    // Constructor vacío requerido por JPA
    public Libro() {
        super();
        this.setTipo("LIBRO");
    }

    // Constructor para crear nuevos libros
    public Libro(String titulo, String autor, Integer anoPublicacion,
                 String isbn, Integer numeroPaginas, String genero, String editorial) {
        super(titulo, autor, anoPublicacion, "LIBRO");
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    public static int getDuracion() {
        return 0; // Este método no es relevante para la clase Libro, pero se deja para cumplir con la interfaz ElementoBiblioteca
    }

    public static Object builder() {
        return new Libro(); // Método para crear un nuevo objeto Libro usando el patrón Builder
    }

    // Getters y setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return super.toString() + " - ISBN: " + isbn + " - Editorial: " + editorial;
    }

    public static void setDuracion(int i) {

    }
}