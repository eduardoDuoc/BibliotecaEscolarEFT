package dao.impl;

import dao.CategoriaDAO;
import model.Categoria;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public boolean create(Categoria categoria) {

        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, categoria.getNombre());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al crear categoría: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public List<Categoria> readAll() {

        List<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT * FROM categorias";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Categoria categoria = new Categoria();

                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));

                categorias.add(categoria);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar categorías: " + e.getMessage()
            );
        }

        return categorias;
    }

    @Override
    public boolean update(Categoria categoria) {

        String sql =
                "UPDATE categorias SET nombre = ? WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getId());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar categoría: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM categorias WHERE id = ?";

        try {
            Connection con =
                    DatabaseConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar categoría: " + e.getMessage()
            );

            return false;
        }
    }
}