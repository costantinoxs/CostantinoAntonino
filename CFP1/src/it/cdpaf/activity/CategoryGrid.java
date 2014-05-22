package it.cdpaf.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.cdpaf.R;
import it.cdpaf.R.id;
import it.cdpaf.R.layout;
import it.cdpaf.R.menu;
import it.cdpaf.entity.Category;
import it.cdpaf.entity.ListCategories;
import it.cdpaf.entity.Macrocategory;
import it.cdpaf.helper.Const;
import it.cdpaf.helper.GridViewAdapter;
import it.cdpaf.helper.GridViewAdapterCategory;
import it.cdpaf.helper.HttpConnection;
import it.cdpaf.helper.ListProductSearchAdapter;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class CategoryGrid extends Activity {

	private GridView gridView;
	private Handler handler;
	
	private GridViewAdapterCategory adapter;
	ListCategories listCategories;
	Context ctx;
	private int idMacrocategory;
	private String Macrocategory;
	//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_fanta_search_category);
		listCategories = new ListCategories();
		ctx=this;
		Intent intent  = getIntent();
		idMacrocategory = intent.getIntExtra("idMacrocategory",0);
		Macrocategory = intent.getStringExtra("Macrocategory");
		
		
		//Log.i("SIZEEEEEE", listCategories.size()+"");
    	//Handler per il messaggio di risposta del Server, proveniente dal Thread.
		handler = new Handler() {
            @Override
            public void handleMessage(Message mess) {
            	
            	int res = mess.arg1;
               	
            	if(res==Const.TIMEOUT){
            		
            	}
                else {
                	showList();
                }
            }
		};
		
		requestMacrocategories task = new requestMacrocategories();
		task.execute();

	}
	
	 public void showList(){
	    	
	    	Log.i("SIZEEEEEE", listCategories.size()+"");
			gridView = (GridView) findViewById(R.id.gridMacroCategory);
			
			adapter = new GridViewAdapterCategory(ctx, R.id.gridMacroCategory, listCategories);
			gridView.setAdapter(adapter);
			
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					// TODO Auto-generated method stub
			        Log.i("Push", ""+arg2+" "+listCategories.get(arg2).getName());
//			        Intent intent = new Intent(getBaseContext(), SearchListActivity.class);
//            		intent.putExtra("Macrocategory",listCategories.get(arg2).getName());
//            		intent.putExtra("search_item", etSearch.getText().toString());
//                	startActivity(intent);
				}
			});
	    }
		
		public class requestMacrocategories extends AsyncTask<Void, Void, Void> {
			ProgressDialog progressDialog;

			@Override
			protected void onPreExecute() {
				//Creazione di un Dialog di attesa per il login
		     
			};

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
		 	}

			@Override
			protected Void doInBackground(Void...params) {
				

				
				try {
				HttpConnection connection = new HttpConnection();
					
				JSONObject json=new JSONObject();
				json.put("richiesta", "2");
				json.put("macrocategoria", idMacrocategory);
				JSONArray arrayObject = connection.connectForCatalog("gestioneCatalogoAppProva", json, handler,Const.CONNECTION_TIMEOUT,Const.SOCKET_TIMEOUT);
				listCategories = new ListCategories();			
				for (int i=0;i<arrayObject.length();i++){ 
					// Lettura dell'oggetto Json
					JSONObject obj= (JSONObject)arrayObject.get(i);
					String idCategory = obj.getString("idCategoria");
					String nome = obj.getString("nome");
					String nomeImmagine = obj.getString("nomeImmagine");
					Category tempCat;
					
					Log.i("idCategoria: ", idCategory);
					Log.i("nome: ", nome);
					
					tempCat= new Category(Integer.parseInt(idCategory), nome, nomeImmagine);
					
					listCategories.add(tempCat);
				}
				Log.i("listCategories: ","");
				for(int i =0;i<listCategories.size();i++){
					Category mTemp = listCategories.get(i);
					Log.i("listCategories: ", mTemp.print());
				}		
				
				Message message = handler.obtainMessage(1, Const.OK, 0);
				
				handler.sendMessage(message);
				
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection1: " + e.toString());
			}

			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search_item) {
			Intent intent = new Intent(getBaseContext(), SearchActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	
}
