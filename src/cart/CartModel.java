package cart;

public class CartModel implements ICartConstants{
	Long cartId;
	Long classifiedId;
	Double bidPrice;
	String status;
	Long bidderID;
	public CartModel() {
		status = "BID";
	}
	public CartModel(Long cartId, Long classifiedId, Double bidPrice, String status, Long bidderID) {
		super();
		this.cartId = cartId;
		this.classifiedId = classifiedId;
		this.bidPrice = bidPrice;
		this.status = status;
		this.bidderID = bidderID;
	}
	@Override
	public String toString() {
		return "CartModel [cartId=" + cartId + ", classifiedId=" + classifiedId + ", bidPrice=" + bidPrice + ", status="
				+ status + ", bidderID=" + bidderID + "]";
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Long getClassifiedId() {
		return classifiedId;
	}
	public void setClassifiedId(Long classifiedId) {
		this.classifiedId = classifiedId;
	}
	public Double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getBidderID() {
		return bidderID;
	}
	public void setBidderID(Long bidderID) {
		this.bidderID = bidderID;
	}
}
