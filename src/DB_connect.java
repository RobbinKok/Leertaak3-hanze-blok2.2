import java.sql.*;

class DB_connect {
    private Connection connection;
    public Statement stmt;
    private ResultSet rs;

    public DB_connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/unwdmi?useSSL=false", "root", "root");
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
