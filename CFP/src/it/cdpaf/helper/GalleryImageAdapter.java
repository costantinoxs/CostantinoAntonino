package it.cdpaf.helper;

import it.cdpaf.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter 
{
    private Context mContext;

    private Integer[] mImageIds = {
           R.drawable.blank,
           R.drawable.ic_launcher,
           R.drawable.arrow
    };

    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


 

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[position]);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
		
	}
}