package dao.impl;

import dao.UsuarioDAO;
import model.Usuario;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public boolean create(Usuario usuario) {

        String sql =
                "INSERT INTO usuarios " +
                        "(nombre, rut, correo, contraseña, rol) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getRut());
                ps.setString(3, usuario.getCorreo());
                ps.setString(4, usuario.getContrasena());
                ps.setString(5, usuario.getRol());

                int filasAfectadas = ps.executeUpdate();

                return filasAfectadas > 0;

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear usuario: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<Usuario> readAll() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRut(rs.getString("rut"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContrasena(rs.getString("contraseña"));
                    usuario.setRol(rs.getString("rol"));

                    usuarios.add(usuario);
                }

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar usuarios: " + e.getMessage()
            );
        }

        return usuarios;
    }

    @Override
    public boolean update(Usuario usuario) {

        String sql =
                "UPDATE usuarios SET " +
                        "nombre = ?, " +
                        "rut = ?, " +
                        "correo = ?, " +
                        "contraseña = ?, " +
                        "rol = ? " +
                        "WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getRut());
                ps.setString(3, usuario.getCorreo());
                ps.setString(4, usuario.getContrasena());
                ps.setString(5, usuario.getRol());
                ps.setInt(6, usuario.getId());

                int filasAfectadas = ps.executeUpdate();

                return filasAfectadas > 0;

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM usuarios WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int filasAfectadas = ps.executeUpdate();

                return filasAfectadas > 0;

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario: " + e.getMessage()
            );

            return false;
        }
    }
}