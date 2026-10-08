
package view;

import controller.EstudianteController;
import controller.LibroController;
import controller.PrestamoController;
import model.Persona;
import model.Estudiante;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import thread.ProcesoPrestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaEstudiante extends JFrame {

    private JPanel pnlEstudiantes;
    private JButton reservarButton;
    private JButton salirButton;
    private JButton devolverButton;
    private JButton limpiarButton;
    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtCurso;
    private JTextField txtCorreo;
    private JTable table1;
    private JButton reportesButton;

    private final EstudianteController estudianteController;
    private final LibroController libroController;
    private final PrestamoController prestamoController;
    private final Estudiante estudianteAutenticado;

    public VentanaEstudiante(Usuario usuarioAutenticado) {

        setContentPane(pnlEstudiantes);
        setTitle("Biblioteca Escolar - Estudiante");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        estudianteController = new EstudianteController();
        libroController = new LibroController();
        prestamoController = new PrestamoController();


        estudianteAutenticado =
                estudianteController.buscarPorRut(
                        usuarioAutenticado.getRut()
                );

// Mostrar la identidad del usuario autenticado
        txtRut.setText(usuarioAutenticado.getRut());

        if (estudianteAutenticado != null) {

            txtNombre.setText(estudianteAutenticado.getNombre());
            txtCurso.setText(estudianteAutenticado.getCurso());
            txtCorreo.setText(estudianteAutenticado.getCorreo());

        } else {

            txtNombre.setText(usuarioAutenticado.getNombre());
            txtCorreo.setText(usuarioAutenticado.getCorreo());

            reservarButton.setEnabled(false);
            devolverButton.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "No existe una ficha de estudiante para este RUT.\n"
                            + "Solicite su registro al bibliotecario."
            );
        }

        txtNombre.setEditable(false);
        txtRut.setEditable(false);
        txtCurso.setEditable(false);
        txtCorreo.setEditable(false);


        cargarLibros();

        limpiarButton.addActionListener(e -> limpiarCampos());

        reservarButton.addActionListener(e -> reservarLibro());

        devolverButton.addActionListener(e -> devolverLibro());

        reportesButton.addActionListener(e -> {

            VentanaReportes ventanaReportes =
                    new VentanaReportes();

            ventanaReportes.setVisible(true);
        });

        salirButton.addActionListener(e -> {

            VentanaAutenticacion ventanaAutenticacion =
                    new VentanaAutenticacion();

            ventanaAutenticacion.setVisible(true);

            dispose();
        });
    }

    // MOSTRAR TODOS LOS LIBROS
    private void cargarLibros() {

        String[] columnas = {
                "ID",
                "Título",
                "Autor",
                "ISBN",
                "Editorial",
                "Stock",
                "Categoría"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        for (Libro libro : libroController.listarLibros()) {

            Object[] fila = {
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock(),
                    libro.getIdCategoria()
            };

            modelo.addRow(fila);
        }

        table1.setModel(modelo);
    }

    // VALIDAR FORMATO DEL RUT
    private boolean validarRut(String rut) {

        return rut.matches("\\d{7,8}-[0-9kK]");
    }

    // VALIDAR FORMATO DEL CORREO
    private boolean validarCorreo(String correo) {

        return correo.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    // VALIDAR CAMPOS DEL ESTUDIANTE
    private boolean validarCampos() {

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty()
                || rut.isEmpty()
                || curso.isEmpty()
                || correo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return false;
        }

        if (!validarRut(rut)) {

            JOptionPane.showMessageDialog(
                    this,
                    "RUT inválido.\nEjemplo: 12345678-9"
            );

            return false;
        }

        if (!validarCorreo(correo)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Correo electrónico inválido."
            );

            return false;
        }

        return true;
    }

    // LIMPIAR CAMPOS
    private void limpiarCampos() {
        table1.clearSelection();
    }

    // RESERVAR LIBRO UTILIZANDO UN HILO
    private void reservarLibro() {

        if (!validarCampos()) {
            return;
        }

        int fila = table1.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro."
            );

            return;
        }

        Estudiante estudiante = estudianteAutenticado;

        if (estudiante == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existe un estudiante asociado a esta sesión."
            );
            return;
        }

        final Persona personaPrestamo = estudiante;

        // OBTENER LIBRO SELECCIONADO
        int idLibro = Integer.parseInt(
                table1.getValueAt(fila, 0).toString()
        );

        int stock = Integer.parseInt(
                table1.getValueAt(fila, 5).toString()
        );

        if (stock <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay stock disponible para este libro."
            );

            return;
        }
        // DESHABILITAR BOTON DURANTE EL PROCESO
        reservarButton.setEnabled(false);

        // EJECUTAR EL PRESTAMO EN SEGUNDO PLANO
        ProcesoPrestamo proceso =
                new ProcesoPrestamo(
                        prestamoController,
                        estudiante.getId(),
                        idLibro,
                        resultado -> {

                            reservarButton.setEnabled(true);

                            if (resultado) {

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Libro reservado correctamente.\n"
                                                + personaPrestamo.obtenerDescripcion()
                                );

                            } else {

                                JOptionPane.showMessageDialog(
                                        this,
                                        "No fue posible reservar el libro."
                                );
                            }

                            cargarLibros();
                        }
                );

        Thread hilo = new Thread(proceso);

        hilo.start();
    }

    // DEVOLVER LIBRO
    private void devolverLibro() {

        if (!validarCampos()) {
            return;
        }

        int fila = table1.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro."
            );

            return;
        }

        Estudiante estudiante = estudianteAutenticado;

        if (estudiante == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "El estudiante no tiene préstamos registrados."
            );

            return;
        }

        int idLibro = Integer.parseInt(
                table1.getValueAt(fila, 0).toString()
        );

        // BUSCAR PRESTAMO PENDIENTE
        Prestamo prestamo =
                prestamoController.buscarPrestamoActivo(
                        estudiante.getId(),
                        idLibro
                );

        if (prestamo == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "El estudiante no tiene un préstamo activo de este libro."
            );

            return;
        }


        boolean devolucionCorrecta =
                prestamoController.devolverPrestamo(prestamo.getId());

        if (!devolucionCorrecta) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la devolución."
            );

            return;
        }



        JOptionPane.showMessageDialog(
                this,
                "Libro devuelto correctamente."
        );

        cargarLibros();
    }
}
