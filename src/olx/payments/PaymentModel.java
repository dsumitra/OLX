package olx.payments;

public class PaymentModel {
	private	int id ;
	private  String email;
	private  String password;
	private  String isAdmin;
	private  String firstName;
	private  String lastName;
	private  String address;
	private  String phone;
	private  String status;
	public PaymentModel(int id, String email, String password, String isAdmin, String firstName, String lastName,
			String address, String phone, String status) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.status = status;
	}
	
	public PaymentModel() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PaymentModel [id=" + id + ", email=" + email + ", password=" + password + ", isAdmin=" + isAdmin
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phone=" + phone
				+ ", status=" + status + "]";
	}
}
