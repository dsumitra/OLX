package olx.user;
import java.sql.ResultSet;

public interface IUserDAO {
	
	public void deleteUser(UserModel user);
	
	public void updateUser(UserModel user);
	
	public void addUser(UserModel user);
	
	public ResultSet getAllUsers();
	}

