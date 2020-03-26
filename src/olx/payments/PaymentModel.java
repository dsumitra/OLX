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

}
