package dao;

import model.Categoria;

import java.util.List;

public interface CategoriaDAO {

    boolean create(Categoria categoria);

    List<Categoria> readAll();

    boolean update(Categoria categoria);

    boolean delete(int id);
}
