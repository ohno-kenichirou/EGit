package bulletinBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProcDAO {

	public Connection conectDb() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=MembershipBulletinBoard;user=javauser;password=javauser";
		return DriverManager.getConnection(connectionUrl);
	}

}
