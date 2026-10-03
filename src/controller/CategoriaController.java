package controller;

import dao.CategoriaDAO;
import dao.impl.CategoriaDAOImpl;
import model.Categoria;

import java.util.List;

public class CategoriaController {

    private final CategoriaDAO categoriaDAO;

    public CategoriaController() {
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    public boolean crearCategoria(Categoria categoria) {
        return categoriaDAO.create(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.readAll();
    }

    public boolean actualizarCategoria(Categoria categoria) {
        return categoriaDAO.update(categoria);
    }

    public boolean eliminarCategoria(int id) {
        return categoriaDAO.delete(id);
    }
}