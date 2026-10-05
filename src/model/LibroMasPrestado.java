package model;

public class LibroMasPrestado {

    private String titulo;
    private String isbn;
    private int cantidadPrestamos;

    public LibroMasPrestado() {
    }

    public LibroMasPrestado(
            String titulo,
            String isbn,
            int cantidadPrestamos) {

        this.titulo = titulo;
        this.isbn = isbn;
        this.cantidadPrestamos = cantidadPrestamos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getCantidadPrestamos() {
        return cantidadPrestamos;
    }

    public void setCantidadPrestamos(int cantidadPrestamos) {
        this.cantidadPrestamos = cantidadPrestamos;
    }
}