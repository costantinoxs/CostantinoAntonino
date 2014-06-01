package it.cdpaf.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import it.cdpaf.entity.*;
import it.cdpaf.helper.*;
import it.cdpaf.R;
import it.cdpaf.R.id;
import it.cdpaf.R.layout;
import it.cdpaf.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class SearchActivity extends Activity {

	private ListProduct list;	
	private Handler handler;
	private Dialogs dialogs;
	private int range=20;
	private int offset=0;
	private Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx=this.getBaseContext();
		setContentView(R.layout.activity_search);
	
		
		TextView tvSearch = (TextView) findViewById(R.id.tvSearch);
		final EditText etSearch = (EditText) findViewById(R.id.etGenericSearch);
		Button btnSearch = (Button) findViewById(R.id.btnSearch);
		dialogs = new Dialogs();
		handler = new Handler() {
            @Override
            public void handleMessage(Message mess) {
            	int res = mess.arg1;
            	if(res==1){
            		Intent intent = new Intent(getBaseContext(), SearchListActivity.class);
            		intent.putExtra("PRODUCTLIST",(Parcelable) list);
            		intent.putExtra("search_item", etSearch.getText().toString());
            		intent.putExtra("mode",Const.SEARCHMODE);
            		intent.putExtra("search_prod_category", "");
                	startActivity(intent);
            	}
            	if(res==0){
            		AlertDialog dialog=dialogs.ProductNotFount(ctx);
            		dialog.show();
            	}
            }
		};
		
		
		
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				offset=0;
            	SearchData task = new SearchData();
				task.execute(etSearch.getText().toString());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_menu_item) {
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);
			finish();
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
				json.put("mode", 1);
				
				
				JSONArray array = connection.connectForCataalog("info_download_cf2", json,Const.CONNECTION_TIMEOUT,Const.SOCKET_TIMEOUT);
				
				JSONObject jObj = (JSONObject) array.get(0);
				JSONObject jObj2 = (JSONObject) array.get(1);
				Log.i("Secondo Prodotto",jObj2.toString(4));
				int res=Integer.parseInt(jObj.getString("result"));
				
				if(res==1){
					list=new ListProduct(array);
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
