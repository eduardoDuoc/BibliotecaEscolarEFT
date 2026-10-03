package dao;

import model.Prestamo;

import java.util.List;

public interface PrestamoDAO {

    boolean create(Prestamo prestamo);

    List<Prestamo> readAll();

    boolean update(Prestamo prestamo);

    boolean delete(int id);
}