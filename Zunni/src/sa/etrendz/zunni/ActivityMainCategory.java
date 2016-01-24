package sa.etrendz.zunni;

import sa.etrendz.zunni.fragment.FragmentHomeCategory;
import sa.etrendz.zunni.fragment.FragmentLeftSideMenu;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class ActivityMainCategory extends AppCompatActivity implements OnClickListener
{

	private FragmentManager mFragmentManager;
//	private Fragment mCurrentFragment;
	private FragmentHomeCategory mHomeFragment;
	private FragmentLeftSideMenu mLeftSideFragment;
	private FrameLayout mLeftSideContainer;
//	private FrameLayout mMainContainer;
	private DrawerLayout mDrawerLayout;
	private ImageView mNavToggleButton;
	private boolean mIsOpened = false;

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        mFragmentManager = getSupportFragmentManager();

        InitUI();
    }

	private void InitUI() 
    {
		setActionBar();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setScrimColor(Color.TRANSPARENT);
		
		if (mHomeFragment == null)
			mHomeFragment = new FragmentHomeCategory();
		
		if (mLeftSideFragment == null)
			mLeftSideFragment = new FragmentLeftSideMenu();
		
		mLeftSideContainer = (FrameLayout) findViewById(R.id.left_container);
    
		mFragmentManager.beginTransaction().replace(R.id.main_container, mHomeFragment).commitAllowingStateLoss();
		mFragmentManager.beginTransaction().replace(R.id.left_container, mLeftSideFragment).commitAllowingStateLoss();
	}
	
	private void setActionBar()
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		mNavToggleButton = (ImageView) toolbar.findViewById(R.id.burger_imageview);
		setSupportActionBar(toolbar);
		
		ActionBar supportToolBar = getSupportActionBar();
		supportToolBar.setDisplayShowTitleEnabled(false);
	
		mNavToggleButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.burger_imageview:
			toggle();
			break;

		}
	}

	public void toggle() 
	{
		if (mIsOpened)
		{
			mDrawerLayout.closeDrawers();
			mIsOpened = false;
		}
		else
		{
			mDrawerLayout.openDrawer(mLeftSideContainer);
			mIsOpened = true;
		}
	}
}