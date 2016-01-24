package sa.etrendz.zunni.adapter;

import java.util.List;

import com.squareup.picasso.Callback;

import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.bean.BeanSubCategoryProductForAdapter;
import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SubCategoryProductRecyclerAdapter extends
		Adapter<SubCategoryProductViewHolder> implements Callback 
{
	private List<BeanSubCategoryProductForAdapter> mCategoryDetailBean;

	public SubCategoryProductRecyclerAdapter(Activity activity, List<BeanSubCategoryProductForAdapter> mCategoryDetailBean)
	{
		this.mCategoryDetailBean = mCategoryDetailBean;
	}
	
	@Override
	public int getItemCount() 
	{
		return mCategoryDetailBean.size();
	}

	@Override
	public void onBindViewHolder(SubCategoryProductViewHolder viewHolder, int position) 
	{
		BeanSubCategoryProductForAdapter mAdapterBean = mCategoryDetailBean.get(position);
		String url = "", title = "";
		if (mAdapterBean.ismIsSubCategoryObject())
		{
			// For Sub Category
			BeanGetAllCategory bean = (BeanGetAllCategory) mAdapterBean.getmBean();
			url = bean.getImageModel().getThumbImageUrl();
			title = bean.getmCategoryName() + " (Category)";
		}
		else
		{
			// For Product
			BeanProductDetail bean = (BeanProductDetail) mAdapterBean.getmBean();
			url = bean.getImageModel().getThumbImageUrl();
			title = bean.getProductName() + " (Product)";
		}
		
		viewHolder.mTitleTextView.setText(title);
		setContentView(url, viewHolder.mBackgroundImageView);

	}
	
	private void setContentView(String url, ImageView mBackgroundImageView)
	{
		ZunniApplication.getmCacheManager().load(Uri.parse(url)).into(mBackgroundImageView, this);
	}

	@Override
	public SubCategoryProductViewHolder onCreateViewHolder(ViewGroup parent,
			int position) 
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sub_category_product, null);
		SubCategoryProductViewHolder viewHolder = new SubCategoryProductViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onError() {
		
	}

	@Override
	public void onSuccess() {
		
	}

	public void notifyListDataChanged(
			List<BeanSubCategoryProductForAdapter> getmBeanListForAdapter) 
	{
		this.mCategoryDetailBean = getmBeanListForAdapter;
		notifyDataSetChanged();
	}
}