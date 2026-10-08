
package view;

import controller.EstudianteController;
import model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaGestionEstudiantes extends JFrame {

    private JPanel panel1;
    private JTable table1RegistroEstudiantes;
    private JButton registrarButton;
    private JButton salirButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;
    private JButton limpiarButton;

    private final EstudianteController estudianteController;

    public VentanaGestionEstudiantes() {

        setContentPane(panel1);
        setTitle("Biblioteca Escolar - Gestión de Estudiantes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        estudianteController = new EstudianteController();

        cargarEstudiantes();

        registrarButton.addActionListener(e -> registrarEstudiante());

        editarButton.addActionListener(e -> editarEstudiante());

        eliminarButton.addActionListener(e -> eliminarEstudiante());

        salirButton.addActionListener(e -> dispose());

        limpiarButton.addActionListener(e -> limpiarCampos());

        table1RegistroEstudiantes.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarEstudianteSeleccionado();
                    }
                });
    }

    // LISTAR ESTUDIANTES
    private void cargarEstudiantes() {

        String[] columnas = {
                "ID", "Nombre", "RUT", "Curso", "Correo"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        for (Estudiante estudiante :
                estudianteController.listarEstudiantes()) {

            Object[] fila = {
                    estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getRut(),
                    estudiante.getCurso(),
                    estudiante.getCorreo()
            };

            modelo.addRow(fila);
        }

        table1RegistroEstudiantes.setModel(modelo);
    }

    // VALIDAR CAMPOS
    private boolean validarCampos() {

        if (txtNombre.getText().trim().isEmpty()
                || txtRut.getText().trim().isEmpty()
                || txtCurso.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return false;
        }

        return true;
    }

    // REGISTRAR ESTUDIANTE
    private void registrarEstudiante() {

        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (estudianteController.buscarPorRut(rut) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ya existe un estudiante con ese RUT."
            );

            return;
        }

        Estudiante estudiante =
                new Estudiante(nombre, rut, curso, correo);

        JPasswordField campoClave = new JPasswordField(16);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                campoClave,
                "Contraseña de acceso del estudiante",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String contrasena =
                new String(campoClave.getPassword());

        if (contrasena.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "La contraseña debe tener al menos 6 caracteres."
            );

            return;
        }

        boolean resultado =
                estudianteController.crearEstudianteConAcceso(
                        estudiante, contrasena
                );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante registrado correctamente."
            );

            limpiarCampos();
            cargarEstudiantes();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el estudiante."
            );
        }
    }

    // CARGAR DATOS DE LA FILA SELECCIONADA
    private void cargarEstudianteSeleccionado() {

        int fila = table1RegistroEstudiantes.getSelectedRow();

        if (fila == -1) {
            return;
        }

        int filaModelo =
                table1RegistroEstudiantes.convertRowIndexToModel(fila);

        DefaultTableModel modelo =
                (DefaultTableModel) table1RegistroEstudiantes.getModel();

        txtNombre.setText(
                modelo.getValueAt(filaModelo, 1).toString()
        );

        txtRut.setText(
                modelo.getValueAt(filaModelo, 2).toString()
        );

        txtCurso.setText(
                modelo.getValueAt(filaModelo, 3).toString()
        );

        txtCorreo.setText(
                modelo.getValueAt(filaModelo, 4).toString()
        );
    }

    // EDITAR ESTUDIANTE
    private void editarEstudiante() {

        int fila = table1RegistroEstudiantes.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        if (!validarCampos()) {
            return;
        }

        int filaModelo =
                table1RegistroEstudiantes.convertRowIndexToModel(fila);

        int id = Integer.parseInt(
                table1RegistroEstudiantes.getModel()
                        .getValueAt(filaModelo, 0).toString()
        );

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        Estudiante existente =
                estudianteController.buscarPorRut(rut);

        if (existente != null && existente.getId() != id) {

            JOptionPane.showMessageDialog(
                    this,
                    "El RUT pertenece a otro estudiante."
            );

            return;
        }

        Estudiante estudiante =
                new Estudiante(id, nombre, rut, curso, correo);

        boolean resultado =
                estudianteController.actualizarEstudiante(estudiante);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante actualizado correctamente."
            );

            limpiarCampos();
            cargarEstudiantes();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el estudiante."
            );
        }
    }

    // ELIMINAR ESTUDIANTE
    private void eliminarEstudiante() {

        int fila = table1RegistroEstudiantes.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante."
            );

            return;
        }

        int filaModelo =
                table1RegistroEstudiantes.convertRowIndexToModel(fila);

        int id = Integer.parseInt(
                table1RegistroEstudiantes.getModel()
                        .getValueAt(filaModelo, 0).toString()
        );

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este estudiante?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado =
                estudianteController.eliminarEstudiante(id);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante eliminado correctamente."
            );

            limpiarCampos();
            cargarEstudiantes();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el estudiante. " +
                            "Puede tener préstamos asociados."
            );
        }
    }

    // LIMPIAR CAMPOS
    private void limpiarCampos() {

        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");

        table1RegistroEstudiantes.clearSelection();
    }
}
