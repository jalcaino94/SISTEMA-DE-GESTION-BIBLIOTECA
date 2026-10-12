package view;

import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Usuario usuarioLogueado;

    public MainFrame(Usuario usuario) {
        this.usuarioLogueado = usuario;
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión de Biblioteca Escolar - " + usuarioLogueado.getNombre() + " (" + usuarioLogueado.getRol().toUpperCase() + ")");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Módulos
        LibrosPanel librosPanel = new LibrosPanel(usuarioLogueado);
        EstudiantesPanel estudiantesPanel = new EstudiantesPanel();
        PrestamosPanel prestamosPanel = new PrestamosPanel();
        ReportesPanel reportesPanel = new ReportesPanel();

        tabbedPane.addTab("Libros e Inventario", librosPanel);


        if (usuarioLogueado.getRol().equalsIgnoreCase("bibliotecario")) {
            tabbedPane.addTab("Gestión de Estudiantes", estudiantesPanel);
        }

        tabbedPane.addTab("Préstamos y Devoluciones", prestamosPanel);
        tabbedPane.addTab("Reportes e Informes", reportesPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Barra de estado
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusBar.add(new JLabel("Usuario: " + usuarioLogueado.getNombre() + " | Rol: " + usuarioLogueado.getRol()));
        add(statusBar, BorderLayout.SOUTH);
    }
}