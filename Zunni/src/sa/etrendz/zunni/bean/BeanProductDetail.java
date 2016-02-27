package sa.etrendz.zunni.bean;

import java.util.ArrayList;
import java.util.List;

public class BeanProductDetail 
{
	private ArrayList<BeanServerImage> PictureModels;
	
	private String Name, ShortDescription, FullDescription, MetaKeywords, MetaDescription, MetaTitle, SeName
	,Id;
	
	private BeanProductPrice ProductPrice;

	private List<BeanProductForCategory> mRelatedProducts;

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

	public List<BeanProductForCategory> getmRelatedProducts() {
		return mRelatedProducts;
	}

	public void setmRelatedProducts(List<BeanProductForCategory> mRelatedProducts) {
		this.mRelatedProducts = mRelatedProducts;
	}
}