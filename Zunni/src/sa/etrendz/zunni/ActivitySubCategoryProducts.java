package sa.etrendz.zunni;

import sa.etrendz.zunni.adapter.SubCategoryProductAdapter;
import sa.etrendz.zunni.asynctask.AsynctaskGetCategoryDetail;
import sa.etrendz.zunni.bean.CategoryDetailBean;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class ActivitySubCategoryProducts extends AppCompatActivity 
{
//	private ListView mListView;
	private CategoryDetailBean mCategoryDetailBean;
	private String mCategoryId;

	private RecyclerView mRecyclerView;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_category_products);
		
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
	
	private void setActionBar() 
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_subcategory_product);
		setSupportActionBar(toolbar);
	}

	public void updateCategories(CategoryDetailBean mCategoryDetailBean2) 
	{
		if (mCategoryDetailBean2 != null)
		{
			this.mCategoryDetailBean = mCategoryDetailBean2;
			if (mListView.getAdapter() == null)
			{
				SubCategoryProductAdapter adapter = new SubCategoryProductAdapter(this, mCategoryDetailBean2.getmBeanListForAdapter());
				mListView.setAdapter(adapter);
			}
			else
			{
				((SubCategoryProductAdapter) mListView.getAdapter()).notifyListDataChanged(mCategoryDetailBean2.getmBeanListForAdapter());
			}
		}
	}
}
