import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		String databaseUrl = "jdbc:mysql://localhost:3306/jdbc-test";
		String databaseUser = "root";
		String password = "";
		String driverDb = "com.mysql.cj.jdbc.Driver";
//		String driverDb = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverDb);
			Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, password);
			insert(connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insert(Connection con) {
		String sql = "insert into student(name, age, address) values (?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,  "Nam");
			ps.setInt(2, 22);
			ps.setString(3, "Ha Noi");
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
