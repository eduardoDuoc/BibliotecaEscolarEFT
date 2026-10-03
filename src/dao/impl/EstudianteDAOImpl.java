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

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

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

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Estudiante estudiante = new Estudiante();

                estudiante.setId(rs.getInt("id"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setRut(rs.getString("rut"));
                estudiante.setCurso(rs.getString("curso"));
                estudiante.setCorreo(rs.getString("correo"));

                estudiantes.add(estudiante);
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

        String sql =
                "UPDATE estudiantes SET " +
                        "nombre = ?, " +
                        "rut = ?, " +
                        "curso = ?, " +
                        "correo = ? " +
                        "WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());
            ps.setInt(5, estudiante.getId());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar estudiante: " + e.getMessage()
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

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar estudiante: " + e.getMessage()
            );

            return false;
        }
    }
}