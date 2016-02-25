package sa.etrendz.zunni.fragment;

import sa.etrendz.zunni.ActivityMainCategory;
import sa.etrendz.zunni.R;
import sa.etrendz.zunni.ZunniApplication;
import sa.etrendz.zunni.utils.ZunniConstants;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.andexert.library.RippleView.OnRippleCompleteListener;

public class FragmentLeftSideMenu extends Fragment implements OnRippleCompleteListener 
{
	private RippleView mInterestRippleView;
	private RippleView mCollectionRippleView;
	private RippleView mFeedRippleView;
	private TextView mFeedTextView;
	private TextView mCollectionTextView;
	private TextView mInterestTextView;
	private int mBlackColor;
	private int mPinkColor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_home_left, null);
		
		mBlackColor = ContextCompat.getColor(getActivity(), android.R.color.black);
		mPinkColor = ContextCompat.getColor(getActivity(), R.color.price_tag_pink);
		
		InitUI(view);
		return view;
	}

	private void InitUI(View view)
	{
		//!!---- Clickable TextViews
		mFeedRippleView = (RippleView) view.findViewById(R.id.frag_left_menu_myfeed_rpview);
		mInterestRippleView = (RippleView) view.findViewById(R.id.frag_left_menu_my_interest_rpview);
		mCollectionRippleView = (RippleView) view.findViewById(R.id.frag_left_menu_my_collection_rpview);
		
		mFeedTextView = (TextView) view.findViewById(R.id.frag_left_menu_myfeed_txtview);
		mInterestTextView = (TextView) view.findViewById(R.id.frag_left_menu_my_interest_txtview);
		mCollectionTextView = (TextView) view.findViewById(R.id.frag_left_menu_my_collection_txtview);
		
		//!!---- Setting UserName
		TextView mUserNameTextView = (TextView) view.findViewById(R.id.frag_left_username_txtview);
		mUserNameTextView.setText(ZunniApplication.getmAppPreferences().getString(ZunniConstants.USER_NAME_PREFS_KEY, "Zuni"));
		
		mFeedRippleView.setOnRippleCompleteListener(this);
		mInterestRippleView.setOnRippleCompleteListener(this);
		mCollectionRippleView.setOnRippleCompleteListener(this);
	}

	@Override
	public void onComplete(RippleView rippleView)
	{
		ActivityMainCategory slidingActivity = (ActivityMainCategory) getActivity();
		
		if (mCollectionRippleView == rippleView)
		{
			mCollectionTextView.setTextColor(mPinkColor);

			mInterestTextView.setTextColor(mBlackColor);
			mFeedTextView.setTextColor(mBlackColor);
			
			slidingActivity.toggle();
			return;
		}
		
		if (mInterestRippleView == rippleView)
		{
			mInterestTextView.setTextColor(mPinkColor);
			
			mCollectionTextView.setTextColor(mBlackColor);
			mFeedTextView.setTextColor(mBlackColor);
			slidingActivity.toggle();
			return;	
		}
		
		if (mFeedRippleView == rippleView)
		{
			mFeedTextView.setTextColor(mPinkColor);

			mCollectionTextView.setTextColor(mBlackColor);
			mInterestTextView.setTextColor(mBlackColor);
			slidingActivity.toggle();
			return;	
		}
	}
}