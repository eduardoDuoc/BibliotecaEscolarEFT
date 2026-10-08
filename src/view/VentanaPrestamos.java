package view;

import controller.PrestamoController;
import model.DetallePrestamo;
import controller.EstudianteController;
import controller.LibroController;
import model.Estudiante;
import model.Libro;
import thread.ProcesoPrestamo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaPrestamos extends JFrame {

    private JPanel panel1;
    private JTable table1Prestamos;
    private JButton salirButton;
    private JButton devolverPrestamoButton;
    private JButton registrarPrestamoButton;

    private PrestamoController prestamoController;
    private EstudianteController estudianteController;
    private LibroController libroController;

    public VentanaPrestamos() {

        setContentPane(panel1);
        setTitle("Registro de Préstamos");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        prestamoController = new PrestamoController();
        estudianteController = new EstudianteController();
        libroController = new LibroController();

        cargarPrestamos();

        registrarPrestamoButton.addActionListener(
                e -> registrarPrestamo()
        );

        devolverPrestamoButton.addActionListener(
                e -> devolverPrestamo()
        );

        salirButton.addActionListener(e -> dispose());
    }

    private void cargarPrestamos() {

        String[] columnas = {
                "ID",
                "Estudiante",
                "RUT",
                "Curso",
                "Correo",
                "Libro",
                "ISBN",
                "Fecha Préstamo",
                "Fecha Devolución",
                "Estado"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        for (DetallePrestamo detalle :
                prestamoController.listarDetallePrestamos()) {

            String estado;


            if (detalle.isDevuelto()) {

                estado = "Entregado";

            } else if (detalle.estaAtrasado()) {

                estado = "Atrasado";

            } else {

                estado = "Pendiente";
            }


            Object[] fila = {
                    detalle.getIdPrestamo(),
                    detalle.getNombreEstudiante(),
                    detalle.getRut(),
                    detalle.getCurso(),
                    detalle.getCorreo(),
                    detalle.getTituloLibro(),
                    detalle.getIsbn(),
                    detalle.getFechaPrestamo(),
                    detalle.getFechaDevolucion(),
                    estado
            };

            modelo.addRow(fila);
        }

        table1Prestamos.setModel(modelo);
    }


    private void registrarPrestamo() {

        String rut = JOptionPane.showInputDialog(
                this, "RUT del estudiante:"
        );

        if (rut == null) return;

        rut = rut.trim();

        if (rut.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Debe ingresar un RUT."
            );
            return;
        }

        Estudiante estudiante =
                estudianteController.buscarPorRut(rut);

        if (estudiante == null) {
            JOptionPane.showMessageDialog(
                    this, "No existe un estudiante con ese RUT."
            );
            return;
        }

        // Obtener libros disponibles
        List<Libro> disponibles = new ArrayList<>();

        for (Libro libro : libroController.listarLibros()) {
            if (libro.getStock() > 0) {
                disponibles.add(libro);
            }
        }

        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "No hay libros con stock disponible."
            );
            return;
        }

        String[] opciones = new String[disponibles.size()];

        for (int i = 0; i < disponibles.size(); i++) {
            Libro libro = disponibles.get(i);

            opciones[i] = libro.getId() + " - "
                    + libro.getTitulo()
                    + " (stock: " + libro.getStock() + ")";
        }

        JComboBox<String> comboLibros =
                new JComboBox<>(opciones);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                comboLibros,
                "Seleccionar libro",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }

        int idLibro = disponibles.get(
                comboLibros.getSelectedIndex()
        ).getId();

        registrarPrestamoButton.setEnabled(false);

        // Reutilizar el hilo existente
        ProcesoPrestamo proceso = new ProcesoPrestamo(
                prestamoController,
                estudiante.getId(),
                idLibro,
                correcto -> {

                    registrarPrestamoButton.setEnabled(true);

                    JOptionPane.showMessageDialog(
                            this,
                            correcto
                                    ? "Préstamo registrado correctamente."
                                    : "No se pudo registrar el préstamo."
                    );

                    cargarPrestamos();
                }
        );

        new Thread(proceso).start();
    }

    private void devolverPrestamo() {

        int fila = table1Prestamos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this, "Seleccione un préstamo en la tabla."
            );
            return;
        }

        int filaModelo =
                table1Prestamos.convertRowIndexToModel(fila);

        int idPrestamo = Integer.parseInt(
                table1Prestamos.getModel()
                        .getValueAt(filaModelo, 0).toString()
        );

        String estado = table1Prestamos.getModel()
                .getValueAt(filaModelo, 9).toString();

        if (estado.equalsIgnoreCase("Entregado")) {
            JOptionPane.showMessageDialog(
                    this, "Ese préstamo ya fue devuelto."
            );
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Registrar la devolución del préstamo "
                        + idPrestamo + "?",
                "Confirmar devolución",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        devolverPrestamoButton.setEnabled(false);

        new Thread(() -> {

            boolean correcto =
                    prestamoController.devolverPrestamo(idPrestamo);

            SwingUtilities.invokeLater(() -> {

                devolverPrestamoButton.setEnabled(true);

                JOptionPane.showMessageDialog(
                        this,
                        correcto
                                ? "Devolución registrada correctamente."
                                : "No se pudo registrar la devolución."
                );

                cargarPrestamos();
            });

        }).start();
    }

}