public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        System.out.println(this.toString());
    }

    /**
     * Check if the credentials are correct
     * @return
     */
    public boolean login() {
        return this.username.equals("hanze") && this.password.equals("hanze");
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
