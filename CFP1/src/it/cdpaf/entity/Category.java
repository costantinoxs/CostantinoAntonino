package it.cdpaf.entity;

import android.graphics.drawable.Drawable;

public class Category {
		private int id;
		private String name;
		private String nomeImmagine;
		private Drawable Immagine;
		
		
		public Category(int i,String n, String nomeImm){
			id=i;
			name=n;	
			setNomeImmagine(nomeImm);
			setImmagine(null);
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public Drawable getImmagine() {
			return Immagine;
		}

		public void setImmagine(Drawable immagine) {
			Immagine = immagine;
		}

		public String getNomeImmagine() {
			return nomeImmagine;
		}

		public void setNomeImmagine(String nomeImmagine) {
			this.nomeImmagine = nomeImmagine;
		}

		public String print() {
			String s = this.id+" "+this.name;
			return s;
		}
}
