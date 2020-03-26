package olx.classifieds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import olx.User.UserModel;
import olx.category.CategoryHelper;
import olx.category.CategoryModel;
import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
import olx.classifieds.ClassifiedsConstants.ClassifiedStatus;

public class Classifieds {
	CategoryHelper categoryHelper;
	ClassifiedDAOImpl classifiedDAOImpl;

	Classifieds() {
		categoryHelper = new CategoryHelper();
		classifiedDAOImpl = new ClassifiedDAOImpl();

	}

	// TODO MAKE 2 DISPLAY FUNCTIONS( ONE FOR SELLER/BUYER BY TWEEKING THE QUERY)
	// STATUS == APPROVED STATUS == POSTED
	// TODO EXCEPTION HANDLING (numberFormatException handling and add do while's
	// for every input)
	// TODO don't delete the classified, but mark them as deleted.
	// TODO implement function to approve or disapprove the newly added classifieds
	void buy(UserModel userModel) {

		Scanner sc = new Scanner(System.in);

		displayAllClassifieds(0);// TODO should pay User Model

		System.out.println("Enter the number of classifieds you want to buy: ");
		int buyClassifiedsCount = Integer.parseInt(sc.nextLine());

		for (int i = 0; i < buyClassifiedsCount; i++) {
			System.out.println("Enter the classified ID you to select: ");
//	 int selectedClassified = Integer.parseInt(sc.nextLine());
		}
	}

	void manageClassifieds() {
//			give options 1.update & 2.delete
	}

	void markSoldClassifieds(List<String> classifiedIDs) {

	}

//TODO : Exceptional Handling for every scanner.
	void addClassifieds() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter the number of Classifieds you want to add: ");
			int totalClassfieds = Integer.parseInt(sc.nextLine().trim());
			for (int i = 0; i < totalClassfieds; i++) {
				ClassifiedModel classifiedModel = new ClassifiedModel();
				String confirmClassified;
				do {
					System.out.println("Following is the list of categories you can select from: ");
					Map<Integer, CategoryModel> categoryMap = categoryHelper.displayCatergoriesTable();
					CategoryModel categoryModel;
					do {
						System.out.println("Enter a valid category/sub-category ID: ");
						int categoryID = Integer.parseInt(sc.nextLine());
						categoryModel = categoryMap.get(categoryID);
						classifiedModel.setCategoryID(categoryID);
					} while (categoryModel == null);

					ClassifiedStatus state = ClassifiedStatus.POSTED;
					classifiedModel.setState(state);

					Date createDate = new Date();
					// to check if the date can be created at the DB
					classifiedModel.setDateCreated(createDate);
					classifiedModel.setDateUpdated(createDate); // At creation, createDate and updateDate are same.

					System.out.println("Enter the new classified Title: ");
					String title = sc.nextLine();
					classifiedModel.setTitle(title);

					System.out.println("Enter the new classified Description: ");
					String description = sc.nextLine();
					classifiedModel.setDescription(description);

					System.out.println("Enter the new classified Price: ");
					double price = Double.parseDouble(sc.nextLine());
					classifiedModel.setPrice(price);

					/*
					 * TODO get the userModel and do userModel.getId(), userModel.getPhone(),
					 * userModel.getEmail(); and set them to the classified model userID; phone;
					 * email;
					 */
					classifiedModel.setUserID(0);
					classifiedModel.setPhone("1234567890");
					classifiedModel.setEmail("dummy@gmail.com");
					System.out
							.println("The entered classified is: " + previewClassified(classifiedModel, categoryModel));

					System.out.println("Do you wish to post the above classified ?(Y/N): ");
					confirmClassified = sc.nextLine();

				} while (!"Y".equalsIgnoreCase(confirmClassified));

				classifiedDAOImpl.addClassified(classifiedModel);

			}
			sc.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Kindly enter a valid input: ");
			addClassifieds();
		}
	}

	String previewClassified(ClassifiedModel classified, CategoryModel categoryModel) {
		return "\n Category:" + categoryModel.getPrimaryCategory() + "\n Sub-category: "
				+ categoryModel.getSubCategory() + "\n Title:" + classified.getTitle() + "\n Description:"
				+ classified.getDescription() + "\n Price:" + classified.getPrice();
	}

	Map<Integer, ClassifiedModel> displayAllClassifieds(int userID) {
		Map<Integer, ClassifiedModel> classifiedMap = new HashMap<>();
		ResultSet rs = classifiedDAOImpl.getAllClassifieds(userID);
		System.out.println("List of classifieds:\n");
		String header = "ID\t\t Title \t\t Description \t\t Price \t\t\t Phone \t\t Email \t\t Date Created";
		boolean userFilter = false;

		if (userID != 0) {
			userFilter = true;
			header += "\t\t State";
		}
		System.out.println(header);
		try {
			while (rs.next()) {
				int ID = rs.getInt(ClassifiedColumnNames.ID);
				ClassifiedModel classifiedModel = createClassifiedModel(rs);
				classifiedMap.put(ID, classifiedModel);
				String row = ID + "\t\t" + classifiedModel.getTitle() + "\t\t" + classifiedModel.getDescription()
						+ "\t\t" + classifiedModel.getPrice() + "\t\t" + classifiedModel.getPhone() + "\t\t" + "\t\t"
						+ classifiedModel.getEmail() + "\t\t" + classifiedModel.getDateCreated();
				if (userFilter == true) {
					row += "\t\t" + classifiedModel.getState();
				}
				System.out.println(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classifiedMap;
	}

	ClassifiedModel createClassifiedModel(ResultSet rs) throws SQLException {
		int ID = rs.getInt(ClassifiedColumnNames.ID);
		int userID = rs.getInt(ClassifiedColumnNames.USER_ID);
		int categoryID = rs.getInt(ClassifiedColumnNames.CATEGORY_ID);
		String title = rs.getString(ClassifiedColumnNames.TITLE);
		String description = rs.getString(ClassifiedColumnNames.DESCRIPTION);
		double price = rs.getDouble(ClassifiedColumnNames.PRICE);
		String email = rs.getString(ClassifiedColumnNames.EMAIL);
		String phone = rs.getString(ClassifiedColumnNames.PHONE);
		ClassifiedStatus state = ClassifiedStatus.valueOf(rs.getString(ClassifiedColumnNames.STATE));
		Date dateCreated = rs.getDate(ClassifiedColumnNames.DATE_CREATED); // cross verify
		Date dateUpdated = rs.getDate(ClassifiedColumnNames.DATE_UPDATED);
		ClassifiedModel classifiedModel = new ClassifiedModel(ID, title, description, userID, dateCreated, dateUpdated,
				phone, email, state, price, categoryID);
		return classifiedModel;
	}

	Map<Integer, ClassifiedModel> displayClassifiedsByUser(int userID) {
		// TODO: pass userID

		return displayAllClassifieds(1);

	}

	void updateClassified(int userID) {
		int dummyUserID = 1;// TODO to use the argument userID here.
		int updateCount;
		ClassifiedModel classifiedModel;
		Map<Integer, ClassifiedModel> classifieds = displayAllClassifieds(dummyUserID);
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Select the number of classifieds you want to update: ");
			updateCount = Integer.parseInt(sc.nextLine());
		} while (classifieds.size() < updateCount || updateCount <= 0);

		for (int i = 0; i < updateCount; i++) {
			do {
				System.out.println("Enter the classified ID you want to update: ");
				int classifiedID = Integer.parseInt(sc.nextLine());
				classifiedModel = classifieds.get(classifiedID);//
				updateClassifiedAttribute(classifiedModel, sc);
				classifiedDAOImpl.updateClassified(classifiedModel);

			} while (classifiedModel == null);
		}
	}

	void updateClassifiedAttribute(ClassifiedModel classifiedModel, Scanner sc) {
		Map<Integer, String> attrOpts = new HashMap<>();
		attrOpts.put(1, "Title");
		attrOpts.put(2, "Description");
		attrOpts.put(3, "Price");
		System.out.println("Following the attributes you can update:" + "\n 1." + attrOpts.get(1) + "\n 2. "
				+ attrOpts.get(2) + "\n 3. " + attrOpts.get(3));

		int updateAttributeCount;
		String attribute;

		do {
			System.out.println("Enter how many attributes you want to update:  ");
			updateAttributeCount = Integer.parseInt(sc.nextLine());
		} while (3 < updateAttributeCount || updateAttributeCount <= 0);

		int attributeID;

		for (int j = 0; j < updateAttributeCount; j++) {
			do {
				System.out.println("Enter the attribute ID you want to update: ");
				attributeID = Integer.parseInt(sc.nextLine());
				attribute = attrOpts.get(attributeID);
			} while (attributeID > attrOpts.size() || attributeID <= 0);

			if (attribute == "Title") {
				System.out.println("Enter the new classified Title: ");
//				String existingValue = classifiedModel.getTitle();
				String updatedValue = sc.nextLine();
				classifiedModel.setTitle(updatedValue);
//				classifiedDAOImpl.updateClassified(attribute, updatedValue, existingValue);
			} else if (attribute == "Description") {
				System.out.println("Enter the new classified Description: ");
//				String existingValue = classifiedModel.getDescription();
				String updatedValue = sc.nextLine();
				classifiedModel.setDescription(updatedValue);
//				classifiedDAOImpl.updateClassified(attribute, updatedValue, existingValue);

			} else if (attribute == "Price") {
				System.out.println("Enter the new classified Price: ");
//				String existingValue = Double.toString(classifiedModel.getPrice());
				String updatedValue = sc.nextLine();
				classifiedModel.setPrice(Double.parseDouble(updatedValue));
//				classifiedDAOImpl.updateClassified(attribute, updatedValue, existingValue);
			}
		}
	}

	void deleteClassifieds(int userID) {
		int dummyUserID = 1; // TODO to use the argument userID here.
		int totalDelNumber = 0;
		Scanner del = new Scanner(System.in);
		ClassifiedModel classifiedModel;
		Map<Integer, ClassifiedModel> classifieds = displayClassifiedsByUser(dummyUserID);

		do {
			System.out.println("Select the number of classifieds you want to delete: ");
			totalDelNumber = Integer.parseInt(del.nextLine());
		} while (classifieds.size() < totalDelNumber || totalDelNumber <= 0);

		for (int i = 0; i < totalDelNumber; i++) {
			do {
				System.out.println("Enter the classified you want to delete: ");
				int classifiedID = Integer.parseInt(del.nextLine());
				classifiedModel = classifieds.get(classifiedID);
				classifiedDAOImpl.deleteClassified(classifiedModel);
				classifieds.size();
			} while (classifiedModel == null);
		}

		del.close();
	}
}
