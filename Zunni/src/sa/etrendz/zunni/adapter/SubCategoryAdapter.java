package sa.etrendz.zunni.adapter;

import java.util.List;

import sa.etrendz.zunni.ActivityProductList;
import sa.etrendz.zunni.ActivitySubCategory;
import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.bean.BeanSubCategoryProductForAdapter;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

public class SubCategoryAdapter extends BaseAdapter implements Callback, OnClickListener {

	private List<BeanSubCategoryProductForAdapter> mCategoryDetailBean;
	private Activity mActivity;
	private LayoutInflater mLayoutInflater;
	private int mHeight;

	@SuppressWarnings("deprecation")
	public SubCategoryAdapter(Activity activitySubCategoryProducts, List<BeanSubCategoryProductForAdapter> mCategoryDetailBean) 
	{
		this.mActivity = activitySubCategoryProducts;
		mLayoutInflater = (LayoutInflater) activitySubCategoryProducts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mCategoryDetailBean = mCategoryDetailBean;
		
		WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		this.mHeight = display.getHeight() / 2;
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
			convertView = mLayoutInflater.inflate(R.layout.adapter_sub_category, parent, false);
			ViewGroup.LayoutParams params = convertView.getLayoutParams();
			params.height = mHeight;
			convertView.setLayoutParams(params);
		}
		
		ImageView mBackgroundImageView = (ImageView) convertView.findViewById(R.id.adapter_sub_category_product_back_imgview);
		
		TextView mTitleTextView = (TextView) convertView.findViewById(R.id.adapter_sub_category_product_title_textview);
		TextView mCategoryNameTextView = (TextView) convertView.findViewById(R.id.adapter_sub_category_title_textview);
		
		BeanSubCategoryProductForAdapter mAdapterBean = mCategoryDetailBean.get(position);
		
		String url = "", title = "";
		if (mAdapterBean.ismIsSubCategoryObject())
		{
			// For Sub Category
			BeanGetAllCategory bean = (BeanGetAllCategory) mAdapterBean.getmBean();
			url = bean.getImageModel().getThumbImageUrl();
			title = bean.getmCategoryName()/* + "(Category)"*/;
		}
		
		mCategoryNameTextView.setText(ZunniApplication.getmAppPreferences().getString(ZunniConstants.SELECTED_CATEGORY_NAME, ""));
		mTitleTextView.setText(title);
		setContentView(url, mBackgroundImageView);
		convertView.setTag(position);
		
		convertView.setOnClickListener(this);
		return convertView;
	}
	
	private void setContentView(String url, ImageView mFirstImageView) 
	{
		ZunniApplication.getmCacheManager().load(Uri.parse(url)).fit().into(mFirstImageView, this);
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

	@Override
	public void onClick(View v) 
	{
		Integer tag = (Integer) v.getTag();
		BeanGetAllCategory beanGetAllCategory = ((BeanGetAllCategory) mCategoryDetailBean.get(tag).getmBean());
		if (beanGetAllCategory.getmHasSubCategory().equalsIgnoreCase("true"))
		{
			Intent intent = new Intent(mActivity, ActivitySubCategory.class);
			intent.putExtra("categoryid", beanGetAllCategory.getmCategoryId());
			mActivity.startActivity(intent);
		}
		else
		{
			Editor editor = ZunniApplication.getmAppPrefEditor();
			editor.putString(ZunniConstants.SELECTED_CATEGORY_NAME, beanGetAllCategory.getmCategoryName());
			editor.commit();
			
			Intent intent = new Intent(mActivity, ActivityProductList.class);
			intent.putExtra("categoryid", beanGetAllCategory.getmCategoryId());
			mActivity.startActivity(intent);
		}
	}
}