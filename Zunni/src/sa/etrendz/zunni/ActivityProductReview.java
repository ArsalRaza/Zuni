package sa.etrendz.zunni;

import java.util.ArrayList;
import java.util.List;

import sa.etrendz.zunni.adapter.AdapterProductReviewImageAdapter;
import sa.etrendz.zunni.asynctask.AsynctaskGetRelatedProducts;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.bean.BeanServerImage;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityProductReview extends AppCompatActivity 
{
	public static BeanProductDetail mProductBean;
	private TextView mPriceTextView;
	private TextView mCategoryTextView;
	private ViewPager mImageViewPager;
	private ImageView mInfoImageView;
	private List<BeanProductDetail> mRelatedProducts;
	private LinearLayout mRelatedProductsLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_review);
		initActionBar();
		InitUI();
		
		new AsynctaskGetRelatedProducts(this, mProductBean.getmProductId()).execute();
	}

	private void initActionBar() 
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_product_review);
		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backbutton_actionbar));

		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		ActionBar supportToolBar = getSupportActionBar();
		supportToolBar.setDisplayShowTitleEnabled(true);
		supportToolBar.setTitle(mProductBean.getProductName());
	}

	private void InitUI()
	{
		mCategoryTextView = (TextView) findViewById(R.id.activity_product_review_category_name);
		mPriceTextView = (TextView) findViewById(R.id.activity_product_review_price);
		mInfoImageView = (ImageView) findViewById(R.id.activity_product_review_info);
		mImageViewPager = (ViewPager) findViewById(R.id.activity_product_review_viewpager);
		mRelatedProductsLayout = (LinearLayout) findViewById(R.id.activity_product_review_similar_products);
		
		
		//Now setting data
		if (mProductBean != null)
		{
			mPriceTextView.setText(String.format(getString(R.string.price_with_format), mProductBean.getProductPrice().getPrice()));
		
			String categoryNameString = ZunniApplication.getmAppPreferences().getString(ZunniConstants.SELECTED_CATEGORY_NAME, "");
			mCategoryTextView.setText(categoryNameString);
			
			ArrayList<BeanServerImage> mImageBeanList = new ArrayList<BeanServerImage>();
			mImageBeanList.add(mProductBean.getImageModel());
			
			if (mImageViewPager.getAdapter() == null)
				mImageViewPager.setAdapter(new AdapterProductReviewImageAdapter(this, mImageBeanList));
			else
				((AdapterProductReviewImageAdapter) mImageViewPager.getAdapter()).notifyListChanged(mImageBeanList);
		}
		else
		{
			
		}
	}

	public void updateRelatedProducts(List<BeanProductDetail> mBeanRelatedProducts) 
	{
		if (mBeanRelatedProducts != null)
		{
			this.mRelatedProducts = mBeanRelatedProducts;
			
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mRelatedProductsLayout.removeAllViews();
			
			for (int i = 0; i < mRelatedProducts.size(); i++)
			{
				View view = layoutInflater.inflate(R.layout.adapter_related_products, null);
				
				ImageView mProductImageView = (ImageView) view.findViewById(R.id.adapter_related_product_imageview);
				TextView mProductNameTextView = (TextView) view.findViewById(R.id.adapter_related_product_name);
				TextView mProductPriceTextView = (TextView) view.findViewById(R.id.adapter_related_product_price);
				
				setContentView(mProductImageView, mRelatedProducts.get(i).getImageModel().getThumbImageUrl());
				mProductNameTextView.setText(mRelatedProducts.get(i).getProductName());
				mProductPriceTextView.setText(mRelatedProducts.get(i).getProductPrice().getPrice());
				
				view.setTag(i);
				view.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					public void onClick(View v)
					{
						//Related product
						
					}
				});
				
				mRelatedProductsLayout.addView(view);
			}
		}
	}
	
    private void setContentView(ImageView imageView, String thumbImageUrl) {
		ZunniApplication.getmCacheManager().load(Uri.parse(thumbImageUrl)).fit().into(imageView);
	}
}