package dao;

import config.DatabaseConnection;
import model.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private Connection getConnection() {
        return DatabaseConnection.getInstance().getConnection();
    }

    public boolean registrarPrestamo(int idEstudiante, int idLibro) {
        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, 0)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            Date fechaActual = Date.valueOf(LocalDate.now());
            Date fechaDevolucion = Date.valueOf(LocalDate.now().plusDays(7)); // Vencimiento a 7 días

            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, idLibro);
            stmt.setDate(3, fechaActual);
            stmt.setDate(4, fechaDevolucion);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar prestamo: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarDevolucion(int idPrestamo) {
        String sql = "UPDATE prestamos SET devuelto = 1 WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idPrestamo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar devolucion: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> obtenerTodos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_estudiante"),
                        rs.getInt("id_libro"),
                        rs.getDate("fecha_prestamo"),
                        rs.getDate("fecha_devolucion"),
                        rs.getBoolean("devuelto")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
        }
        return lista;
    }
}