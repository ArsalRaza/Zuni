package sa.etrendz.zunni;

import java.util.List;

import sa.etrendz.zunni.adapter.AdapterProductReviewImageAdapter;
import sa.etrendz.zunni.asynctask.AsynctaskGetProductDetail;
import sa.etrendz.zunni.asynctask.AsynctaskGetRelatedProducts;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.bean.BeanProductForCategory;
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
	private BeanProductDetail mProductBean;
	private TextView mPriceTextView;
	private TextView mCategoryTextView;
	private ViewPager mImageViewPager;
	private ImageView mInfoImageView;
	private List<BeanProductForCategory> mRelatedProducts;
	private LinearLayout mRelatedProductsLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_review);
		initActionBar();
		InitUI();
		
		String mProductId = "";
		if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("productId"))
		{
			mProductId = getIntent().getStringExtra("productId");
		}
		else
		{
			finish();
		}
		new AsynctaskGetProductDetail(ActivityProductReview.this, mProductId).execute();
		new AsynctaskGetRelatedProducts(this, mProductId).execute();
	}

	@SuppressWarnings("deprecation")
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
	}

	private void InitUI()
	{
		mCategoryTextView = (TextView) findViewById(R.id.activity_product_review_category_name);
		mPriceTextView = (TextView) findViewById(R.id.activity_product_review_price);
		mInfoImageView = (ImageView) findViewById(R.id.activity_product_review_info);
		mImageViewPager = (ViewPager) findViewById(R.id.activity_product_review_viewpager);
		mRelatedProductsLayout = (LinearLayout) findViewById(R.id.activity_product_review_similar_products);
	}
	
	public void onProductDetailsObtained(BeanProductDetail mProductDetailBean) 
	{
		if (mProductDetailBean == null)
		{
			return;
		}
		this.mProductBean = mProductDetailBean;
		mPriceTextView.setText(String.format(getString(R.string.price_with_format), mProductBean.getProductPrice().getPrice()));
		
		String categoryNameString = ZunniApplication.getmAppPreferences().getString(ZunniConstants.SELECTED_CATEGORY_NAME, "");
		mCategoryTextView.setText(categoryNameString);
		
		if (mImageViewPager.getAdapter() == null)
			mImageViewPager.setAdapter(new AdapterProductReviewImageAdapter(this, mProductDetailBean.getmProductImageModel()));
		else
			((AdapterProductReviewImageAdapter) mImageViewPager.getAdapter()).notifyListChanged(mProductDetailBean.getmProductImageModel());

		getSupportActionBar().setTitle(mProductBean.getmProductName());
	}

	public void updateRelatedProducts(List<BeanProductForCategory> mBeanRelatedProducts) 
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