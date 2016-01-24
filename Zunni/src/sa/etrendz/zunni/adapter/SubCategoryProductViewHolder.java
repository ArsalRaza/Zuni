package sa.etrendz.zunni.adapter;

import sa.etrendz.zunni.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SubCategoryProductViewHolder extends RecyclerView.ViewHolder
{

	public TextView mTitleTextView;
	public ImageView mBackgroundImageView;

	public SubCategoryProductViewHolder(View itemView) 
	{
		super(itemView);
		mTitleTextView = (TextView) itemView.findViewById(R.id.adapter_sub_category_product_title_textview);
		mBackgroundImageView = (ImageView) itemView.findViewById(R.id.adapter_sub_category_product_back_imgview);
	}
}