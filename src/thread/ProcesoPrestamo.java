package thread;

import controller.PrestamoController;

import javax.swing.*;
import java.util.function.Consumer;

public class ProcesoPrestamo implements Runnable {

    private final PrestamoController prestamoController;
    private final int idEstudiante;
    private final int idLibro;
    private final Consumer<Boolean> resultado;

    public ProcesoPrestamo(
            PrestamoController prestamoController,
            int idEstudiante,
            int idLibro,
            Consumer<Boolean> resultado) {

        this.prestamoController = prestamoController;
        this.idEstudiante = idEstudiante;
        this.idLibro = idLibro;
        this.resultado = resultado;
    }

    @Override
    public void run() {

        boolean correcto =
                prestamoController.realizarPrestamo(
                        idEstudiante,
                        idLibro
                );

        SwingUtilities.invokeLater(
                () -> resultado.accept(correcto)
        );
    }
}