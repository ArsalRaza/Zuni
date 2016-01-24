package sa.etrendz.zunni.adapter;

import java.util.List;

import sa.etrendz.zunni.ActivitySubCategoryProducts;
import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.bean.BeanGetAllCategory.PictureModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;

public class AdapterViewPagerMainCategory extends PagerAdapter implements Callback, OnClickListener {

	private Activity mActivity;
	private List<BeanGetAllCategory> mCategoryList;
	private LayoutInflater mInflater;

	public AdapterViewPagerMainCategory(Activity activityMainCategory, List<BeanGetAllCategory> mCategoryListBean2)
	{
		mActivity = activityMainCategory;
		mCategoryList = mCategoryListBean2;
		mInflater = (LayoutInflater) activityMainCategory.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}
	public void notifyDataSetChanged(List<BeanGetAllCategory> beanGetAllCategories)
	{
		this.mCategoryList = beanGetAllCategories;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() 
	{
		return mCategoryList.size() / 3;
	}

	@Override
	public boolean isViewFromObject(View view, Object layout) {
		return view == ((LinearLayout) layout);
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) 
	{
		((ViewPager) container).removeView((LinearLayout)object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		View view = mInflater.inflate(R.layout.adapter_view_pager_main_category, null);
		
		ImageView mFirstImageView = (ImageView) view.findViewById(R.id.adapter_category_first_imageview);
		ImageView mSecondImageView = (ImageView) view.findViewById(R.id.adapter_category_sec_imageview);
		ImageView mThirdImageView = (ImageView) view.findViewById(R.id.adapter_category_third_imageview);
		
		TextView mFirstTextView = (TextView) view.findViewById(R.id.adapter_category_first_textview);
		TextView mSecondTextView = (TextView) view.findViewById(R.id.adapter_category_sec_textview);
		TextView mThirdTextView = (TextView) view.findViewById(R.id.adapter_category_third_textview);
		
		RelativeLayout mFirstLayout = (RelativeLayout) view.findViewById(R.id.adapter_category_first_image_layout);
		RelativeLayout mSecondLayout = (RelativeLayout) view.findViewById(R.id.adapter_category_sec_image_layout);
		RelativeLayout mThirdLayout = (RelativeLayout) view.findViewById(R.id.adapter_category_third_image_layout);
	
		int firstBean = position;
		int secondBean = position + 1;
		int thirdBean = position + 2;
		
		int beanSize = mCategoryList.size();
		
		if (firstBean < beanSize)
		{
			BeanGetAllCategory beanGetAllCategory = mCategoryList.get(firstBean);
			setContentView(mFirstImageView, beanGetAllCategory.getImageModel());
			mFirstTextView.setText(beanGetAllCategory.getmCategoryName());
			
			mFirstLayout.setTag(firstBean);
		}
		
		if (secondBean < beanSize)
		{
			BeanGetAllCategory beanGetAllCategory = mCategoryList.get(secondBean);
			setContentView(mSecondImageView, beanGetAllCategory.getImageModel());
			mSecondTextView.setText(beanGetAllCategory.getmCategoryName());
		
			mSecondLayout.setTag(secondBean);
		}
		else
		{
			mSecondLayout.setVisibility(View.GONE);
		}
		
		if (thirdBean < beanSize)
		{
			BeanGetAllCategory beanGetAllCategory = mCategoryList.get(thirdBean);
			setContentView(mThirdImageView, beanGetAllCategory.getImageModel());
			mThirdTextView.setText(beanGetAllCategory.getmCategoryName());
			
			mThirdLayout.setTag(thirdBean);
		}
		else
		{
			mThirdLayout.setVisibility(View.GONE);
		}
		
		mFirstLayout.setOnClickListener(this);
		mSecondLayout.setOnClickListener(this);
		mThirdLayout.setOnClickListener(this);
		
		((ViewPager) container).addView(view);
		return view;
	}

	private void setContentView(ImageView mFirstImageView, PictureModel imageModel) 
	{
		String mimeUrl = imageModel.getThumbImageUrl();
		ZunniApplication.getmCacheManager().load(Uri.parse(mimeUrl)).into(mFirstImageView, this);
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
		Intent intent = new Intent(mActivity, ActivitySubCategoryProducts.class);
		intent.putExtra("categoryid", mCategoryList.get(tag).getmCategoryId());
		mActivity.startActivity(intent);
	}
}