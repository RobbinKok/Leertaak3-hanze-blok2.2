public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Init table of database
     */
    public static void init(DB_connect connect) {
        connect.query("CREATE TABLE `users` ( `id` int(11) NOT NULL,  `username` varchar(255) NOT NULL, `password` varchar(255) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
//        connect.query("INSERT INTO `users` (`id`, `username`, `password`) VALUES (1, 'hanze', 'hanze')");
    }

    /**
     * Check if the credentials are correct
     * @return
     */
    public boolean login(DB_connect connect) {
        return true;
    }
}
