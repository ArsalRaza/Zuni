package sa.etrendz.zunni;

import java.util.List;

import sa.etrendz.zunni.adapter.AdapterProductList;
import sa.etrendz.zunni.asynctask.AsynctaskGetCategoryDetail;
import sa.etrendz.zunni.bean.BeanProductDetail;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

public class ActivityProductList extends AppCompatActivity 
{
	private GridView mGridView;
	private String mCategoryId;
	private List<BeanProductDetail> mAllProductsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_list);
		
		initActionBar();
		mGridView = (GridView) findViewById(R.id.activity_product_list_gridview);
		
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null)
		{
			mCategoryId = intent.getExtras().getString("categoryid");
		}
				
		if (mAllProductsList != null)
		{
			updateProducts(mAllProductsList);
		}
		else
		{
			new AsynctaskGetCategoryDetail(ActivityProductList.this, mCategoryId).execute();
		}
	}

	@SuppressWarnings("deprecation")
	private void initActionBar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
		supportToolBar.setTitle(ZunniApplication.getmAppPreferences().getString(ZunniConstants.SELECTED_CATEGORY_NAME, ""));
	}
	
	public void updateProducts(List<BeanProductDetail> mAllProductsList2) 
	{
		if (mAllProductsList2 == null)
			return;
		
		this.mAllProductsList = mAllProductsList2;
		if (mGridView.getAdapter() != null)
		{
			//notify Data Changed
			((AdapterProductList) mGridView.getAdapter()).notifyListChanged(mAllProductsList2);
		}
		else
		{
			AdapterProductList adapter = new AdapterProductList(ActivityProductList.this, mAllProductsList2);
			mGridView.setAdapter(adapter);
		}
	}
}