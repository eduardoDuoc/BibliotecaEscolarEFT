package dao;

import model.Estudiante;

import java.util.List;

public interface EstudianteDAO {

    boolean create(Estudiante estudiante);

    List<Estudiante> readAll();

    boolean update(Estudiante estudiante);

    boolean delete(int id);

    boolean createWithUser(Estudiante estudiante, String contrasena);
}
