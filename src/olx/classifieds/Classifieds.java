package olx.classifieds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import olx.user.UserModel;
import olx.category.CategoryDAOImpl;
import olx.category.CategoryHelper;
import olx.category.CategoryModel;
import olx.classifieds.ClassifiedsConstants.ClassifiedColumnNames;
import olx.classifieds.ClassifiedsConstants.ClassifiedStatus;

public class Classifieds {
	CategoryHelper categoryHelper;
	ClassifiedDAOImpl classifiedDAOImpl;
	CategoryDAOImpl categoryDAOImpl;
	Scanner sc = new Scanner(System.in);

	public Classifieds() {
		categoryHelper = new CategoryHelper();
		classifiedDAOImpl = new ClassifiedDAOImpl();
		categoryDAOImpl = new CategoryDAOImpl();
	}

//TODO : Exceptional Handling for every scanner.
	public void addClassifieds(UserModel userModel) {
		try {
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

					classifiedModel.setUserID(userModel.getId());
					classifiedModel.setPhone(userModel.getPhone());
					classifiedModel.setEmail(userModel.getEmail());

					System.out.println("The entered classified is: ");
					previewClassified(classifiedModel, categoryModel);
					System.out.println("Do you wish to post the above classified ?(Y/N): ");
					confirmClassified = sc.nextLine();

				} while (!"Y".equalsIgnoreCase(confirmClassified));

				classifiedDAOImpl.addClassified(classifiedModel);

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Kindly enter a valid input: ");
			addClassifieds(userModel);
		}
	}

	void previewClassified(ClassifiedModel classified, CategoryModel categoryModel) {
		System.out.println("\n Category:" + categoryModel.getPrimaryCategory() + "\n Sub-category: "
				+ categoryModel.getSubCategory() + "\n Title:" + classified.getTitle() + "\n Description:"
				+ classified.getDescription() + "\n Price:" + classified.getPrice());
	}

	Map<Integer, ClassifiedModel> displayAllClassifieds(ResultSet rs, boolean showStatusColumn) {
		String header = "";
		Map<Integer, ClassifiedModel> classifiedMap = new HashMap<>();
		try {
			System.out.println("List of classifieds:\n");
			header = "ID\t\t Title \t\t Description \t\t Price \t\t\t Phone \t\t Email \t\t Date Created";
			if (showStatusColumn == true) {
				header += "\t\t State";
			}
			System.out.println(header);
			while (rs.next()) {
				int ID = rs.getInt(ClassifiedColumnNames.ID);
				ClassifiedModel classifiedModel = createClassifiedModel(rs);
				classifiedMap.put(ID, classifiedModel);
				String row = ID + "\t\t" + classifiedModel.getTitle() + "\t\t" + classifiedModel.getDescription()
						+ "\t\t" + classifiedModel.getPrice() + "\t\t" + classifiedModel.getPhone() + "\t\t" + "\t\t"
						+ classifiedModel.getEmail() + "\t\t" + classifiedModel.getDateCreated();
				if (showStatusColumn == true) {
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

	void updateClassified(UserModel userModel) {
		int updateCount;
		ClassifiedModel classifiedModel;
		ResultSet rs = classifiedDAOImpl.getClassifiedsByUserId(userModel.getId());
		Map<Integer, ClassifiedModel> classifieds = displayAllClassifieds(rs, false);
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Select the number of classifieds you want to update: ");
			updateCount = Integer.parseInt(sc.nextLine());
		} while (classifieds.size() < updateCount || updateCount <= 0); // Validated

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

	void deleteClassifieds(UserModel userModel) {
		int totalDelNumber = 0;
		ClassifiedModel classifiedModel;
		ResultSet rs = classifiedDAOImpl.getClassifiedsByUserId(userModel.getId());

		Map<Integer, ClassifiedModel> classifieds = displayAllClassifieds(rs, true);

		do {
			System.out.println("Select the number of classifieds you want to delete: ");
			totalDelNumber = Integer.parseInt(sc.nextLine());
		} while (classifieds.size() < totalDelNumber || totalDelNumber <= 0);

		for (int i = 0; i < totalDelNumber; i++) {
			do {
				System.out.println("Enter the classified you want to delete: ");
				int classifiedID = Integer.parseInt(sc.nextLine());
				classifiedModel = classifieds.get(classifiedID);
				classifiedModel.setState(ClassifiedStatus.REMOVED);
//				classifiedDAOImpl.deleteClassified(classifiedModel);
				classifiedDAOImpl.updateClassified(classifiedModel);
				classifieds.size();
			} while (classifiedModel == null);
		}

	}

	// TODO MAKE 2 DISPLAY FUNCTIONS( ONE FOR SELLER/BUYER BY TWEEKING THE QUERY)
	// --- done
	// STATUS == APPROVED STATUS == POSTED ----- done
	// TODO EXCEPTION HANDLING (numberFormatException handling and add do while's
	// for every input)
	// TODO don't delete the classified, but mark them as deleted.-- done
	// TODO implement function to approve or disapprove the newly added classifieds
	// -- done (but error)

	public void buy(UserModel userModel) {
		int buyCount, classifiedId = 0;
		List<Integer> selectedCategoryIds = new ArrayList<Integer>();
		Map<Integer, ClassifiedModel> classifiedMap = displayApprovedClassifieds();
		if (classifiedMap.size() == 0) {
			System.out.println("No classifieds available");
			return;
		}
		do {
			System.out.println("Enter the number of classifieds you want to buy: ");
			buyCount = Integer.parseInt(sc.nextLine()); // TODO Handle space exception.
		} while (buyCount > classifiedMap.size());

		for (int i = 0; i < buyCount; i++) {
			do {
				System.out.println("Enter a valid classified ID: ");
				classifiedId = Integer.parseInt(sc.nextLine());
			} while (classifiedMap.get(classifiedId) == null);
			selectedCategoryIds.add(classifiedId);

			System.out.println("Added to cart: " + classifiedMap.get(classifiedId).getTitle());
		}
//		TODO: call cartView
//		cartView.addToCart(selectedCategoryIds, userModel);
	}

	Map<Integer, ClassifiedModel> displayApprovedClassifieds() {
		ResultSet rs = ClassifiedDAOImpl.filterClassifiedsByState(ClassifiedStatus.APPROVED);
		Map<Integer, ClassifiedModel> classifiedMap = displayAllClassifieds(rs, false);
		return classifiedMap;
	}

	public void displayPostedClassifieds() {
		String confirm;
		Map<Integer, CategoryModel> categoryMap = categoryHelper.getAllCategories();
		ResultSet rs = ClassifiedDAOImpl.filterClassifiedsByState(ClassifiedStatus.POSTED);
		List<ClassifiedModel> toUpdate = new ArrayList<ClassifiedModel>();
		try {
			while (rs.next()) {
				ClassifiedModel classifiedModel = createClassifiedModel(rs);
				CategoryModel categoryModel = categoryMap.get(classifiedModel.getCategoryID());
				System.out.println("New Classified details:");
				previewClassified(classifiedModel, categoryModel);
				do {
					System.out.println("Do you approve this classified? (Y/N): ");
					confirm = sc.nextLine().trim();
				} while (!(confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")));

				if (confirm.equalsIgnoreCase("Y")) {
					classifiedModel.setState(ClassifiedStatus.APPROVED);
				} else {
					classifiedModel.setState(ClassifiedStatus.REJECTED);
				}
				toUpdate.add(classifiedModel);
			}
			for (ClassifiedModel classifiedModel : toUpdate) {
				classifiedDAOImpl.updateClassified(classifiedModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void manageClassifieds(UserModel userModel) {
		int option = 0;
		System.out.println("Classified Options:\n 1.Update Classified \n 2.Delete Classified");
		do {
			System.out.println("Enter a valid option number: ");
			option = Integer.parseInt(sc.nextLine());
		} while (!(option == 1 || option == 2));
		if (option == 1) {
			updateClassified(userModel);
		} else {
			deleteClassifieds(userModel);
		}

	}

	public void markClassifiedsAsSold(List<Integer> classifiedIDs) {
		classifiedDAOImpl.updateStatusById(classifiedIDs);

	}

	public void viewOrderHistory(UserModel UserModel) {
		ResultSet rs = classifiedDAOImpl.OrderHistory(UserModel);
		try {
			System.out.println("Classified ID \t\t  Title \t\t Price \t\t Purchase Date\t\t");
			while (rs.next()) {
				// int ID = rs.getInt();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void sellClassifieds(UserModel userModel) {
		System.out.println("Sell Classifieds");
		addClassifieds(userModel);
	}

}
