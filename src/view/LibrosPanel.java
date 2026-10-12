package view;

import dao.CategoriaDAO;
import dao.LibroDAO;
import model.Categoria;
import model.Libro;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibrosPanel extends JPanel {
    private JTable tablaLibros;
    private DefaultTableModel modelTabla;
    private JTextField txtTitulo, txtAutor, txtIsbn, txtEditorial, txtStock;
    private JComboBox<Categoria> comboCategorias;
    private JButton btnGuardar, btnEliminar, btnRefrescar;
    private LibroDAO libroDAO;
    private CategoriaDAO categoriaDAO;
    private Usuario usuario;

    public LibrosPanel(Usuario usuario) {
        this.usuario = usuario;
        this.libroDAO = new LibroDAO();
        this.categoriaDAO = new CategoriaDAO();
        setLayout(new BorderLayout(10, 10));
        initUI();
        cargarCategorias();
        cargarLibros();
    }

    private void initUI() {
        // Formulario (Solo editable si es bibliotecario)
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();
        comboCategorias = new JComboBox<>();

        panelForm.add(new JLabel("Título:")); panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Autor:")); panelForm.add(txtAutor);
        panelForm.add(new JLabel("ISBN:")); panelForm.add(txtIsbn);
        panelForm.add(new JLabel("Editorial:")); panelForm.add(txtEditorial);
        panelForm.add(new JLabel("Stock:")); panelForm.add(txtStock);
        panelForm.add(new JLabel("Categoría:")); panelForm.add(comboCategorias);

        btnGuardar = new JButton("Guardar Libro");
        btnEliminar = new JButton("Eliminar Seleccionado");
        btnRefrescar = new JButton("Refrescar");

        JPanel panelBotones = new JPanel();
        if (usuario.getRol().equalsIgnoreCase("bibliotecario")) {
            panelBotones.add(btnGuardar);
            panelBotones.add(btnEliminar);
        }
        panelBotones.add(btnRefrescar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        if (usuario.getRol().equalsIgnoreCase("bibliotecario")) {
            panelSuperior.add(panelForm, BorderLayout.CENTER);
        }
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        modelTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "ID Categ."}, 0);
        tablaLibros = new JTable(modelTabla);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnRefrescar.addActionListener(e -> cargarLibros());
    }

    private void cargarCategorias() {
        comboCategorias.removeAllItems();
        List<Categoria> lista = categoriaDAO.obtenerTodas();
        for (Categoria c : lista) {
            comboCategorias.addItem(c);
        }
    }

    public void cargarLibros() {
        modelTabla.setRowCount(0);
        List<Libro> lista = libroDAO.obtenerTodos();
        for (Libro l : lista) {
            modelTabla.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.getEditorial(), l.getStock(), l.getIdCategoria()});
        }
    }

    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String editorial = txtEditorial.getText().trim();
            int stock = Integer.parseInt(txtStock.getText().trim());
            Categoria cat = (Categoria) comboCategorias.getSelectedItem();

            if (titulo.isEmpty() || cat == null) {
                JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.");
                return;
            }

            Libro l = new Libro(titulo, autor, isbn, editorial, stock, cat.getId());
            if (libroDAO.registrar(l)) {
                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.");
                cargarLibros();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número válido.");
        }
    }

    private void eliminarLibro() {
        int fila = tablaLibros.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelTabla.getValueAt(fila, 0);
            if (libroDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado.");
                cargarLibros();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla.");
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");
    }
}