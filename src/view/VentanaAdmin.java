package view;

import controller.LibroController;
import model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAdmin extends JFrame {

    private JPanel pnlAdmin;
    private JTextField txtTitulo;
    private JTable table1;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton salirButton;
    private JButton editarButton;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;
    private JComboBox<String> cmbCategoria;
    private JButton limpiarCamposButton;
    private JButton prestamosButton;
    private JButton reportesButton;

    private LibroController libroController;

    public VentanaAdmin() {

        setContentPane(pnlAdmin);
        setTitle("Biblioteca Escolar - Administrador");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        libroController = new LibroController();
        cargarLibros();




        guardarButton.addActionListener(e -> guardarLibro());

        editarButton.addActionListener(e -> editarLibro());

        eliminarButton.addActionListener(e -> eliminarLibro());

        limpiarCamposButton.addActionListener(e -> limpiarCampos());

        reportesButton.addActionListener(e -> {

            VentanaReportes ventanaReportes =
                    new VentanaReportes();

            ventanaReportes.setVisible(true);
        });

        prestamosButton.addActionListener(e -> {

            VentanaPrestamos ventanaPrestamos =
                    new VentanaPrestamos();

            ventanaPrestamos.setVisible(true);
        });

        table1.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                cargarLibroSeleccionado();
            }
        });


        salirButton.addActionListener(e -> {

            VentanaAutenticacion ventanaAutenticacion =
                    new VentanaAutenticacion();

            ventanaAutenticacion.setVisible(true);

            dispose();
        });
    }

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

    private void guardarLibro() {

        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String isbn = txtIsbn.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String stockTexto = txtStock.getText().trim();

        if (titulo.isEmpty()
                || autor.isEmpty()
                || isbn.isEmpty()
                || editorial.isEmpty()
                || stockTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return;
        }

        try {

            int stock = Integer.parseInt(stockTexto);

            if (stock < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El stock no puede ser negativo."
                );
                return;
            }

            int idCategoria = cmbCategoria.getSelectedIndex() + 1;

            Libro libro = new Libro(
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    idCategoria
            );

            boolean resultado =
                    libroController.crearLibro(libro);

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro registrado correctamente."
                );

                limpiarCampos();
                cargarLibros();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo registrar el libro."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número entero."
            );
        }
    }

    private void limpiarCampos() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");

        cmbCategoria.setSelectedIndex(0);

        table1.clearSelection();
    }

    private void cargarLibroSeleccionado() {

        int fila = table1.getSelectedRow();

        if (fila == -1) {
            return;
        }

        txtTitulo.setText(
                table1.getValueAt(fila, 1).toString()
        );

        txtAutor.setText(
                table1.getValueAt(fila, 2).toString()
        );

        txtIsbn.setText(
                table1.getValueAt(fila, 3).toString()
        );

        txtEditorial.setText(
                table1.getValueAt(fila, 4).toString()
        );

        txtStock.setText(
                table1.getValueAt(fila, 5).toString()
        );

        int idCategoria =
                Integer.parseInt(
                        table1.getValueAt(fila, 6).toString()
                );

        cmbCategoria.setSelectedIndex(idCategoria - 1);
    }

    private void editarLibro() {

        int fila = table1.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro."
            );

            return;
        }

        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String isbn = txtIsbn.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String stockTexto = txtStock.getText().trim();

        if (titulo.isEmpty()
                || autor.isEmpty()
                || isbn.isEmpty()
                || editorial.isEmpty()
                || stockTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos."
            );

            return;
        }

        try {

            int id =
                    Integer.parseInt(
                            table1.getValueAt(fila, 0).toString()
                    );

            int stock =
                    Integer.parseInt(stockTexto);

            if (stock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El stock no puede ser negativo."
                );

                return;
            }

            int idCategoria =
                    cmbCategoria.getSelectedIndex() + 1;

            Libro libro = new Libro(
                    id,
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    idCategoria
            );

            boolean resultado =
                    libroController.actualizarLibro(libro);

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro actualizado correctamente."
                );

                limpiarCampos();
                cargarLibros();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo actualizar el libro."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número entero."
            );
        }
    }

    private void eliminarLibro() {

        int fila = table1.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un libro."
            );

            return;
        }

        int id = Integer.parseInt(
                table1.getValueAt(fila, 0).toString()
        );

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este libro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado =
                libroController.eliminarLibro(id);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro eliminado correctamente."
            );

            limpiarCampos();
            cargarLibros();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el libro."
            );
        }
    }
}