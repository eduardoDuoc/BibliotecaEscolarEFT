package dao.impl;

import dao.EstudianteDAO;
import model.Estudiante;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOImpl implements EstudianteDAO {

    @Override
    public boolean create(Estudiante estudiante) {

        String sql =
                "INSERT INTO estudiantes " +
                        "(nombre, rut, curso, correo) " +
                        "VALUES (?, ?, ?, ?)";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, estudiante.getNombre());
                ps.setString(2, estudiante.getRut());
                ps.setString(3, estudiante.getCurso());
                ps.setString(4, estudiante.getCorreo());

                int filasAfectadas = ps.executeUpdate();

                return filasAfectadas > 0;

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear estudiante: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<Estudiante> readAll() {

        List<Estudiante> estudiantes = new ArrayList<>();

        String sql = "SELECT * FROM estudiantes";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Estudiante estudiante = new Estudiante();

                    estudiante.setId(rs.getInt("id"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setRut(rs.getString("rut"));
                    estudiante.setCurso(rs.getString("curso"));
                    estudiante.setCorreo(rs.getString("correo"));

                    estudiantes.add(estudiante);
                }

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar estudiantes: " + e.getMessage()
            );
        }

        return estudiantes;
    }


    @Override
    public boolean update(Estudiante estudiante) {

        String sqlRut =
                "SELECT rut FROM estudiantes WHERE id = ? FOR UPDATE";

        String sqlExiste =
                "SELECT id FROM usuarios WHERE rut = ? AND rut <> ? LIMIT 1";

        String sqlEstudiante =
                "UPDATE estudiantes SET nombre = ?, rut = ?, " +
                        "curso = ?, correo = ? WHERE id = ?";

        String sqlUsuario =
                "UPDATE usuarios SET nombre = ?, rut = ?, correo = ? " +
                        "WHERE rut = ? AND LOWER(rol) = 'estudiante'";

        try (Connection con = DatabaseConnection.getInstance()
                .crearConexionTransaccional()) {

            con.setAutoCommit(false);

            try {
                String rutAnterior;

                // Obtener el RUT original
                try (PreparedStatement ps =
                             con.prepareStatement(sqlRut)) {

                    ps.setInt(1, estudiante.getId());

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        rutAnterior = rs.getString("rut");
                    }
                }

                // Comprobar que el nuevo RUT no pertenece a otra cuenta
                if (!rutAnterior.equalsIgnoreCase(estudiante.getRut())) {

                    try (PreparedStatement ps =
                                 con.prepareStatement(sqlExiste)) {

                        ps.setString(1, estudiante.getRut());
                        ps.setString(2, rutAnterior);

                        try (ResultSet rs = ps.executeQuery()) {

                            if (rs.next()) {
                                con.rollback();
                                return false;
                            }
                        }
                    }
                }

                // Actualizar los datos del estudiante
                try (PreparedStatement ps =
                             con.prepareStatement(sqlEstudiante)) {

                    ps.setString(1, estudiante.getNombre());
                    ps.setString(2, estudiante.getRut());
                    ps.setString(3, estudiante.getCurso());
                    ps.setString(4, estudiante.getCorreo());
                    ps.setInt(5, estudiante.getId());

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // Actualizar los datos de su cuenta de acceso
                try (PreparedStatement ps =
                             con.prepareStatement(sqlUsuario)) {

                    ps.setString(1, estudiante.getNombre());
                    ps.setString(2, estudiante.getRut());
                    ps.setString(3, estudiante.getCorreo());
                    ps.setString(4, rutAnterior);

                    ps.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException e) {

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                System.out.println(
                        "Error al actualizar estudiante y cuenta: "
                                + e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión al editar: " + e.getMessage()
            );

            return false;
        }
    }


    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM estudiantes WHERE id = ?";

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
                    "Error al eliminar estudiante: " + e.getMessage()
            );

            return false;
        }
    }


    @Override
    public boolean createWithUser(
            Estudiante estudiante, String contrasena) {

        String sqlExiste =
                "SELECT id FROM usuarios WHERE rut = ? LIMIT 1";

        String sqlEstudiante =
                "INSERT INTO estudiantes " +
                        "(nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";

        String sqlUsuario =
                "INSERT INTO usuarios " +
                        "(nombre, rut, correo, contraseña, rol) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getInstance()
                .crearConexionTransaccional()) {

            con.setAutoCommit(false);

            try {
                // Verificar que no exista la cuenta
                try (PreparedStatement verificar =
                             con.prepareStatement(sqlExiste)) {

                    verificar.setString(1, estudiante.getRut());

                    try (ResultSet rs = verificar.executeQuery()) {
                        if (rs.next()) {
                            con.rollback();
                            return false;
                        }
                    }
                }

                // Registrar estudiante
                try (PreparedStatement ps =
                             con.prepareStatement(sqlEstudiante)) {

                    ps.setString(1, estudiante.getNombre());
                    ps.setString(2, estudiante.getRut());
                    ps.setString(3, estudiante.getCurso());
                    ps.setString(4, estudiante.getCorreo());

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // Crear cuenta de acceso
                try (PreparedStatement ps =
                             con.prepareStatement(sqlUsuario)) {

                    ps.setString(1, estudiante.getNombre());
                    ps.setString(2, estudiante.getRut());
                    ps.setString(3, estudiante.getCorreo());
                    ps.setString(4, contrasena);
                    ps.setString(5, "estudiante");

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                con.commit();
                return true;

            } catch (SQLException e) {

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                System.out.println(
                        "Error al registrar estudiante y acceso: "
                                + e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión: " + e.getMessage()
            );

            return false;
        }
    }

}