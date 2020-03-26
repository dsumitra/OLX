package olx.classifieds;

import java.util.Date;
import olx.classifieds.ClassifiedsConstants.ClassifiedStatus;

public class ClassifiedModel {
	private int ID;
	private String title;
	private String description;
	private int userID;
	private Date dateCreated;
	private Date dateUpdated;
	private String phone;
	private String email;
	private double price;
	private int categoryID;
	private ClassifiedStatus state;

	public ClassifiedModel(int ID, String title, String description, int userID, Date dateCreated, Date dateUpdated,
			String phone, String email, ClassifiedStatus state, double price, int categoryID) {
		super();
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.userID = userID;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.phone = phone;
		this.email = email;
		this.state = state;
		this.price = price;
		this.categoryID = categoryID;
	}

	public ClassifiedModel() {
		super();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date createDate) {
		this.dateCreated = createDate;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ClassifiedStatus getState() {
		return state;
	}

	public void setState(ClassifiedStatus state) {
		this.state = state;
	}

	public double getPrice() {
		return price;

	}

	public void setPrice(double price) {
		this.price = price;

	}

	@Override
	public String toString() {
		return "ClassifiedModel [ID=" + ID + ", title=" + title + ", description=" + description + ", userID=" + userID
				+ ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + ", phone=" + phone + ", email="
				+ email + ", price=" + price + ", categoryID=" + categoryID + ", state=" + state + "]";
	}

}
