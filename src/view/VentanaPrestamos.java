package view;

import controller.PrestamoController;
import model.DetallePrestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaPrestamos extends JFrame {

    private JPanel panel1;
    private JTable table1Prestamos;
    private JButton salirButton;

    private PrestamoController prestamoController;

    public VentanaPrestamos() {

        setContentPane(panel1);
        setTitle("Registro de Préstamos");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        prestamoController = new PrestamoController();

        cargarPrestamos();

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
}