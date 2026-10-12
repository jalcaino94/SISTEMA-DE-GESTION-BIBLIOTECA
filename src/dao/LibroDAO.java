package dao;

import config.DatabaseConnection;
import model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private Connection getConnection() {
        return DatabaseConnection.getInstance().getConnection();
    }

    /**

     */
    public synchronized boolean prestarLibroSincronizado(int idLibro) {
        String checkSql = "SELECT stock FROM libros WHERE id = ?";
        String updateSql = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";

        try (PreparedStatement checkStmt = getConnection().prepareStatement(checkSql)) {
            checkStmt.setInt(1, idLibro);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt("stock") > 0) {
                    try (PreparedStatement updateStmt = getConnection().prepareStatement(updateSql)) {
                        updateStmt.setInt(1, idLibro);
                        return updateStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en transaccion sincronizada de stock: " + e.getMessage());
        }
        return false;
    }

    public synchronized boolean devolverLibroSincronizado(int idLibro) {
        String sql = "UPDATE libros SET stock = stock + 1 WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idLibro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al incrementar stock: " + e.getMessage());
            return false;
        }
    }

    public boolean registrar(Libro l) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getAutor());
            stmt.setString(3, l.getIsbn());
            stmt.setString(4, l.getEditorial());
            stmt.setInt(5, l.getStock());
            stmt.setInt(6, l.getIdCategoria());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al registrar libro: " + ex.getMessage());
            return false;
        }
    }

    public List<Libro> obtenerTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getString("editorial"),
                        rs.getInt("stock"),
                        rs.getInt("id_categoria")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar libros: " + ex.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Libro l) {
        String sql = "UPDATE libros SET titulo=?, autor=?, isbn=?, editorial=?, stock=?, id_categoria=? WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getAutor());
            stmt.setString(3, l.getIsbn());
            stmt.setString(4, l.getEditorial());
            stmt.setInt(5, l.getStock());
            stmt.setInt(6, l.getIdCategoria());
            stmt.setInt(7, l.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar libro: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar libro: " + ex.getMessage());
            return false;
        }
    }
}