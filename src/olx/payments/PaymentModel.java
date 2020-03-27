package olx.payments;

public class PaymentModel {
	private int id;

	private String userID;
	private String paymentMethod;
	private String cardNumber;
	private String expMonth;
	private String expYear;
	private String nameCard;

	public PaymentModel(int id, String userID, String paymentMethod, String cardNumber, String expMonth, String expYear,
			String nameCard) {
		super();
		this.id = id;
		this.userID = userID;
		this.paymentMethod = paymentMethod;
		this.cardNumber = cardNumber;
		this.expMonth = expMonth;
		this.expYear = expYear;
		this.nameCard = nameCard;
	}
	
	public PaymentModel(){
		super();
	}

	public int getId() {
		return id;
	}

	public String getUserID() {
		return userID;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public String getExpYear() {
		return expYear;
	}

	public String getNameCard() {
		return nameCard;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public void setNameCard(String nameCard) {
		this.nameCard = nameCard;
	}

	@Override
	public String toString() {
		return "PaymentModel [id=" + id + ", userID=" + userID + ", paymentMethod=" + paymentMethod + ", cardNumber="
				+ cardNumber + ", expMonth=" + expMonth + ", expYear=" + expYear + ", nameCard=" + nameCard + "]";
	}

}
