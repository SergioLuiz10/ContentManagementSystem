package persistency;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import user.User;

public class UserPersistencyDB {

    private static final String DB_URL = "jdbc:hsqldb:mem:devDB";
    private static final String USER = "root";
    private static final String PASSWORD = "2004";

    private Connection dbConnection = null;

    public UserPersistencyDB() {
        createTable();
    }

    public Connection getConnection() throws SQLException {
        if (dbConnection == null) {
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return dbConnection;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User ("
                + "id INTEGER IDENTITY PRIMARY KEY,"
                + "nickname VARCHAR(225),"
                + "password VARCHAR(225),"
                + "created_at DATE);";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void save(User user) {
        String sql = "INSERT INTO User (nickname, password, created_at) VALUES (?, ?, ?)";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getPassword());
            stmt.setDate(3, new java.sql.Date(user.getCreatedAt().getTime())); // Conversão correta da data
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> list() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                User user = new User(
                        rs.getString("nickname"),
                        rs.getString("password")
                );
                user.setId(rs.getInt("id"));
                user.setCreatedAt(rs.getDate("created_at"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void update(User user) {
        String sql = "UPDATE User SET nickname = ?, password = ? WHERE id = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar um usuário pelo ID
    public boolean delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        boolean delete = false;

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            delete = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return delete;
    }
}
