package it.cdpaf.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.util.Log;

public class GenericFunctions {
	public static String convertStreamToString(java.io.InputStream is) {
	    try {
	        return new java.util.Scanner(is).useDelimiter("\\A").next();
	    } catch (java.util.NoSuchElementException e) {
	        return "";
	    }
	}
	
	public static String FirstIS(java.io.InputStream is,String str) {
	    try {
	        return new java.util.Scanner(is).next(str);
	    } catch (java.util.NoSuchElementException e) {
	        return "";
	    }
	}
	public static StringBuilder inputStreamToString(InputStream is) {
		
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
	public static String currencyStamp(double d){
		
		double price=d;
		DecimalFormat df= new DecimalFormat ("0.00");
		if(d == 0) {
			return "0,00"+" \u20ac";
		}
		else {
			String res=df.format(price)+" \u20ac";
			return res;
		}
	}
	public static String getDate(){
		long msTime = System.currentTimeMillis();  
		Date anotherCurDate = new Date(msTime);  
		SimpleDateFormat formatterDate = new SimpleDateFormat("EEEE, d MMMM yyy");  
		String formattedDateString = formatterDate.format(anotherCurDate);
		Log.i("Data",formattedDateString);
		return formattedDateString;
	}
	public static String getTime(){
		long msTime = System.currentTimeMillis();  
		Date anotherCurDate = new Date(msTime);  
		SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
		String formattedTimeString = formatterTime.format(anotherCurDate);
		Log.i("Orario",formattedTimeString);
		return formattedTimeString;
	}
	public static String convertOrderState(int associateOrderState) {
		String result;
		switch (associateOrderState) {
        case 0:  result = "In attesa di ricezione";
                 break;
        case 1:  result = "Pervenuto";
                 break;
        case 2:  result = "Preso in Carico";
                 break;
        case 3:  result = "Pronto, in attesa di ritiro";
                 break;
        case 4:  result = "Ritirato";
        		 break;         
        default: result="";
                 break;
    }
		return result;
	}
}
