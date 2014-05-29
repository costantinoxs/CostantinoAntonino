package it.cdpaf.activity;

import it.cdpaf.R;
import it.cdpaf.R.id;
import it.cdpaf.R.layout;
import it.cdpaf.R.menu;
import it.cdpaf.R.string;
import it.cdpaf.entity.ListProduct;
import it.cdpaf.entity.Product;
import it.cdpaf.helper.Const;
import it.cdpaf.helper.DrawableManager;
import it.cdpaf.helper.GalleryImageAdapter;
import it.cdpaf.helper.GenericFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.app.Activity;
import android.app.ActionBar;
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
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

	
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
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
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
			Product prod = getArguments().getParcelable("PRODUCT");
			
			tvPrice.setText(GenericFunctions.currencyStamp(prod.getPrezzo()));
			tvProductor.setText(prod.getProduttore());
			tvDescription.setText(prod.getDescrizione());
			tvAvailability.setText(Integer.toString(prod.getQuantita()));
			
			Drawable d= prod.getImmagine();
			
			iv.setImageDrawable(d);
			iv.invalidate();
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
			Product prod = getArguments().getParcelable("PRODUCT");
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
			Product prod = getArguments().getParcelable("PRODUCT");
			
			ImageView ivGallery = (ImageView) rootView.findViewById(R.id.ivGallery);
			Gallery gallery = (Gallery) rootView.findViewById(R.id.gallery);
			gallery.setSpacing(1);
	        
			DrawableManager draw = new DrawableManager();
			ArrayList<Drawable> product_images=draw.fetchAllDrawableOnThread(prod.getPercorsoImmagine(), getActivity());
			
			gallery.setAdapter(new GalleryImageAdapter(getActivity()));
	        
	        
			
			
			return rootView;
		}
	}

}
