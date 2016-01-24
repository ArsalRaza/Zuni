package sa.etrendz.zunni.fragment;

import sa.etrendz.zunni.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentLeftSideMenu extends Fragment 
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_home_left, null);
		ListView mListView = (ListView) view.findViewById(R.id.home_left_side_listview);
		mListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"Arsal", "asd","asd"}));
		return view;
	}
}
