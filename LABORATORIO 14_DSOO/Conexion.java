import java.sql.*;

public class Conexion {
    private static Connection conn = null;

    public static Connection getConexion() {
        try {
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:sqlite:ranking.db";
                conn = DriverManager.getConnection(url);
                System.out.println("Conexión establecida.");

                // Crear tabla si no existe
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE IF NOT EXISTS ranking (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ganador TEXT," +
                        "perdedor TEXT," +
                        "soldados_vivos INTEGER)");
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return conn;
    }
}