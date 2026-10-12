package controller;

import dao.LibroDAO;
import dao.PrestamoDAO;
import javax.swing.SwingWorker;
import javax.swing.JOptionPane;

/**


 */
public class PrestamoWorker extends SwingWorker<Boolean, Void> {
    private final int idEstudiante;
    private final int idLibro;
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;
    private final Runnable onComplete; // Callback para refrescar las tablas en la vista

    public PrestamoWorker(int idEstudiante, int idLibro, Runnable onComplete) {
        this.idEstudiante = idEstudiante;
        this.idLibro = idLibro;
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.onComplete = onComplete;
    }

    @Override
    protected Boolean doInBackground() throws Exception {

        Thread.sleep(500);

        // 1. Descueta
        boolean stockDescontado = libroDAO.prestarLibroSincronizado(idLibro);

        if (stockDescontado) {
            // 2. Registra
            return prestamoDAO.registrarPrestamo(idEstudiante, idLibro);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            boolean exito = get();
            if (exito) {
                JOptionPane.showMessageDialog(null,
                        "Préstamo registrado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No fue posible realizar el préstamo (Stock insuficiente).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al procesar el préstamo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Ejecutar
        if (onComplete != null) {
            onComplete.run();
        }
    }
}