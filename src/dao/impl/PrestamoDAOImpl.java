package dao.impl;

import dao.PrestamoDAO;
import model.DetallePrestamo;
import model.Prestamo;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.LibroMasPrestado;

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

            try (PreparedStatement ps = con.prepareStatement(sql)) {

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

            }

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

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

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

            try (PreparedStatement ps = con.prepareStatement(sql)) {

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

            }

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

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int filasAfectadas = ps.executeUpdate();

                return filasAfectadas > 0;

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar préstamo: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<DetallePrestamo> listarDetallePrestamos() {

        List<DetallePrestamo> detalles = new ArrayList<>();

        String sql =
                "SELECT " +
                        "p.id AS id_prestamo, " +
                        "e.nombre AS nombre_estudiante, " +
                        "e.rut, " +
                        "e.curso, " +
                        "e.correo, " +
                        "l.titulo AS titulo_libro, " +
                        "l.isbn, " +
                        "p.fecha_prestamo, " +
                        "p.fecha_devolucion, " +
                        "p.devuelto " +
                        "FROM prestamos p " +
                        "INNER JOIN estudiantes e ON p.id_estudiante = e.id " +
                        "INNER JOIN libros l ON p.id_libro = l.id";

        try {

            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    DetallePrestamo detalle =
                            new DetallePrestamo();

                    detalle.setIdPrestamo(
                            rs.getInt("id_prestamo")
                    );

                    detalle.setNombreEstudiante(
                            rs.getString("nombre_estudiante")
                    );

                    detalle.setRut(
                            rs.getString("rut")
                    );

                    detalle.setCurso(
                            rs.getString("curso")
                    );

                    detalle.setCorreo(
                            rs.getString("correo")
                    );

                    detalle.setTituloLibro(
                            rs.getString("titulo_libro")
                    );

                    detalle.setIsbn(
                            rs.getString("isbn")
                    );

                    detalle.setFechaPrestamo(
                            rs.getDate("fecha_prestamo").toLocalDate()
                    );

                    detalle.setFechaDevolucion(
                            rs.getDate("fecha_devolucion").toLocalDate()
                    );

                    detalle.setDevuelto(
                            rs.getBoolean("devuelto")
                    );

                    detalles.add(detalle);
                }

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar detalle de préstamos: "
                            + e.getMessage()
            );
        }

        return detalles;
    }

    @Override
    public List<LibroMasPrestado> listarLibrosMasPrestados() {

        List<LibroMasPrestado> libros = new ArrayList<>();

        String sql =
                "SELECT " +
                        "l.titulo, " +
                        "l.isbn, " +
                        "COUNT(p.id) AS cantidad_prestamos " +
                        "FROM prestamos p " +
                        "INNER JOIN libros l ON p.id_libro = l.id " +
                        "GROUP BY l.id, l.titulo, l.isbn " +
                        "ORDER BY cantidad_prestamos DESC";

        try {

            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    LibroMasPrestado libro =
                            new LibroMasPrestado();

                    libro.setTitulo(
                            rs.getString("titulo")
                    );

                    libro.setIsbn(
                            rs.getString("isbn")
                    );

                    libro.setCantidadPrestamos(
                            rs.getInt("cantidad_prestamos")
                    );

                    libros.add(libro);
                }

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al generar reporte de libros más prestados: "
                            + e.getMessage()
            );
        }

        return libros;
    }


    @Override
    public boolean registrarPrestamoTransaccional(Prestamo prestamo) {

        String sqlStock =
                "UPDATE libros SET stock = stock - 1 " +
                        "WHERE id = ? AND stock > 0";

        String sqlPrestamo =
                "INSERT INTO prestamos " +
                        "(id_estudiante, id_libro, fecha_prestamo, " +
                        "fecha_devolucion, devuelto) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getInstance()
                .crearConexionTransaccional()) {

            con.setAutoCommit(false);

            try {

                // 1. Descontar stock disponible
                try (PreparedStatement psStock =
                             con.prepareStatement(sqlStock)) {

                    psStock.setInt(1, prestamo.getIdLibro());

                    int filas = psStock.executeUpdate();

                    if (filas != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // 2. Registrar el préstamo
                try (PreparedStatement psPrestamo =
                             con.prepareStatement(sqlPrestamo)) {

                    psPrestamo.setInt(
                            1, prestamo.getIdEstudiante()
                    );

                    psPrestamo.setInt(
                            2, prestamo.getIdLibro()
                    );

                    psPrestamo.setDate(
                            3, Date.valueOf(prestamo.getFechaPrestamo())
                    );

                    psPrestamo.setDate(
                            4, Date.valueOf(prestamo.getFechaDevolucion())
                    );

                    psPrestamo.setBoolean(
                            5, prestamo.isDevuelto()
                    );

                    int filas = psPrestamo.executeUpdate();

                    if (filas != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // 3. Confirmar ambas operaciones
                con.commit();
                return true;

            } catch (SQLException e) {

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                System.out.println(
                        "Error en la transacción: " + e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión transaccional: "
                            + e.getMessage()
            );

            return false;
        }
    }


    @Override
    public boolean registrarDevolucionTransaccional(int idPrestamo) {

        String sqlDevolucion =
                "UPDATE prestamos SET devuelto = 1 " +
                        "WHERE id = ? AND devuelto = 0";

        String sqlStock =
                "UPDATE libros SET stock = stock + 1 " +
                        "WHERE id = (SELECT id_libro FROM prestamos WHERE id = ?)";

        try (Connection con = DatabaseConnection.getInstance()
                .crearConexionTransaccional()) {

            con.setAutoCommit(false);

            try {
                // 1. Registrar la devolución
                try (PreparedStatement ps =
                             con.prepareStatement(sqlDevolucion)) {

                    ps.setInt(1, idPrestamo);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // 2. Recuperar una unidad de stock
                try (PreparedStatement ps =
                             con.prepareStatement(sqlStock)) {

                    ps.setInt(1, idPrestamo);

                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        return false;
                    }
                }

                // 3. Confirmar ambas operaciones
                con.commit();
                return true;

            } catch (SQLException e) {

                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                System.out.println(
                        "Error en devolución: " + e.getMessage()
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