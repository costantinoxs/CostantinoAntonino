package it.cdpaf.entity;






import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ListProduct extends ArrayList<Product> implements Parcelable {
	int count=0;
	
	
	public ListProduct(JSONArray array){
		super();
		for(int i=0;i<array.length();i++){
			try {
				Product temp;
				JSONObject jTemp = (JSONObject) array.get(i);
				temp= new Product(
						jTemp.getString("id"),
						jTemp.getString("codice"),
						jTemp.getString("immagine"),
						jTemp.getDouble("prezzo"),
						jTemp.getInt("quantita"),
						jTemp.getString("produttore"),
						jTemp.getString("nome"),
						jTemp.getString("descrizione"),
						jTemp.getString("icecat"),
						jTemp.getString("id_categoria"),
						jTemp.getString("id_sottocategoria"),
						jTemp.getString("nome_categoria"),
						jTemp.getString("nome_sotto_categoria"));
				this.add(temp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		this.print("Stampa Prodotti");
		
	}	

	public boolean add(Product prod){
		super.add(prod);
		for (int i=0;i<prod.getQuantita();i++){
			count++;
		}
		return true;
		
	}
	
	public Product remove(int position){
		int q=super.get(position).getQuantita();
		for (int i=0;i<q;i++){
			count--;
		}
		super.remove(position);
		return null;
	}

	public void print(String g) {
		// TODO Auto-generated method stub
		Log.i(g, " ");
		for(int i=0;i<this.size();i++){
			Log.i(g, "PRODOTTO NUMERO "+i+" : "	+this.get(i).getNome());
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
		dest.writeInt(this.size());
		for(int i=0;i<this.size();i++){
			
			dest.writeString(this.get(i).getId());
			dest.writeString(this.get(i).getCodice());
			dest.writeString(this.get(i).getPercorsoImmagine());
			dest.writeDouble(this.get(i).getPrezzo());
			dest.writeInt	(this.get(i).getQuantita());
			dest.writeString(this.get(i).getProduttore());
			dest.writeString(this.get(i).getNome());
			dest.writeString(this.get(i).getDescrizione());
			dest.writeString(this.get(i).getIcecat());
			dest.writeString(this.get(i).getId_categoria());
			dest.writeString(this.get(i).getId_sottocategoria());
			dest.writeString(this.get(i).getNome_categoria());
			dest.writeString(this.get(i).getNome_sotto_categoria());
		}
	}
	private ListProduct(Parcel in) {
			 super();
			 int totale=in.readInt();
			 for(int i=0;i<totale;i++){
			
				 String idcategoria= in.readString();
				 String codice= in.readString();
				 String percorsoImmagine= in.readString();
				 double prezzo=in.readDouble();
				 int quantitative=in.readInt();
				 String produttore=in.readString();
				 String nome=in.readString();
				 String descrizione=in.readString();
				 String icecat=in.readString();
				 String id_categoria=in.readString();
				 String id_sottocategoria=in.readString();
				 String nome_categoria=in.readString();
				 String nome_sotto_categoria=in.readString();
				 
				 Product pr = new Product(idcategoria, codice, percorsoImmagine, prezzo, quantitative, produttore,
						 nome, descrizione, icecat, id_categoria, id_sottocategoria, nome_categoria, nome_sotto_categoria);
				 
				 this.add(pr);
			     this.print("Lista");
			}
			 
		}
		public static final Parcelable.Creator<ListProduct> CREATOR = new Parcelable.Creator<ListProduct>() {
		     public ListProduct createFromParcel(Parcel in) {
		         return new ListProduct(in);
		     }
		     public ListProduct[] newArray(int size) {
		         return new ListProduct[size];
		     }
		};
			
}
		

