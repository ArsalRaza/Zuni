package sa.etrendz.zunni.adapter;

import java.util.List;

import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.bean.BeanSubCategoryProductForAdapter;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

public class SubCategoryProductAdapter extends BaseAdapter implements Callback {

	private List<BeanSubCategoryProductForAdapter> mCategoryDetailBean;
	private Activity mActivity;
	private LayoutInflater mLayoutInflater;

	public SubCategoryProductAdapter(Activity activitySubCategoryProducts, List<BeanSubCategoryProductForAdapter> mCategoryDetailBean) 
	{
		this.mActivity = activitySubCategoryProducts;
		mLayoutInflater = (LayoutInflater) activitySubCategoryProducts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mCategoryDetailBean = mCategoryDetailBean;
	}

	@Override
	public int getCount() 
	{
		return mCategoryDetailBean.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return mCategoryDetailBean.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView == null)
		{
			convertView = mLayoutInflater.inflate(R.layout.adapter_sub_category_product, null);
		}
		
		ImageView mBackgroundImageView = (ImageView) convertView.findViewById(R.id.adapter_sub_category_product_back_imgview);
		TextView mTitleTextView = (TextView) convertView.findViewById(R.id.adapter_sub_category_product_title_textview);
		
		BeanSubCategoryProductForAdapter mAdapterBean = mCategoryDetailBean.get(position);
		String url = "", title = "";
		if (mAdapterBean.ismIsSubCategoryObject())
		{
			// For Sub Category
			BeanGetAllCategory bean = (BeanGetAllCategory) mAdapterBean.getmBean();
			url = bean.getImageModel().getThumbImageUrl();
			title = bean.getmCategoryName() + "(Category)";
		}
		else
		{
			// For Product
			BeanProductDetail bean = (BeanProductDetail) mAdapterBean.getmBean();
			url = bean.getImageModel().getThumbImageUrl();
			title = bean.getProductName() + "(Product)";
		}
		
		mTitleTextView.setText(title);
		setContentView(url, mBackgroundImageView);
		
		return convertView;
	}
	
	private void setContentView(String url, ImageView mFirstImageView) 
	{
		ZunniApplication.getmCacheManager().load(Uri.parse(url)).into(mFirstImageView, this);
	}
	
	public void notifyListDataChanged(List<BeanSubCategoryProductForAdapter> mCategoryDetailBean2) {
		this.mCategoryDetailBean = mCategoryDetailBean2;
		notifyDataSetChanged();
	}

	@Override
	public void onError() 
	{
		
	}

	@Override
	public void onSuccess() 
	{
		
	}
}