package sa.etrendz.zunni.bean;

import sa.etrendz.zunni.bean.BeanGetAllCategory.PictureModel;

public class BeanProductDetail 
{
	private String Name, ShortDescription, FullDescription, Id, SeName;
	private BeanProductPrice ProductPrice;
	private PictureModel DefaultPictureModel;
	private BeanReviewModel ReviewOverviewModel;
	public PictureModel getImageModel() {
		return DefaultPictureModel;
	}
	public void setImageModel(PictureModel defaultPictureModel) {
		DefaultPictureModel = defaultPictureModel;
	}
	public String getProductName() {
		return Name;
	}
	public void setProductName(String name) {
		Name = name;
	}
}