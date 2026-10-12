package controller;

import dao.LibroDAO;
import dao.PrestamoDAO;
import javax.swing.JOptionPane;

/**
 *
 */
public class PrestamoController {
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;

    public PrestamoController() {
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
    }

    /**

     */
    public void realizarPrestamoAsincrono(int idEstudiante, int idLibro, Runnable callbackRefresco) {
        PrestamoWorker worker = new PrestamoWorker(idEstudiante, idLibro, callbackRefresco);
        worker.execute();
    }

    /**

     */
    public boolean realizarDevolucion(int idPrestamo, int idLibro) {
        boolean devolucionRegistrada = prestamoDAO.registrarDevolucion(idPrestamo);
        if (devolucionRegistrada) {
            return libroDAO.devolverLibroSincronizado(idLibro);
        }
        return false;
    }
}