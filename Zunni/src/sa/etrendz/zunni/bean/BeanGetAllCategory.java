package sa.etrendz.zunni.bean;

public class BeanGetAllCategory 
{
	private String Name, Description, MetaKeywords, MetaDescription, MetaTitle, SeName
				, HasChild = "false", DisplayCategoryBreadcrumb
				,Id;
	
	private BeanServerImage PictureModel;

	private Object CustomProperties;
	private Object PriceRanges;
	private Object PageSizeOptions;

	public BeanServerImage getImageModel() {
		return PictureModel;
	}

	public void setImageModel(BeanServerImage pictureModel) {
		this.PictureModel = pictureModel;
	}

	public String getmCategoryName() {
		return Name;
	}

	public void setmCategoryName(String name) {
		Name = name;
	}

	public String getmCategoryId() {
		return Id;
	}

	public void setmCategoryId(String id) {
		Id = id;
	}

	public String getmHasSubCategory() {
		return HasChild;
	}

	public void setmHasSubCategory(String hasChild) {
		HasChild = hasChild;
	}
}