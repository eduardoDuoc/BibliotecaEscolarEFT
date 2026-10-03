package controller;

import dao.PrestamoDAO;
import dao.impl.PrestamoDAOImpl;
import model.Prestamo;

import java.util.List;

public class PrestamoController {

    private final PrestamoDAO prestamoDAO;

    public PrestamoController() {
        this.prestamoDAO = new PrestamoDAOImpl();
    }

    public boolean crearPrestamo(Prestamo prestamo) {
        return prestamoDAO.create(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoDAO.readAll();
    }

    public boolean actualizarPrestamo(Prestamo prestamo) {
        return prestamoDAO.update(prestamo);
    }

    public boolean eliminarPrestamo(int id) {
        return prestamoDAO.delete(id);
    }
}