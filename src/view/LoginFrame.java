package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private UsuarioDAO usuarioDAO;

    public LoginFrame() {
        this.usuarioDAO = new UsuarioDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Biblioteca Escolar - Iniciar Sesión");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("ACCESO AL SISTEMA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Correo:"), gbc);

        txtCorreo = new JTextField(18);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);

        txtContrasena = new JPasswordField(18);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtContrasena, gbc);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        add(panel);

        btnIngresar.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String correo = txtCorreo.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();

        if (correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = usuarioDAO.autenticar(correo, pass);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido/a, " + usuario.getNombre() + "!\nRol: " + usuario.getRol().toUpperCase());
            MainFrame mainFrame = new MainFrame(usuario);
            mainFrame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}