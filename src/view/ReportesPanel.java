package view;

import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Libro;
import model.Prestamo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportesPanel extends JPanel {
    private JTextArea txtReporte;
    private JButton btnGenerarReporte;
    private LibroDAO libroDAO;
    private PrestamoDAO prestamoDAO;

    public ReportesPanel() {
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();

        setLayout(new BorderLayout(10, 10));
        initUI();
    }

    private void initUI() {
        btnGenerarReporte = new JButton("Generar Resumen de Préstamos e Inventario");
        add(btnGenerarReporte, BorderLayout.NORTH);

        txtReporte = new JTextArea();
        txtReporte.setEditable(false);
        txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtReporte), BorderLayout.CENTER);

        btnGenerarReporte.addActionListener(e -> generarReporte());
    }

    private void generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== INFORME GENERAL DE LA BIBLIOTECA ===\n\n");

        List<Libro> libros = libroDAO.obtenerTodos();
        sb.append("--- INVENTARIO DE LIBROS ---\n");
        for (Libro l : libros) {
            sb.append("Libro: ").append(l.getTitulo())
                    .append(" | Stock Disponible: ").append(l.getStock()).append("\n");
        }

        List<Prestamo> prestamos = prestamoDAO.obtenerTodos();
        long vigentes = prestamos.stream().filter(p -> !p.isDevuelto()).count();
        long devueltos = prestamos.stream().filter(Prestamo::isDevuelto).count();

        sb.append("\n--- ESTADÍSTICAS DE PRÉSTAMOS ---\n");
        sb.append("Total de Préstamos Registrados: ").append(prestamos.size()).append("\n");
        sb.append("Préstamos Actualmente Vigentes: ").append(vigentes).append("\n");
        sb.append("Préstamos Devueltos: ").append(devueltos).append("\n");

        txtReporte.setText(sb.toString());
    }
}