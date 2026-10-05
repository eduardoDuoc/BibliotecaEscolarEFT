package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;

public class VentanaAutenticacion extends JFrame {

    private JPanel panel1;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton aceptarButton;
    private JButton cancelarButton;

    private UsuarioController usuarioController;

    public VentanaAutenticacion() {

        setContentPane(panel1);
        setTitle("Biblioteca Escolar");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        usuarioController = new UsuarioController();

        aceptarButton.addActionListener(e -> iniciarSesion());

        cancelarButton.addActionListener(e -> System.exit(0));
    }

    private void iniciarSesion() {

        String rut = textField1.getText().trim();

        String contrasena =
                new String(passwordField1.getPassword()).trim();

        if (rut.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar RUT y contraseña"
            );

            return;
        }

        Usuario usuario =
                usuarioController.iniciarSesion(rut, contrasena);

        if (usuario == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "RUT o contraseña incorrectos"
            );

            return;
        }

        if (usuario.getRol().equalsIgnoreCase("bibliotecario")) {

            VentanaAdmin ventanaAdmin =
                    new VentanaAdmin();

            ventanaAdmin.setVisible(true);

        } else if (usuario.getRol().equalsIgnoreCase("estudiante")) {

            VentanaEstudiante ventanaEstudiante =
                    new VentanaEstudiante();

            ventanaEstudiante.setVisible(true);
        }

        dispose();
    }
}