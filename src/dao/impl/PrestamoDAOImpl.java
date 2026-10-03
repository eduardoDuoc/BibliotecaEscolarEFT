package dao.impl;

import dao.PrestamoDAO;
import model.Prestamo;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO {

    @Override
    public boolean create(Prestamo prestamo) {

        String sql =
                "INSERT INTO prestamos " +
                        "(id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, prestamo.getIdEstudiante());
            ps.setInt(2, prestamo.getIdLibro());

            ps.setDate(
                    3,
                    Date.valueOf(prestamo.getFechaPrestamo())
            );

            ps.setDate(
                    4,
                    Date.valueOf(prestamo.getFechaDevolucion())
            );

            ps.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear préstamo: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<Prestamo> readAll() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM prestamos";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Prestamo prestamo = new Prestamo();

                prestamo.setId(
                        rs.getInt("id")
                );

                prestamo.setIdEstudiante(
                        rs.getInt("id_estudiante")
                );

                prestamo.setIdLibro(
                        rs.getInt("id_libro")
                );

                prestamo.setFechaPrestamo(
                        rs.getDate("fecha_prestamo").toLocalDate()
                );

                prestamo.setFechaDevolucion(
                        rs.getDate("fecha_devolucion").toLocalDate()
                );

                prestamo.setDevuelto(
                        rs.getBoolean("devuelto")
                );

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar préstamos: " + e.getMessage()
            );
        }

        return prestamos;
    }

    @Override
    public boolean update(Prestamo prestamo) {

        String sql =
                "UPDATE prestamos SET " +
                        "id_estudiante = ?, " +
                        "id_libro = ?, " +
                        "fecha_prestamo = ?, " +
                        "fecha_devolucion = ?, " +
                        "devuelto = ? " +
                        "WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(
                    1,
                    prestamo.getIdEstudiante()
            );

            ps.setInt(
                    2,
                    prestamo.getIdLibro()
            );

            ps.setDate(
                    3,
                    Date.valueOf(prestamo.getFechaPrestamo())
            );

            ps.setDate(
                    4,
                    Date.valueOf(prestamo.getFechaDevolucion())
            );

            ps.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            ps.setInt(
                    6,
                    prestamo.getId()
            );

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar préstamo: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM prestamos WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar préstamo: " + e.getMessage()
            );

            return false;
        }
    }
}