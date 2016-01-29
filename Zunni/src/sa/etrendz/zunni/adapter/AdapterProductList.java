package sa.etrendz.zunni.adapter;

import java.util.List;

import sa.etrendz.zunni.ActivityProductReview;
import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.bean.BeanProductDetail;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterProductList extends BaseAdapter implements OnClickListener {

	private List<BeanProductDetail> mList;
	private Activity mActivity;
	private LayoutInflater mLayoutInflater;

	public AdapterProductList(Activity activityProductList,
			List<BeanProductDetail> mAllProductsList2) 
	{
		this.mActivity = activityProductList;
		this.mList = mAllProductsList2;
		this.mLayoutInflater = (LayoutInflater) activityProductList.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (convertView == null)
		{
			convertView = mLayoutInflater.inflate(R.layout.adapter_product_list, null);
		}
		
		ImageView mBackgroundImageView = (ImageView) convertView.findViewById(R.id.adapter_product_first_imageview);
		TextView mTitleTextView = (TextView) convertView.findViewById(R.id.adapter_product_first_textview);
		
		setContentView(mList.get(position).getImageModel().getThumbImageUrl(), mBackgroundImageView);
		mTitleTextView.setText(mList.get(position).getProductName());
		
		convertView.setTag(position);
		convertView.setOnClickListener(this);
		
		return convertView;
	}
	
	private void setContentView(String url, ImageView mFirstImageView) 
	{
		ZunniApplication.getmCacheManager().load(Uri.parse(url)).into(mFirstImageView);
	}
	
	public void notifyListChanged(List<BeanProductDetail> mAllProductsList2) {
		this.mList = mAllProductsList2;
		notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) 
	{
		Integer tag = (Integer) v.getTag();
		Intent intent = new Intent(mActivity, ActivityProductReview.class);
		ActivityProductReview.mProductBean = mList.get(tag);
		
		mActivity.startActivity(intent);
	}
}