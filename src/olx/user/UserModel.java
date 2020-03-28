package olx.user;

import olx.user.UserConstants.UserStatus;


/**
 * @author albuquea
 *
 */
public class UserModel {
	long id;
	String firstName;
	String lastName;
	String phone;
	String email;
	String address;
	String password;
	boolean admin = false;
	UserStatus status;

	public UserModel(long id, String firstName, String lastName, String phone, String email, String address,
			boolean admin, UserStatus status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.admin = admin;
		this.status = status;
	}

	public UserModel() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", email=" + email + ", address=" + address + ", admin=" + admin + ", status=" + status + "]";
	}

}
