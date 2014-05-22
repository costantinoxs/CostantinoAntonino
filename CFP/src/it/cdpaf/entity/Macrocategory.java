package it.cdpaf.entity;

import android.graphics.drawable.Drawable;

public class Macrocategory {
		private int id;
		private String name;
		private String nomeImmagine;
		private Drawable Immagine;
		
		
		public Macrocategory(int i,String n, String nomeImm){
			id=i;
			name=n;	
			nomeImmagine = nomeImm;
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
		
		public String print(){
			String s = this.id+" "+this.name;
			
			return s;
		}

		public String getNomeImmagine() {
			return nomeImmagine;
		}

		public void setNomeImmagine(String nomeImmagine) {
			this.nomeImmagine = nomeImmagine;
		}

		public Drawable getImmagine() {
			return Immagine;
		}

		public void setImmagine(Drawable immagine) {
			Immagine = immagine;
		}
}
