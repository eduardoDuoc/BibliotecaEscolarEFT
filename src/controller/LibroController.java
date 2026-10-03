package controller;

import dao.LibroDAO;
import dao.impl.LibroDAOImpl;
import model.Libro;

import java.util.List;

public class LibroController {

    private final LibroDAO libroDAO;

    public LibroController() {
        this.libroDAO = new LibroDAOImpl();
    }

    public boolean crearLibro(Libro libro) {
        return libroDAO.create(libro);
    }

    public List<Libro> listarLibros() {
        return libroDAO.readAll();
    }

    public boolean actualizarLibro(Libro libro) {
        return libroDAO.update(libro);
    }

    public boolean eliminarLibro(int id) {
        return libroDAO.delete(id);
    }
}