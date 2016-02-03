package sa.etrendz.zunni;

import sa.etrendz.zunni.adapter.SubCategoryAdapter;
import sa.etrendz.zunni.asynctask.AsynctaskGetCategoryDetail;
import sa.etrendz.zunni.bean.CategoryDetailBean;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ActivitySubCategory extends AppCompatActivity 
{
	private CategoryDetailBean mCategoryDetailBean;
	private String mCategoryId;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_category);
		
		setActionBar();
		
		mListView = (ListView) findViewById(R.id.activity_subcategory_listview);
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null)
		{
			mCategoryId = intent.getExtras().getString("categoryid");
		}
		
		if (mCategoryDetailBean != null)
		{
			updateCategories(mCategoryDetailBean);
		}
		else
		{
			new AsynctaskGetCategoryDetail(this, mCategoryId).execute();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setActionBar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_subcategory_product);
		setSupportActionBar(toolbar);
//		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

	public void updateCategories(CategoryDetailBean mCategoryDetailBean2) 
	{
		if (mCategoryDetailBean2 != null)
		{
			this.mCategoryDetailBean = mCategoryDetailBean2;
			if (mListView.getAdapter() == null)
			{
				SubCategoryAdapter adapter = new SubCategoryAdapter(this, mCategoryDetailBean2.getmBeanListForAdapter());
				mListView.setAdapter(adapter);
			}
			else
			{
				((SubCategoryAdapter) mListView.getAdapter()).notifyListDataChanged(mCategoryDetailBean2.getmBeanListForAdapter());
			}
		}
	}
}