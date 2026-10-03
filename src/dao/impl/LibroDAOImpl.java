package dao.impl;

import dao.LibroDAO;
import model.Libro;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public boolean create(Libro libro) {

        String sql = "INSERT INTO libros " +
                "(titulo, autor, isbn, editorial, stock, id_categoria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear libro: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<Libro> readAll() {

        List<Libro> libros = new ArrayList<>();

        String sql = "SELECT * FROM libros";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Libro libro = new Libro();

                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));
                libro.setIdCategoria(rs.getInt("id_categoria"));

                libros.add(libro);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar libros: " + e.getMessage()
            );
        }

        return libros;
    }

    @Override
    public boolean update(Libro libro) {

        String sql = "UPDATE libros SET " +
                "titulo = ?, " +
                "autor = ?, " +
                "isbn = ?, " +
                "editorial = ?, " +
                "stock = ?, " +
                "id_categoria = ? " +
                "WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            ps.setInt(7, libro.getId());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar libro: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error al eliminar libro: " + e.getMessage()
            );
        }

        return false;
    }
}