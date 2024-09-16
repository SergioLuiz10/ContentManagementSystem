package persistency;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import content.Content;

public class ContentPersistencyDB {

    private static final String DB_URL = "jdbc:hsqldb:mem:devDB";
    private static final String USER = "dev";
    private static final String PASSWORD = "123";

    private Connection dbConnection = null;

    public ContentPersistencyDB() {
        createTable();
    }

    public Connection getConnection() throws SQLException {
        if (dbConnection == null) {
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }
        return dbConnection;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Content ("
                + "id INTEGER IDENTITY PRIMARY KEY,"
                + "title VARCHAR(255),"
                + "content VARCHAR(10000),"
                + "created_at DATE);";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Content content) {
        String sql = "INSERT INTO Content (title, content, created_at) VALUES (?, ?, ?)";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, content.getTitle());
            stmt.setString(2, content.getContent());
            stmt.setDate(3, new java.sql.Date(content.getCreatedAt().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Content> list() {
        List<Content> contents = new ArrayList<>();
        String sql = "SELECT * FROM Content";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Content content = new Content();
                content.setId(rs.getInt("id"));
                content.setTitle(rs.getString("title"));
                content.setContent(rs.getString("content"));
                content.setCreatedAt(rs.getDate("created_at"));

                contents.add(content);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contents;
    }

    public void update(Content content) {
        String sql = "UPDATE Content SET title = ?, content = ? WHERE id = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, content.getTitle());
            stmt.setString(2, content.getContent());
            stmt.setInt(3, content.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Content WHERE id = ?";
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
