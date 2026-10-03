package dao;

import model.Libro;

import java.util.List;

public interface LibroDAO {

    boolean create(Libro libro) ;

    List<Libro> readAll();

    boolean update(Libro libro);

    boolean delete(int id);
}
