import java.sql.ResultSet;
import java.sql.SQLException;

public interface Model {
    public Model convertFromResultSet(ResultSet resultSet) throws SQLException;
}
