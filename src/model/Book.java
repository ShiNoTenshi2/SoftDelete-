package model;


public class Book {
    private String titulo;
    private String autor;
    private long ISBN;
    private int year;
    private boolean disponible;

    public Book(String titulo, String autor, long ISBN, int year, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.year = year;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public long getISBN() {
        return ISBN;
    }

    public int getYear() {
        return year;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

 
    public static boolean validarISBN(long ISBN) {
        return String.valueOf(ISBN).matches("[978]{3}[0-9]{10}");
    }

 
    public static boolean validarAño(int año) {
        return año >= -800 && año <= 2025;
    }

    @Override
    public String toString() {
        return "Libro: " + titulo + " | Autor: " + autor + " | ISBN: " + ISBN + " | Año: " + year + " | Disponible: " + disponible;
    }
}