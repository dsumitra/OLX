package olx.payments;


public class PaymentsModel {
	Long paymentID;
	Long classifiedID;
	Long userID;
	Long paymentMethodID;
	Long cartID;
	Double amount;
	
	public PaymentsModel() {
		
	}
	
	public PaymentsModel(Long paymentID, Long classifiedID, Long userID, Long paymentMethodID, Long cartID,
			Double amount) {
		super();
		this.paymentID = paymentID;
		this.classifiedID = classifiedID;
		this.userID = userID;
		this.paymentMethodID = paymentMethodID;
		this.cartID = cartID;
		this.amount = amount;
	}
	public Long getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}
	public Long getClassifiedID() {
		return classifiedID;
	}
	public void setClassifiedID(Long classifiedID) {
		this.classifiedID = classifiedID;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Long getPaymentMethodID() {
		return paymentMethodID;
	}
	public void setPaymentMethodID(Long paymentMethodID) {
		this.paymentMethodID = paymentMethodID;
	}
	public Long getCartID() {
		return cartID;
	}
	public void setCartID(Long cartID) {
		this.cartID = cartID;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "PaymentsModel [paymentID=" + paymentID + ", classifiedID=" + classifiedID + ", userID=" + userID
				+ ", paymentMethodID=" + paymentMethodID + ", cartID=" + cartID + ", amount=" + amount + "]";
	}
}
