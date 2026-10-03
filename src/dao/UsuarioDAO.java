package dao;

import model.Usuario;

import java.util.List;

public interface UsuarioDAO {

    boolean create(Usuario usuario);

    List<Usuario> readAll();

    boolean update(Usuario usuario);

    boolean delete(int id);
}