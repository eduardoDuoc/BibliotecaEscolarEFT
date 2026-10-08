package view;

import controller.PrestamoController;
import model.DetallePrestamo;
import model.LibroMasPrestado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaReportes extends JFrame {

    private JPanel panel1;
    private JTable table1;
    private JButton librosMásPrestadosButton;
    private JButton salirButton;
    private JButton historialEstudianteButton;
    private JButton préstamosActivosButton;

    private final PrestamoController prestamoController;

    public VentanaReportes() {

        setContentPane(panel1);
        setTitle("Reportes");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        prestamoController = new PrestamoController();

        préstamosActivosButton.addActionListener(
                e -> mostrarPrestamosActivos()
        );

        librosMásPrestadosButton.addActionListener(
                e -> mostrarLibrosMasPrestados()
        );

        historialEstudianteButton.addActionListener(
                e -> mostrarHistorialEstudiante()
        );

        salirButton.addActionListener(
                e -> dispose()
        );
    }

    private void mostrarLibrosMasPrestados() {

        String[] columnas = {
                "Título",
                "ISBN",
                "Cantidad de Préstamos"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        for (LibroMasPrestado libro :
                prestamoController.listarLibrosMasPrestados()) {

            Object[] fila = {
                    libro.getTitulo(),
                    libro.getIsbn(),
                    libro.getCantidadPrestamos()
            };

            modelo.addRow(fila);
        }

        table1.setModel(modelo);
    }

    private void mostrarPrestamosActivos() {

        String[] columnas = {
                "Estudiante",
                "RUT",
                "Curso",
                "Libro",
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
                prestamoController.listarPrestamosActivos()) {

            Object[] fila = {
                    detalle.getNombreEstudiante(),
                    detalle.getRut(),
                    detalle.getCurso(),
                    detalle.getTituloLibro(),
                    detalle.getFechaPrestamo(),
                    detalle.getFechaDevolucion(),
                    detalle.estaAtrasado() ? "Atrasado" : "Pendiente"
            };

            modelo.addRow(fila);
        }

        table1.setModel(modelo);
    }

    private void mostrarHistorialEstudiante() {

        String rut = JOptionPane.showInputDialog(
                this,
                "Ingrese el RUT del estudiante\nEjemplo: 12345678-9"
        );

        if (rut == null) {
            return;
        }

        rut = rut.trim();

        if (rut.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un RUT."
            );

            return;
        }

        if (!rut.matches("\\d{7,8}-[0-9kK]")) {

            JOptionPane.showMessageDialog(
                    this,
                    "RUT inválido.\nDebe ingresarlo sin puntos y con guion."
            );

            return;
        }

        String[] columnas = {
                "Estudiante",
                "RUT",
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
                prestamoController.listarHistorialPorRut(rut)) {

            String estado;


            if (detalle.isDevuelto()) {

                estado = "Entregado";

            } else if (detalle.estaAtrasado()) {

                estado = "Atrasado";

            } else {

                estado = "Pendiente";
            }


            Object[] fila = {
                    detalle.getNombreEstudiante(),
                    detalle.getRut(),
                    detalle.getTituloLibro(),
                    detalle.getIsbn(),
                    detalle.getFechaPrestamo(),
                    detalle.getFechaDevolucion(),
                    estado
            };

            modelo.addRow(fila);
        }

        table1.setModel(modelo);

        if (modelo.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontraron préstamos para ese RUT."
            );
        }
    }
}