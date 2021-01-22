import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Test {


    public static void main(String[] args) {

        DB_connect db = new DB_connect();

        Measurement measurement = new Measurement(
                10010,
               "biem",
               1.5,
                2.0,
                3.0,
                4.0,
                5.0,
                6.0,
                7.0,
                8.0,
                0,
                8.0,
                1
        );
        try {
            measurement.create(db);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
