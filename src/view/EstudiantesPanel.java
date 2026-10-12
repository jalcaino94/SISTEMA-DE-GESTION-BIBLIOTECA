package view;

import dao.EstudianteDAO;
import model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudiantesPanel extends JPanel {
    private JTable tablaEstudiantes;
    private DefaultTableModel modelTabla;
    private JTextField txtNombre, txtRut, txtCurso, txtCorreo;
    private JButton btnGuardar, btnEliminar, btnRefrescar;
    private EstudianteDAO estudianteDAO;

    public EstudiantesPanel() {
        this.estudianteDAO = new EstudianteDAO();
        setLayout(new BorderLayout(10, 10));
        initUI();
        cargarEstudiantes();
    }

    private void initUI() {
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registro de Estudiante"));

        txtNombre = new JTextField();
        txtRut = new JTextField();
        txtCurso = new JTextField();
        txtCorreo = new JTextField();

        panelForm.add(new JLabel("Nombre Completo:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("RUT:")); panelForm.add(txtRut);
        panelForm.add(new JLabel("Curso:")); panelForm.add(txtCurso);
        panelForm.add(new JLabel("Correo:")); panelForm.add(txtCorreo);

        btnGuardar = new JButton("Registrar Estudiante");
        btnEliminar = new JButton("Eliminar Seleccionado");
        btnRefrescar = new JButton("Refrescar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        modelTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "RUT", "Curso", "Correo"}, 0);
        tablaEstudiantes = new JTable(modelTabla);
        add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnRefrescar.addActionListener(e -> cargarEstudiantes());
    }

    public void cargarEstudiantes() {
        modelTabla.setRowCount(0);
        List<Estudiante> lista = estudianteDAO.obtenerTodos();
        for (Estudiante e : lista) {
            modelTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getRut(), e.getCurso(), e.getCorreo()});
        }
    }

    private void guardarEstudiante() {
        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || rut.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y RUT son obligatorios.");
            return;
        }

        Estudiante e = new Estudiante(nombre, rut, curso, correo);
        if (estudianteDAO.registrar(e)) {
            JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.");
            cargarEstudiantes();
            txtNombre.setText(""); txtRut.setText(""); txtCurso.setText(""); txtCorreo.setText("");
        }
    }

    private void eliminarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelTabla.getValueAt(fila, 0);
            if (estudianteDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
                cargarEstudiantes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.");
        }
    }
}