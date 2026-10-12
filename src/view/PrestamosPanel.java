package view;

import controller.PrestamoController;
import dao.EstudianteDAO;
import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Estudiante;
import model.Libro;
import model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PrestamosPanel extends JPanel {
    private JComboBox<Estudiante> comboEstudiantes;
    private JComboBox<Libro> comboLibros;
    private JButton btnPrestar, btnDevolver, btnRefrescar;
    private JTable tablaPrestamos;
    private DefaultTableModel modelTabla;

    private PrestamoController controller;
    private EstudianteDAO estudianteDAO;
    private LibroDAO libroDAO;
    private PrestamoDAO prestamoDAO;

    public PrestamosPanel() {
        this.controller = new PrestamoController();
        this.estudianteDAO = new EstudianteDAO();
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();

        setLayout(new BorderLayout(10, 10));
        initUI();
        cargarCombos();
        cargarPrestamos();
    }

    private void initUI() {
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Nuevo Préstamo (Ejecución Asíncrona)"));

        comboEstudiantes = new JComboBox<>();
        comboLibros = new JComboBox<>();

        panelForm.add(new JLabel("Estudiante:")); panelForm.add(comboEstudiantes);
        panelForm.add(new JLabel("Libro Disponible:")); panelForm.add(comboLibros);

        btnPrestar = new JButton("Solicitar Préstamo");
        btnDevolver = new JButton("Marcar Devolución");
        btnRefrescar = new JButton("Refrescar Tablas");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnRefrescar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        modelTabla = new DefaultTableModel(new String[]{"ID Préstamo", "ID Estudiante", "ID Libro", "F. Préstamo", "F. Devolución", "Estado"}, 0);
        tablaPrestamos = new JTable(modelTabla);
        add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);

        btnPrestar.addActionListener(e -> solicitarPrestamo());
        btnDevolver.addActionListener(e -> registrarDevolucion());
        btnRefrescar.addActionListener(e -> {
            cargarCombos();
            cargarPrestamos();
        });
    }

    private void cargarCombos() {
        comboEstudiantes.removeAllItems();
        for (Estudiante est : estudianteDAO.obtenerTodos()) {
            comboEstudiantes.addItem(est);
        }

        comboLibros.removeAllItems();
        for (Libro lib : libroDAO.obtenerTodos()) {
            if (lib.getStock() > 0) {
                comboLibros.addItem(lib);
            }
        }
    }

    public void cargarPrestamos() {
        modelTabla.setRowCount(0);
        List<Prestamo> lista = prestamoDAO.obtenerTodos();
        for (Prestamo p : lista) {
            modelTabla.addRow(new Object[]{
                    p.getId(), p.getIdEstudiante(), p.getIdLibro(),
                    p.getFechaPrestamo(), p.getFechaDevolucion(),
                    p.isDevuelto() ? "Devuelto" : "PENDIENTE"
            });
        }
    }

    private void solicitarPrestamo() {
        Estudiante est = (Estudiante) comboEstudiantes.getSelectedItem();
        Libro lib = (Libro) comboLibros.getSelectedItem();

        if (est == null || lib == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante y un libro.");
            return;
        }


        controller.realizarPrestamoAsincrono(est.getId(), lib.getId(), () -> {
            cargarCombos();
            cargarPrestamos();
        });
    }

    private void registrarDevolucion() {
        int fila = tablaPrestamos.getSelectedRow();
        if (fila >= 0) {
            int idPrestamo = (int) modelTabla.getValueAt(fila, 0);
            int idLibro = (int) modelTabla.getValueAt(fila, 2);
            String estado = (String) modelTabla.getValueAt(fila, 5);

            if ("Devuelto".equalsIgnoreCase(estado)) {
                JOptionPane.showMessageDialog(this, "Este préstamo ya ha sido devuelto.");
                return;
            }

            if (controller.realizarDevolucion(idPrestamo, idLibro)) {
                JOptionPane.showMessageDialog(this, "Devolución registrada correctamente.");
                cargarCombos();
                cargarPrestamos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un préstamo de la tabla.");
        }
    }
}