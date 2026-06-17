package tag03.template;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {
    private static MySQL instance = null;

    private final MysqlDataSource dataSource = new MysqlDataSource();
    private String connectionString = "jdbc:mysql://127.0.0.1:3306/onlineshop";
    private String user = "root";
    private String password = "";

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
        dataSource.setURL(connectionString);
    }

    public void setUser(String user) {
        this.user = user;
        dataSource.setUser(user);
    }

    public void setPassword(String password) {
        this.password = password;
        dataSource.setPassword(password);
    }

    public MySQL() {
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setURL(connectionString);
    }

    public static MySQL getInstance() {
        if (instance == null)
            instance = new MySQL();
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().dataSource.getConnection();
    }

}