import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

class DB_connect {
    private Connection connection;
    public Statement stmt;
    private ResultSet rs;

    public DB_connect() throws IOException {

        Config config = new Config();

        try {
            this.connection = DriverManager.getConnection(config.get("connection_string"), config.get("user"), config.get("password"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void query(String query) {
        try {
            this.stmt = connection.createStatement();
            System.out.println(query);
            this.rs = this.stmt.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public PreparedStatement prepareQuery(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public ResultSet getResult() {
        return this.rs;
    }
}
