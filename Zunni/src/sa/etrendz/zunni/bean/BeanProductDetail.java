package sa.etrendz.zunni.bean;

import java.util.ArrayList;

public class BeanProductDetail 
{
	private ArrayList<BeanServerImage> PictureModels;
	
	private String Name, ShortDescription, FullDescription, MetaKeywords, MetaDescription, MetaTitle, SeName
	,Id;
	
	private BeanProductPrice ProductPrice;

	public String getmProductId() {
		return Id;
	}

	public void setmProductId(String id) {
		Id = id;
	}

	public BeanProductPrice getProductPrice() {
		return ProductPrice;
	}

	public void setProductPrice(BeanProductPrice productPrice) {
		ProductPrice = productPrice;
	}

	public ArrayList<BeanServerImage> getmProductImageModel() {
		return PictureModels;
	}

	public void setmProductImageModel(ArrayList<BeanServerImage> pictureModels) {
		PictureModels = pictureModels;
	}

	public String getmProductName() {
		return Name;
	}

	public void setmProductName(String name) {
		Name = name;
	}
}