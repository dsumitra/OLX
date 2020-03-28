package olx.user;

/**
 * @author albuquea
 *
 */
public class UserConstants {

		public final static class UserColumnNames {
			public static final String USER_ID = "id";
			public static final String EMAIL = "email";
			public static final String PASSWORD = "password"; 
			public static final String IS_ADMIN = "is_admin";
			public static final String FIRSTNAME = "first_name";
			public static final String LASTNAME = "last_name";
			public static final String ADDRESS = "address";
			public static final String PHONE = "phone";
			public static final String STATUS = "status";
	
		}
		
		public enum UserStatus {
			ACTIVE, INACTIVE;
		}
	}