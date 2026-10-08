package dao;

import model.DetallePrestamo;
import model.Prestamo;
import model.LibroMasPrestado;

import java.util.List;

public interface PrestamoDAO {

    boolean create(Prestamo prestamo);

    List<Prestamo> readAll();

    boolean update(Prestamo prestamo);

    boolean delete(int id);

    List<DetallePrestamo> listarDetallePrestamos();

    List<LibroMasPrestado> listarLibrosMasPrestados();

    boolean registrarPrestamoTransaccional(Prestamo prestamo);

    boolean registrarDevolucionTransaccional(int idPrestamo);
}