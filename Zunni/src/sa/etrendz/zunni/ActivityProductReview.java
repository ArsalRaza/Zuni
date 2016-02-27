package sa.etrendz.zunni;

import java.util.List;

import sa.etrendz.zunni.adapter.AdapterProductReviewImageAdapter;
import sa.etrendz.zunni.asynctask.AsynctaskAddToCart;
import sa.etrendz.zunni.asynctask.AsynctaskGetProductDetail;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.bean.BeanProductForCategory;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

public class ActivityProductReview extends AppCompatActivity implements OnClickListener 
{
	private BeanProductDetail mProductBean;
	private TextView mPriceTextView;
	private TextView mCategoryTextView;
	private ViewPager mImageViewPager;
	private ImageView mInfoImageView;
	private LinearLayout mRelatedProductsLayout;
	private TextView mRelatedProductsTitleTextView;
	private TextView mAddToCartTextView;

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
		mRelatedProductsTitleTextView = (TextView) findViewById(R.id.activity_product_review_matching_title);
	
		//!-- Add to cart view
		mAddToCartTextView = (TextView) findViewById(R.id.shopping_cart_imgview);
		
		mAddToCartTextView.setOnClickListener(this);
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
	
		//!-- Setting related products --!//
		updateRelatedProducts(mProductDetailBean.getmRelatedProducts());
	}

	public void updateRelatedProducts(List<BeanProductForCategory> mBeanRelatedProducts) 
	{
		if (mBeanRelatedProducts != null && mBeanRelatedProducts.size() > 0)
		{
			mRelatedProductsTitleTextView.setVisibility(View.VISIBLE);
			
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mRelatedProductsLayout.removeAllViews();
			
			for (int i = 0; i < mBeanRelatedProducts.size(); i++)
			{
				View view = layoutInflater.inflate(R.layout.adapter_related_products, null);
				
				ImageView mProductImageView = (ImageView) view.findViewById(R.id.adapter_related_product_imageview);
				TextView mProductNameTextView = (TextView) view.findViewById(R.id.adapter_related_product_name);
				TextView mProductPriceTextView = (TextView) view.findViewById(R.id.adapter_related_product_price);
				
				setContentView(mProductImageView, mBeanRelatedProducts.get(i).getImageModel().getThumbImageUrl());
				mProductNameTextView.setText(mBeanRelatedProducts.get(i).getProductName());
				mProductPriceTextView.setText(mBeanRelatedProducts.get(i).getProductPrice().getPrice());
				
				view.setTag(mBeanRelatedProducts.get(i).getmProductId());
				view.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					public void onClick(View v)
					{
						//Related product
						Intent intent = new Intent(ActivityProductReview.this, ActivityProductReview.class);
						intent.putExtra("productId", (String) v.getTag());
						startActivity(intent);
					}
				});
				mRelatedProductsLayout.addView(view);
			}
		}
		else
		{
			mRelatedProductsTitleTextView.setVisibility(View.GONE);
		}
	}
	
    private void setContentView(ImageView imageView, String thumbImageUrl)
    {
		ZunniApplication.getmCacheManager().load(Uri.parse(thumbImageUrl)).fit().into(imageView);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mAddToCartTextView)
		{
			if (mProductBean != null)
				new AsynctaskAddToCart(ActivityProductReview.this, mProductBean.getmProductId(), "1", "1", null).execute();
			return;
		}
	}

	public void onAddToCartComplete()
	{
		Toast.makeText(this, "Congratulations! You sucessfully added this item to your shopping cart", Toast.LENGTH_LONG).show();
	}
}