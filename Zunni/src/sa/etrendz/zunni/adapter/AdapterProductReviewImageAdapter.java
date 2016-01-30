package sa.etrendz.zunni.adapter;

import java.util.ArrayList;

import sa.etrendz.zunni.ActivityFullScreenImage;
import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.bean.BeanServerImage;
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

public class AdapterProductReviewImageAdapter extends PagerAdapter implements OnClickListener
{

	private Activity mActivity;
	private ArrayList<BeanServerImage> mImageBeanList;
	private LayoutInflater mLayoutInflater;

	public AdapterProductReviewImageAdapter(
			Activity activityProductReview,
			ArrayList<BeanServerImage> mImageBeanList)
	{
		this.mActivity = activityProductReview;
		this.mImageBeanList = mImageBeanList;
		mLayoutInflater = (LayoutInflater) activityProductReview.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mImageBeanList.size();
	}

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ViewGroup) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container,final int position)
    {
    	View view = mLayoutInflater.inflate(R.layout.adapter_product_review_image, null);
    	ImageView imageView = (ImageView) view.findViewById(R.id.adapter_product_review_imageview);
    	
    	setContentView(imageView, mImageBeanList.get(position).getmFullSizeImageUrl());
    	
    	imageView.setTag(mImageBeanList.get(position).getmFullSizeImageUrl());
    	imageView.setOnClickListener(this);
		
    	((ViewPager) container).addView(view);
		return view;
    }
    
    private void setContentView(ImageView imageView, String thumbImageUrl) {
		ZunniApplication.getmCacheManager().load(Uri.parse(thumbImageUrl)).fit().into(imageView);
	}

	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }

    @Override
    public int getItemPosition(Object object) 
    {
    	return POSITION_NONE;
    }
	
    public void notifyListChanged(ArrayList<BeanServerImage> mUriList)
	{
		mImageBeanList = mUriList;
		notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) 
	{
		Intent intent = new Intent(mActivity, ActivityFullScreenImage.class);
		intent.putExtra("image-url", ((String) v.getTag()));
		mActivity.startActivity(intent);
	}
}