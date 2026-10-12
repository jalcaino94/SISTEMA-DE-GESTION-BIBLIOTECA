package dao;

import config.DatabaseConnection;
import model.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    private Connection getConnection() {
        return DatabaseConnection.getInstance().getConnection();
    }

    public boolean registrar(Estudiante e) {
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getRut());
            stmt.setString(3, e.getCurso());
            stmt.setString(4, e.getCorreo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al registrar estudiante: " + ex.getMessage());
            return false;
        }
    }

    public List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rut"),
                        rs.getString("curso"),
                        rs.getString("correo")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener estudiantes: " + ex.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Estudiante e) {
        String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getRut());
            stmt.setString(3, e.getCurso());
            stmt.setString(4, e.getCorreo());
            stmt.setInt(5, e.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar estudiante: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar estudiante: " + ex.getMessage());
            return false;
        }
    }
}