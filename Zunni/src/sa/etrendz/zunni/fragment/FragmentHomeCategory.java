package sa.etrendz.zunni.fragment;

import java.util.List;

import sa.etrendz.zunni.R;
import sa.etrendz.zunni.adapter.AdapterViewPagerMainCategory;
import sa.etrendz.zunni.asynctask.AsyncTaskGetAllCategories;
import sa.etrendz.zunni.bean.BeanGetAllCategory;
import sa.etrendz.zunni.view.VerticalViewPager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHomeCategory extends Fragment 
{
    private List<BeanGetAllCategory> mCategoryListBean;
	private VerticalViewPager mVerticalViewPager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_home_category, null);
        InitUI(view);
        
        if (mCategoryListBean == null)
        {
        	new AsyncTaskGetAllCategories(getActivity(), this).execute();
        }
        else
        {
        	updateCategories(mCategoryListBean);
        }
		
		return view;
	}

	private void InitUI(View view) {
    	mVerticalViewPager = (VerticalViewPager) view.findViewById(R.id.vertcal_view_pager);
	}
	
	public void updateCategories(List<BeanGetAllCategory> mCategoryListBean2)
    {
		if (mCategoryListBean2 != null)
		{
			this.mCategoryListBean = mCategoryListBean2;
			
			if (mVerticalViewPager.getAdapter() != null)
			{
				((AdapterViewPagerMainCategory) mVerticalViewPager.getAdapter()).notifyDataSetChanged(mCategoryListBean2);
			}
			else
			{
				AdapterViewPagerMainCategory adapter = new AdapterViewPagerMainCategory(getActivity(), mCategoryListBean2);
				mVerticalViewPager.setAdapter(adapter);
			}
		}
    }
}