package it.cdpaf.entity;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{
	private String		id;
	private String 		codice;
	private String 		percorsoImmagine;
	private double 		prezzo;
	private int 		quantita;
	private String		produttore;
	private String		nome;
	private String		descrizione;
	private String		icecat;
	private String		id_categoria;
	private String		id_sottocategoria;
	private String		nome_categoria;
	private String		nome_sotto_categoria;
	private Drawable 	immagine;
	
	
	
	public Product(String id, String codice, String percorsoImmagine,
			double prezzo, int quantita, String produttore, String nome,
			String descrizione, String icecat, String id_categoria,
			String id_sottocategoria, String nome_categoria,
			String nome_sotto_categoria) {
		super();
		this.id = id;
		this.codice = codice;
		this.percorsoImmagine = percorsoImmagine;
		this.prezzo = prezzo;
		this.quantita = quantita;
		this.produttore = produttore;
		this.nome = nome;
		this.descrizione = descrizione;
		this.icecat = icecat;
		this.id_categoria = id_categoria;
		this.id_sottocategoria = id_sottocategoria;
		this.nome_categoria = nome_categoria;
		this.nome_sotto_categoria = nome_sotto_categoria;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getPercorsoImmagine() {
		return percorsoImmagine;
	}

	public void setPercorsoImmagine(String percorsoImmagine) {
		this.percorsoImmagine = percorsoImmagine;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getProduttore() {
		return produttore;
	}

	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIcecat() {
		return icecat;
	}

	public void setIcecat(String icecat) {
		this.icecat = icecat;
	}

	public String getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(String id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getId_sottocategoria() {
		return id_sottocategoria;
	}

	public void setId_sottocategoria(String id_sottocategoria) {
		this.id_sottocategoria = id_sottocategoria;
	}

	public String getNome_categoria() {
		return nome_categoria;
	}

	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}

	public String getNome_sotto_categoria() {
		return nome_sotto_categoria;
	}

	public void setNome_sotto_categoria(String nome_sotto_categoria) {
		this.nome_sotto_categoria = nome_sotto_categoria;
	}

	public Drawable getImmagine() {
		return immagine;
	}

	public void setImmagine(Drawable immagine) {
		this.immagine = immagine;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
			dest.writeString(id);
			dest.writeString(codice);
			dest.writeString(percorsoImmagine);
			dest.writeDouble(prezzo);
			dest.writeInt(quantita);
			dest.writeString(produttore);
			dest.writeString(nome);
			dest.writeString(descrizione);
			dest.writeString(icecat);
			dest.writeString(id_categoria);
			dest.writeString(id_sottocategoria);
			dest.writeString(nome_categoria);
			dest.writeString(nome_sotto_categoria);
						
	}
	private Product(Parcel in) {
		 super();
		
				this.id = in.readString();
				this.codice = in.readString();
				this.percorsoImmagine = in.readString();
				this.prezzo = in.readDouble();
				this.quantita = in.readInt();
				this.produttore = in.readString();
				this.nome = in.readString();
				this.descrizione = in.readString();
				this.icecat = in.readString();
				this.id_categoria = in.readString();
				this.id_sottocategoria = in.readString();
				this.nome_categoria = in.readString();
				this.nome_sotto_categoria = in.readString();
			
	}
		 
	
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
	     public Product createFromParcel(Parcel in) {
	         return new Product(in);
	     }
	     public Product[] newArray(int size) {
	         return new Product[size];
	     }
	};
	
	
}
