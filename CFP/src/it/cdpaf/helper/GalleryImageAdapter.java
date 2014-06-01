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
    private int number;
    private String path;

    private Integer[] mImageIds = {
           R.drawable.blank,
           R.drawable.ic_launcher,
           R.drawable.arrow
    };

    public GalleryImageAdapter(Context context, int numberOfImage, String p) 
    {
        mContext = context;
        number=numberOfImage;
        path=p;
    }

    public int getCount() {
        return number;
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

		i=DrawableManager.fetchIvOnThread(path,position, mContext);
		    
        
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
		
	}
}