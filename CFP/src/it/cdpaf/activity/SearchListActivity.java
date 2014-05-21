package it.cdpaf.activity;

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
import android.content.Intent;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.os.Build;


import it.cdpaf.entity.*;
public class SearchListActivity extends Activity {
	private int range=20;
	private int offset=0;
	private Handler handler;
	private Dialogs dialogs;
	private Context ctx;
	private String search_item;
	
	ListProduct productList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx=this;
		setContentView(R.layout.activity_search_list);
		
		Intent intent = getIntent();
		search_item=intent.getStringExtra("searc_item");
		productList  = (ListProduct) intent.getParcelableExtra("PRODUCTLIST");
		productList.print("List");
		  
		final ListView listView =(ListView) findViewById(R.id.list);
		
		final ListProductSearchAdapter adapter = 
				new ListProductSearchAdapter(this,
				R.layout.product_choice_list_item, productList);
		
		
		View footerView = ((LayoutInflater)this
				.getSystemService(this.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.listfooter, null, false);
        listView.addFooterView(footerView);
		
		footerView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	System.gc();
            	offset=offset+20;
            	SearchData task = new SearchData();
				task.execute(search_item);
            }

        });
		handler = new Handler() {
            @Override
            public void handleMessage(Message mess) {
            	int res = mess.arg1;
            	if(res==1){
            		final ListProductSearchAdapter adaptern = 
            				new ListProductSearchAdapter(ctx,
            				R.layout.product_choice_list_item, productList);
            		listView.setAdapter(adaptern);
            	}
            	if(res==0){
            		
            	}
            }
		};
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

	

public class SearchData extends AsyncTask<String, Void, Void> {
		
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
				
				json.put("search", strToSearch);
				json.put("offset", offset);
				json.put("range", range);
				
				
				JSONArray array = connection.connectForCataalog("info_download_cf", json,Const.CONNECTION_TIMEOUT,Const.SOCKET_TIMEOUT);
				
				JSONObject jObj = (JSONObject) array.get(0);
				int res=Integer.parseInt(jObj.getString("result"));
				
				if(res==1){
					productList=new ListProduct(array);
					Message message = handler.obtainMessage(1, res, 0);
					handler.sendMessage(message);
				}else{
					Message message = handler.obtainMessage(1, res, 0);
					handler.sendMessage(message);
				}
					
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				

			return null;
		};
	}
}
