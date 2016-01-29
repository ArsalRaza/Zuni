package sa.etrendz.zunni.bean;

import java.util.ArrayList;
import java.util.List;


public class CategoryDetailBean 
{
	private String Name, Description, MetaKeywords, MetaDescription, MetaTitle, SeName
	, HasChild, DisplayCategoryBreadcrumb
	,Id;

	private BeanServerImage PictureModel;
	
	private Object CustomProperties;
	private Object PriceRanges;
	private Object PageSizeOptions;
	
	private ArrayList<BeanProductDetail> Products;
	private ArrayList<BeanGetAllCategory> SubCategories;
	private List<BeanSubCategoryProductForAdapter> mBeanListForAdapter;
	
	public ArrayList<BeanProductDetail> getProducts() {
		return Products;
	}
	public void setProducts(ArrayList<BeanProductDetail> products) {
		Products = products;
	}
	public ArrayList<BeanGetAllCategory> getSubCategories() {
		return SubCategories;
	}
	public void setSubCategories(ArrayList<BeanGetAllCategory> subCategories) {
		SubCategories = subCategories;
	}
	public List<BeanSubCategoryProductForAdapter> getmBeanListForAdapter() {
		return mBeanListForAdapter;
	}
	public void setmBeanListForAdapter(List<BeanSubCategoryProductForAdapter> mBeanListForAdapter) {
		this.mBeanListForAdapter = mBeanListForAdapter;
	}
}