package it.cdpaf.helper;

import it.cdpaf.entity.ListProduct;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Const {
	public static final String APPNAME ="Computer Family"; 
	public static final String IPADDRESS = "dev.computerfamily.it/cfapp/";
	public static final String SEARCHMODE = "1";
	public static final String CATEGORYMODE = "0";
//	public static final String IPADDRESS = "10.0.2.2";
	
	public static ListProduct basketProductList;
	
	public static final String IMAGE_URL = "http://www.computerfamily.it/store/images/";
	public static final String QRCODE_URL = "http://"+IPADDRESS+"/images/QRCOrdini/";

	public static final int OK = 1;
	public static final int KO = 0;
	public static final int TIMEOUT = 2;
	public static final int CONNECTION_TIMEOUT = 10000;
	public static final int SOCKET_TIMEOUT = 10000;
	public static final String TIMEOUTS= "T";
	//Numero di cifre in un id di un prodotto.
	public static final int IDFORMAT = 13;
	//Nome del file per le Preferences
	public static final String PREFS_NAME = "FilePreferences";


	public static final int ATTEMPTS_RETRANSMISSION = 3;
	
	
    // indirizzo del server per la registrazione del servizio notifiche
	public static final String SERVER_URL = "http://shopapp.dyndns.org:88/PUSH/register.php"; 
    // ID del progetto di google
    //static final String SENDER_ID = "589713569951"; 
	public static final String SENDER_ID = "458575759094"; //Progetto associato a costantino.depetrillo@gmail.com 
    
    
	public static final String TAG = "Notifica per android";
	public static final String DISPLAY_MESSAGE_ACTION = "it.torvergata.mp.DISPLAY_MESSAGE";
	public static final String EXTRA_MESSAGE = "message";
	public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
	
	public static final void initializeBasketList() {
		basketProductList=new ListProduct();
	}

	
	public static final Drawable resize(Drawable image) {
	    Bitmap b = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 130, 130, false);
	    return new BitmapDrawable(bitmapResized);
	}

	public static boolean verifyConnection(Context ctx) {
		// TODO Auto-generated method stub
		ConnectivityManager cm =
		        (ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}
	
	
}

