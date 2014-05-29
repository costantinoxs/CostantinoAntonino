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
import it.cdpaf.entity.ListProduct;
import it.cdpaf.helper.Const;
import it.cdpaf.helper.GridViewAdapterCategory;
import it.cdpaf.helper.HttpConnection;
import it.cdpaf.helper.ListProductSearchAdapter;
import android.annotation.SuppressLint;
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
	private int idCategory;
	private String nomeCategory;
	
	private int range=20;
	private int offset=0;
	private ListProduct list;	
	private Handler handlerProd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_fanta_search_category);
		listCategories = new ListCategories();
		ctx=this;
		Intent intent  = getIntent();
		// id e nome della categoria selezionato nella activity precedente
		idCategory = intent.getIntExtra("idCategory",0);
		nomeCategory = intent.getStringExtra("nomeCategory");

    	//Handler per il messaggio di risposta del Server, proveniente dal Thread.
		handler = new Handler() {
			@Override
            public void handleMessage(Message mess1) {
            	
            	int res = mess1.arg1;
               	
            	if(res==0){
            		//esegui intent visualizzazione prodotti
            		Log.i("Push", "CIAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");			        
			        
            		handlerProd = new Handler() {
            			@Override
            			public void handleMessage(Message mess) {
                        	
                        	int res1 = mess.arg1;
                        	if(res1 == 1){
                        		finish();
                        		Intent intent = new Intent(getBaseContext(), SearchListActivity.class);
                        		intent.putExtra("PRODUCTLIST",(Parcelable) list);
                        		intent.putExtra("search_prod_category", ""+idCategory);
                            	startActivity(intent);
                        	}
                        	else{
                        		finish();
                        	}
            			}
            		};
            		SearchData task = new SearchData();
            		task.execute(""+idCategory);
            	
            	}
                if(res == 1){
                	showList();
                }
            }
		};
		
		requestCategories task = new requestCategories();
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
			        Intent intent = new Intent(getBaseContext(), CategoryGrid.class);
            		intent.putExtra("idCategory",listCategories.get(arg2).getId());
            		intent.putExtra("nomeCategory",listCategories.get(arg2).getName());
                	startActivity(intent);
				}
			});
	    }
		
		public class requestCategories extends AsyncTask<Void, Void, Void> {
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
				json.put("idCategoria", idCategory);
				JSONArray arrayObject = connection.connectForCatalog("gestioneCatalogoAppProva", json, handler,Const.CONNECTION_TIMEOUT,Const.SOCKET_TIMEOUT);
				JSONObject obj= (JSONObject)arrayObject.get(0);
				Log.i("HAS: ", obj.getString("hasSubCategory"));

				if(obj.getString("hasSubCategory").equals("1")){
					for(int q = 0;q<20;q++){
						Log.i("HAS SUBCATEGORIES", "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
					}
					listCategories = new ListCategories();			
					for (int i=1;i<arrayObject.length();i++){ 
						// Lettura dell'oggetto Json
						obj= (JSONObject)arrayObject.get(i);
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
				}
				else{
					for(int q = 0;q<20;q++){
						Log.i("HAS SUBCATEGORIES", "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");

					}
					Message message = handler.obtainMessage(1, Const.KO, 0);
					handler.sendMessage(message);
				}

				
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection1: " + e.toString());
			}

			return null;
		}
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
					json.put("mode", 0);
					json.put("offset", offset);
					json.put("range", range);
					
					
					JSONArray array = connection.connectForCataalog("info_download_cf2", json,Const.CONNECTION_TIMEOUT,Const.SOCKET_TIMEOUT);
					
					JSONObject jObj = (JSONObject) array.get(0);
					int res=Integer.parseInt(jObj.getString("result"));
					
					if(res==1){
						list=new ListProduct(array);
						Message message = handlerProd.obtainMessage(1, res, 0);
						handlerProd.sendMessage(message);
					}else{
						Message message = handlerProd.obtainMessage(1, res, 0);
						handlerProd.sendMessage(message);
					}
						
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					

				return null;
			};
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
		}
		return super.onOptionsItemSelected(item);
	}

	
}
