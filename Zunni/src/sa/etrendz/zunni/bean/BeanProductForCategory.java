package sa.etrendz.zunni.bean;


public class BeanProductForCategory 
{
	private String Name, ShortDescription, FullDescription, Id, SeName;
	private BeanProductPrice ProductPrice;
	private BeanServerImage DefaultPictureModel;
	private BeanReviewModel ReviewOverviewModel;
	
	public BeanServerImage getImageModel() {
		return DefaultPictureModel;
	}
	
	public void setImageModel(BeanServerImage defaultPictureModel) {
		DefaultPictureModel = defaultPictureModel;
	}
	
	public String getProductName() {
		return Name;
	}
	
	public void setProductName(String name) {
		Name = name;
	}
	
	public BeanProductPrice getProductPrice() {
		return ProductPrice;
	}
	
	public void setProductPrice(BeanProductPrice productPrice) {
		ProductPrice = productPrice;
	}

	public String getmProductId() {
		return Id;
	}

	public void setmProductId(String id) {
		Id = id;
	}
}