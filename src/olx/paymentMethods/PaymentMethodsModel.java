package olx.paymentMethods;

public class PaymentMethodsModel {
	Long pmID,userID;
	String paymentMethod,cardNumber;
	Long expMonth,	expYear;
	String nameOnCard;
	
	public PaymentMethodsModel() {
		
	}
	
	public PaymentMethodsModel(Long pmID, Long userID, String paymentMethod, String cardNumber, Long expMonth,
			Long expYear, String nameOnCard) {
		super();
		this.pmID = pmID;
		this.userID = userID;
		this.paymentMethod = paymentMethod;
		this.cardNumber = cardNumber;
		this.expMonth = expMonth;
		this.expYear = expYear;
		this.nameOnCard = nameOnCard;
	}
	
	public Long getPmID() {
		return pmID;
	}
	public void setPmID(Long pmID) {
		this.pmID = pmID;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Long getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(Long expMonth) {
		this.expMonth = expMonth;
	}
	public Long getExpYear() {
		return expYear;
	}
	public void setExpYear(Long expYear) {
		this.expYear = expYear;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	@Override
	public String toString() {
		return "PaymentMethodsModel [pmID=" + pmID + ", userID=" + userID + ", paymentMethod=" + paymentMethod
				+ ", cardNumber=" + cardNumber + ", expMonth=" + expMonth + ", expYear=" + expYear + ", nameOnCard="
				+ nameOnCard + "]";
	}
}
