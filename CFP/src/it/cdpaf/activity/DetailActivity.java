package it.cdpaf.activity;

import it.cdpaf.R;
import it.cdpaf.R.id;
import it.cdpaf.R.layout;
import it.cdpaf.R.menu;
import it.cdpaf.R.string;
import it.cdpaf.activity.SearchActivity.SearchData;
import it.cdpaf.entity.ListProduct;
import it.cdpaf.entity.Product;
import it.cdpaf.helper.Const;
import it.cdpaf.helper.Dialogs;
import it.cdpaf.helper.DrawableManager;
import it.cdpaf.helper.GalleryImageAdapter;
import it.cdpaf.helper.GenericFunctions;
import it.cdpaf.helper.HttpConnection;
import it.cdpaf.helper.ListProductSearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	Product product;
	// list contains fragments to instantiate in the viewpager
	   List<Fragment> fragments;
	   DrawableManager drawableM;
	   static Handler handler;
	   static Dialogs dialogs;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		dialogs= new Dialogs();
		drawableM= new DrawableManager();
		Intent intent = getIntent();
		product  = (Product) intent.getParcelableExtra("PRODUCT");
		
		String perc =Const.IMAGE_URL+product.getPercorsoImmagine();
		Log.i("PERCORSO IMG",perc);
		
		Drawable img = drawableM.returnDrawable(perc, this);
		
		product.setImmagine(img);
		
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basket, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_basket_item) {
			Intent intent = new Intent(getBaseContext(), BasketActivity.class);
			startActivity(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}

	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			if (position==0)
			return DetailFragmentOne.newInstance(product);
			else if (position==1)
			return 	DetailFragmentTwo.newInstance(product);
			else
			return 	DetailFragmentThree.newInstance(product);
				
			
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
				case 0 :
					return getString(R.string.title_section1).toUpperCase(l);
				case 1 :
					return getString(R.string.title_section2).toUpperCase(l);
				case 2 :
					return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DetailFragmentOne extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static DetailFragmentOne newInstance(Product prod) {
			DetailFragmentOne fragment = new DetailFragmentOne();
			Bundle args = new Bundle();
			args.putParcelable("PRODUCT", prod);
				fragment.setArguments(args);
			return fragment;
		}

		public DetailFragmentOne() {
		}
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail_one,
					container, false);
			
			TextView tvPrice = (TextView) rootView.findViewById(R.id.tvPrice);
			TextView tvProductor= (TextView) rootView.findViewById(R.id.tvProductor);
			TextView tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
			TextView tvAvailability = (TextView) rootView.findViewById(R.id.tvAvailability);
			ImageView iv = (ImageView) rootView.findViewById(R.id.ivFirstPhoto);
			
			Button buttonMinus = (Button) rootView.findViewById(R.id.buttonMinus);
			EditText etQuant = (EditText) rootView.findViewById(R.id.etQuantitative);
			Button ButtonPlus = (Button) rootView.findViewById(R.id.buttonPlus);
			Button buttonAddToBasket = (Button) rootView.findViewById(R.id.buttonAddToBasket);
			
			
			final Product prod = getArguments().getParcelable("PRODUCT");
			
			tvPrice.setText(GenericFunctions.currencyStamp(prod.getPrezzo()));
			tvProductor.setText(prod.getProduttore());
			tvDescription.setText(prod.getDescrizione());
			tvAvailability.setText(Integer.toString(prod.getQuantita()));
			
			Drawable d= prod.getImmagine();
			
			iv.setImageDrawable(d);
			iv.invalidate();
			
			buttonAddToBasket.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Const.basketProductList.add(prod);
				}
			});
			return rootView;
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DetailFragmentTwo extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static DetailFragmentTwo newInstance(Product prod) {
			DetailFragmentTwo fragment = new DetailFragmentTwo();
			Bundle args = new Bundle();
			args.putParcelable("PRODUCT", prod);
			fragment.setArguments(args);
			return fragment;
		}

		public DetailFragmentTwo() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail_two,
					container, false);
			Product productTwo = getArguments().getParcelable("PRODUCT");
			
			WebView wvIcecat = (WebView) rootView.findViewById(R.id.webView);
			
			// lets assume we have /assets/style.css file
			String mime = "text/html";
			String encoding = "utf-8";
			Log.i("ICECAT ",productTwo.getIcecat());
			String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + productTwo.getIcecat();
			
			wvIcecat.loadDataWithBaseURL("file:///android_asset/", htmlData , mime, encoding, null);
			return rootView;
		}
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DetailFragmentThree extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static DetailFragmentThree newInstance(Product prod) {
			DetailFragmentThree fragment = new DetailFragmentThree();
			Bundle args = new Bundle();
			args.putParcelable("PRODUCT", prod);
			fragment.setArguments(args);
			return fragment;
		}

		public DetailFragmentThree() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail_three,
					container, false);
			final Product prod = getArguments().getParcelable("PRODUCT");
			
			final ImageView ivGallery = (ImageView) rootView.findViewById(R.id.ivGallery);
			final Gallery gallery = (Gallery) rootView.findViewById(R.id.gallery);
			gallery.setSpacing(1);
	        
			DrawableManager draw = new DrawableManager();
			
			gallery.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					ivGallery.setImageDrawable(DrawableManager.fetchDrawableFromMap(prod.getPercorsoImmagine(), position, getActivity()));
				}
			});
			ivGallery.setImageDrawable(prod.getImmagine());
    		
			SearchPhoto task = new SearchPhoto();
			task.execute(prod.getPercorsoImmagine());
			
			handler = new Handler() {
	            @Override
	            public void handleMessage(Message mess) {
	            	int res = mess.arg1;
	            	if (res==0){
	            		AlertDialog dialog=dialogs.ProductNotFount(getActivity());
	            		dialog.show();
	            		
	            	}
	            	else{
	            		final GalleryImageAdapter adaptern = 
	            				new GalleryImageAdapter(getActivity(),res,prod.getPercorsoImmagine());
	            		gallery.setAdapter(adaptern);
	            		
	            	}	
	            	
	            }
			};
	        
	        
			
			
			return rootView;
		}
		public class SearchPhoto extends AsyncTask<String, Void, Void> {
			
			@Override
			protected void onPreExecute() {
			};

			@Override
			protected void onPostExecute(Void result) {
			}

			@Override
			protected Void doInBackground(String... params) {
				String strToSearch = params[0];
				JSONObject json = new JSONObject();
				try {
					HttpConnection connection = new HttpConnection();
					
					json.put("photo", strToSearch);
									
					JSONObject jObj = connection.connect("info_photo_fake", json,Const.CONNECTION_TIMEOUT,Const.SOCKET_TIMEOUT);
					
					
					int numberOfPhotoToDownload=Integer.parseInt(jObj.getString("result"));
					
					Message message = handler.obtainMessage(1, numberOfPhotoToDownload, 0);
					handler.sendMessage(message);
					
						
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					

				return null;
			};
		}
		
	}


}
