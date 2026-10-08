package controller;

import dao.EstudianteDAO;
import dao.impl.EstudianteDAOImpl;
import model.Estudiante;

import java.util.List;

public class EstudianteController {

    private final EstudianteDAO estudianteDAO;

    public EstudianteController() {
        this.estudianteDAO = new EstudianteDAOImpl();
    }

    public boolean crearEstudiante(Estudiante estudiante) {
        return estudianteDAO.create(estudiante);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.readAll();
    }

    public boolean actualizarEstudiante(Estudiante estudiante) {
        return estudianteDAO.update(estudiante);
    }

    public boolean eliminarEstudiante(int id) {
        return estudianteDAO.delete(id);
    }

    public Estudiante buscarPorRut(String rut) {

        for (Estudiante estudiante : estudianteDAO.readAll()) {

            if (estudiante.getRut().equalsIgnoreCase(rut)) {
                return estudiante;
            }
        }

        return null;
    }

    public boolean crearEstudianteConAcceso(
            Estudiante estudiante, String contrasena) {

        return estudianteDAO.createWithUser(estudiante, contrasena);
    }
}