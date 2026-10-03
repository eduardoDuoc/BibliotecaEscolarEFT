package controller;

import dao.UsuarioDAO;
import dao.impl.UsuarioDAOImpl;
import model.Usuario;

import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public boolean crearUsuario(Usuario usuario) {
        return usuarioDAO.create(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.readAll();
    }

    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.update(usuario);
    }

    public boolean eliminarUsuario(int id) {
        return usuarioDAO.delete(id);
    }

    public Usuario iniciarSesion(String rut, String contrasena) {

        for (Usuario usuario : usuarioDAO.readAll()) {

            if (usuario.getRut().equals(rut)
                    && usuario.getContrasena().equals(contrasena)) {

                return usuario;
            }
        }

        return null;
    }
}