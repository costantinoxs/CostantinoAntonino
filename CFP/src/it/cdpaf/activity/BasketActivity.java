package it.cdpaf.activity;

import java.io.ByteArrayOutputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.cdpaf.R;
import it.cdpaf.R.id;
import it.cdpaf.R.layout;
import it.cdpaf.R.menu;
import it.cdpaf.helper.*;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.widget.ListPopupWindowCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.os.Build;


import it.cdpaf.entity.*;

public class BasketActivity extends Activity {
	
	private Handler handler;
	private Dialogs dialogs;
	private Context ctx;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx=this;
		setContentView(R.layout.activity_basket);
		
		//Const.basketProductList;
		
		dialogs=new Dialogs();
		  
		final ListView listView =(ListView) findViewById(R.id.listBasket);
		
		final ListProductBasketAdapter adapter = 
				new ListProductBasketAdapter(this,
				R.layout.product_basket_list_item, Const.basketProductList);
		
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), DetailActivity.class);
        		intent.putExtra("PRODUCT",(Parcelable) Const.basketProductList.get(position));
        		startActivity(intent);
     		}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				final AlertDialog dialogBox =dialogs.DeleteDialog(position,Const.basketProductList, ctx);
				dialogBox.show();
				
				Button deleteButton = dialogBox
						.getButton(DialogInterface.BUTTON_POSITIVE);
				deleteButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						try{
						
					    Const.basketProductList.remove(position);
						adapter.notifyDataSetChanged();
						
						//setTotalPrice();
						
						}catch (IndexOutOfBoundsException e){
							adapter.notifyDataSetChanged();
						}
						dialogBox.dismiss();	
					}
				});
				
				
				
				return false;
			}
		});
		
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_list, menu);
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

}	

