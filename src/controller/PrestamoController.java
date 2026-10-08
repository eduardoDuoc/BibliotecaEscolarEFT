package controller;

import dao.PrestamoDAO;
import dao.impl.PrestamoDAOImpl;
import model.DetallePrestamo;
import model.Prestamo;
import model.LibroMasPrestado;
import dao.LibroDAO;
import dao.impl.LibroDAOImpl;
import model.Libro;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class PrestamoController {

    private final LibroDAO libroDAO;

    private final PrestamoDAO prestamoDAO;

    public PrestamoController() {

        this.prestamoDAO = new PrestamoDAOImpl();
        this.libroDAO = new LibroDAOImpl();
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

    public Prestamo buscarPrestamoActivo(int idEstudiante, int idLibro) {

        for (Prestamo prestamo : prestamoDAO.readAll()) {

            if (prestamo.getIdEstudiante() == idEstudiante
                    && prestamo.getIdLibro() == idLibro
                    && !prestamo.isDevuelto()) {

                return prestamo;
            }
        }

        return null;
    }

    public List<DetallePrestamo> listarDetallePrestamos() {
        return prestamoDAO.listarDetallePrestamos();
    }


    public synchronized boolean realizarPrestamo(
            int idEstudiante,
            int idLibro) {

        // Crear el préstamo con sus fechas
        Prestamo prestamo = new Prestamo(
                idEstudiante,
                idLibro,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                false
        );

        // Registrar préstamo y descontar stock
        return prestamoDAO.registrarPrestamoTransaccional(prestamo);
    }


    public List<LibroMasPrestado> listarLibrosMasPrestados() {

        return prestamoDAO.listarLibrosMasPrestados();
    }

    public List<DetallePrestamo> listarPrestamosActivos() {

        List<DetallePrestamo> activos = new ArrayList<>();

        for (DetallePrestamo detalle :
                prestamoDAO.listarDetallePrestamos()) {

            if (!detalle.isDevuelto()) {
                activos.add(detalle);
            }
        }

        return activos;
    }

    public List<DetallePrestamo> listarHistorialPorRut(String rut) {

        List<DetallePrestamo> historial = new ArrayList<>();

        for (DetallePrestamo detalle :
                prestamoDAO.listarDetallePrestamos()) {

            if (detalle.getRut().equalsIgnoreCase(rut)) {
                historial.add(detalle);
            }
        }

        return historial;
    }

    public boolean devolverPrestamo(int idPrestamo) {
        return prestamoDAO.registrarDevolucionTransaccional(idPrestamo);
    }
}