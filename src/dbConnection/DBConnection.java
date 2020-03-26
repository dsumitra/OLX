package dbConnection;

import java.sql.*;
import java.util.Formatter;

public class DBConnection {
	static Connection conn;
	static Statement stat;

	private static Connection initCon(String conStr, String usr, String pass)
			throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection(conStr, usr, pass);
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String cs1 = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String cs2 = "jdbc:oracle:thin:@localhost:1521/orc";

		if (conn == null) {
			try {
				conn = initCon(cs1, "amazon", "amazon");
			} catch (SQLRecoverableException e) {
				try {
					conn = initCon(cs2, "amazon", "amazon");
				} catch (SQLRecoverableException e2) {
					System.err.println("======= Unable to connect  to URL :" + cs1);
					e.printStackTrace();
					System.err.println("======= Unable to connect  to URL :" + cs2);
					e2.printStackTrace();
					System.exit(0);
				}
			}
		}
		return conn;
	}

	public static Statement createStatement() throws SQLException, ClassNotFoundException {
		if (conn == null) {
			conn = getConnection();
		}

		return conn.createStatement();
	}

	public static Statement getStatement() throws SQLException, ClassNotFoundException {
		if (stat == null) {
			stat = createStatement();
		}
		return stat;
	}

	public static ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException {
		return getStatement().executeQuery(sql);
	}

	public static int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
		return getStatement().executeUpdate(sql);
	}

	private static String format(String format, Object... args) {
		Formatter formatter = new Formatter();
		String out = formatter.format(format, args).toString();
		formatter.close();
		return out;
	}

	public static ResultSet executeQueryFormat(String sqlFormatString, Object... args)
			throws SQLException, ClassNotFoundException {
		return getStatement().executeQuery(format(sqlFormatString, args));
	}

	public static int executeUpdateFormat(String sqlFormatString, Object... args)
			throws SQLException, ClassNotFoundException {
		return getStatement().executeUpdate(format(sqlFormatString, args));
	}

	public static boolean executeFormat(String sqlFormatString, Object... args)
			throws SQLException, ClassNotFoundException {
		return getStatement().execute(format(sqlFormatString, args));
	}

}
