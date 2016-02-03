package sa.etrendz.zunni.bean;

public class BeanProductPrice
{
	private String OldPrice, Price;
	
	private String AvailableForPreOrder, DisableAddToCompareListButton, DisableBuyButton, DisableWishlistButton
				, DisplayTaxShippingInfo, ForceRedirectionAfterAddingToCart, IsRental;

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getmProductOldPrice() {
		return OldPrice;
	}

	public void setmProductOldPrice(String oldPrice) {
		OldPrice = oldPrice;
	}
}
