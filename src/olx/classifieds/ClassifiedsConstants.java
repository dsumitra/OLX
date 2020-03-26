package olx.classifieds;

public class ClassifiedsConstants {

	public enum ClassifiedStatus {
		POSTED, APPROVED, REJECTED, SOLD, REMOVED;
	}

	public final static class ClassifiedColumnNames {
		public static final String TITLE = "title";
		public static final String ID = "id";
		public static final String CATEGORY_ID = "category_id";
		public static final String DESCRIPTION = "description";
		public static final String PRICE = "price";
		public static final String PHONE = "phone";
		public static final String EMAIL = "email";
		public static final String USER_ID = "user_id";
		public static final String STATE = "state";
		public static final String DATE_CREATED = "create_date";
		public static final String DATE_UPDATED = "update_date";
	}
}
