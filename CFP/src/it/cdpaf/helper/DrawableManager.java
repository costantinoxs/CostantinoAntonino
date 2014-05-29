

package it.cdpaf.helper;

/*
 Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/
import it.cdpaf.*;
import it.cdpaf.entity.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

public class DrawableManager {
	//Statica perch� tutte le Activity o Fragment devono possedere la stessa mappa di caching delle immagini
    public static Map<String, Drawable> drawableMap = new HashMap<String, Drawable>();
    
    public DrawableManager() {
    	 //drawableMap = new HashMap<String, Drawable>();
    }

    public static Drawable fetchDrawable(String urlString, Context ctx) {
        if (drawableMap.containsKey(urlString)) {
        	Log.d(ctx.getClass().getSimpleName(),"DRAWABLE MANAGER FD:"+ "RIUSO: "+urlString+" Size:"+drawableMap.size());
        	return drawableMap.get(urlString);
            
        }

        Log.d(ctx.getClass().getSimpleName(), "image url:" + urlString);
        try {
        	
        	
            InputStream is = fetch(urlString,ctx);
            Log.i("IS:--->",is.toString());
            Drawable drawable = Drawable.createFromStream(is, "src");


            if (drawable != null) {
                drawableMap.put(urlString, drawable);
                Log.d(ctx.getClass().getSimpleName(), "got a thumbnail drawable: " + drawable.getBounds() + ", "
                        + drawable.getIntrinsicHeight() + "," + drawable.getIntrinsicWidth() + ", "
                        + drawable.getMinimumHeight() + "," + drawable.getMinimumWidth());
            } else {
              Log.w(ctx.getClass().getSimpleName(), "could not get thumbnail");
            }

            return drawable;
        } catch (MalformedURLException e) {
            Log.e(ctx.getClass().getSimpleName(), "MALFORMEDURL EXC fetchDrawable failed", e);
            return null;
        } catch (IOException e) {
            Log.e(ctx.getClass().getSimpleName(), "IO EXCP fetchDrawable failed", e);
            return null;
        }
    }
    
    public static Drawable returnDrawable(String urlString, Context ctx) {
        	
        	Log.d(ctx.getClass().getSimpleName(),"return ------DRAWABLE MAN:"+ "RIUSO: "+urlString+" Size:"+drawableMap.size());
        	Drawable d= drawableMap.get(urlString);
            return d;
        
    }
    
    public static void fetchDrawableOnThread( final Macrocategory mac, final ImageView imageView, final Context ctx) {
        final String urlString=Const.IMAGE_URL+mac.getNomeImmagine();
    	if (drawableMap.containsKey(urlString)) {
            imageView.setImageDrawable(drawableMap.get(urlString));
            //product.setImmagine(drawableMap.get(urlString));
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Drawable dr = (Drawable) message.obj;
            	imageView.setImageDrawable(dr);
                mac.setImmagine(dr);
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
            	Drawable d = ctx.getResources().getDrawable( R.drawable.ic_launcher );
            	Message messagea = handler.obtainMessage(1, d);
                handler.sendMessage(messagea);
                Drawable drawable = fetchDrawable(urlString,ctx);
                Message messageb = handler.obtainMessage(1, drawable);
                handler.sendMessage(messageb);
            }
        };
        thread.start();
    }
    
    public static void fetchDrawableOnThread( final Category cat, final ImageView imageView, final Context ctx) {
        final String urlString=Const.IMAGE_URL+cat.getNomeImmagine();
    	if (drawableMap.containsKey(urlString)) {
            imageView.setImageDrawable(drawableMap.get(urlString));
            //product.setImmagine(drawableMap.get(urlString));
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Drawable dr = (Drawable) message.obj;
            	imageView.setImageDrawable(dr);
                cat.setImmagine(dr);
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
            	Drawable d = ctx.getResources().getDrawable( R.drawable.ic_launcher );
            	Message messagea = handler.obtainMessage(1, d);
                handler.sendMessage(messagea);
                Drawable drawable = fetchDrawable(urlString,ctx);
                Message messageb = handler.obtainMessage(1, drawable);
                handler.sendMessage(messageb);
            }
        };
        thread.start();
    }

    public static void fetchDrawableOnThread( final Product product, final ImageView imageView, final Context ctx) {
        final String urlString=Const.IMAGE_URL+product.getPercorsoImmagine();
    	if (drawableMap.containsKey(urlString)) {
    		Log.d(ctx.getClass().getSimpleName(),"DRAWABLE MANAGER FDOT:"+ "RIUSO, Size:"+drawableMap.size());
        	imageView.setImageDrawable(drawableMap.get(urlString));
            return;
            //product.setImmagine(drawableMap.get(urlString));
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Drawable dr = (Drawable) message.obj;
            	imageView.setImageDrawable(dr);
                product.setImmagine(dr);
            }
        };
        
        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
            	Drawable d = ctx.getResources().getDrawable( R.drawable.blank );
            	Message messagea = handler.obtainMessage(1, d);
                handler.sendMessage(messagea);
                Drawable drawable = fetchDrawable(urlString,ctx);
                
                Message messageb = handler.obtainMessage(1, drawable);
                handler.sendMessage(messageb);
            }
        };
        Log.i("DRAWABLE MANAGER:","LANCIO FDOT, Size:"+drawableMap.size());
        thread.start();
    }
    
    public static ArrayList<Drawable> fetchAllDrawableOnThread(String imagepath, final Context ctx) {
        final String urlString=Const.IMAGE_URL+imagepath;
        final ArrayList<Drawable> res= new ArrayList<Drawable>();
        for (int i=1;i<30;i++){
        	
        	String url=urlString+"_"+i;
        	String[] result = urlString.split(".j");
        	String prima=result[0];
        	String seconda=result[1];
        	String jpg=".j"+seconda;
        	final String urlStringValid=prima+"_"+i+jpg;
        	
        	if (drawableMap.containsKey(urlStringValid)) {
        		Log.d("ALL DRAWABLE","INDIVIDUATO UN RIUSO : "+ urlStringValid);
            	res.add(drawableMap.get(urlStringValid));
            }
        	
        	final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message message) {
                    Drawable e =((Drawable) message.obj);
                    res.add(e);
                }
            };
            
            Thread thread = new Thread() {
                @Override
                public void run() {
                    //TODO : set imageView to a "pending" image
                	Drawable d = ctx.getResources().getDrawable( R.drawable.blank );
                	Message messagea = handler.obtainMessage(1, d);
                    handler.sendMessage(messagea);
                    Drawable drawable = fetchDrawable(urlStringValid,ctx);
                    
                    Message messageb = handler.obtainMessage(1, drawable);
                    handler.sendMessage(messageb);
                }
            };
            Log.d("ALL DRAWABLE","LANCIO UNA RICERCA : "+ urlStringValid);
            thread.start();
        }
        
        return res;
    	
        
    }
   /* 
    public void fetchDrawableOnThread(final String urlString, final Product product, final Context ctx) {
        if (drawableMap.containsKey(urlString)) {
            product.setImmagine(drawableMap.get(urlString));
            Log.d(ctx.getClass().getSimpleName(),"DRAWABLE MANAGER:"+ "RIUSO, Size:"+drawableMap.size());
        	
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                product.setImmagine((Drawable) message.obj);
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
                Drawable drawable = fetchDrawable(urlString,ctx);
                Message message = handler.obtainMessage(1, drawable);
                handler.sendMessage(message);
            }
        };
        thread.start();
    }
    
*/
    private static InputStream fetch(String urlString, Context ctx) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
        
    }
/*
	public static void fetchDrawableQrCode(int ordId,final FragmentActivity ctx,final ImageView imageView) {
		// TODO Auto-generated method stub
		final String urlString=Const.QRCODE_URL+ordId+".png";
		
		if (drawableMap.containsKey(urlString)) {
            imageView.setImageDrawable(drawableMap.get(urlString));
            //product.setImmagine(drawableMap.get(urlString));
        }

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Drawable dr = (Drawable) message.obj;
            	imageView.setImageDrawable(dr);
                
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
            	Drawable d = ctx.getResources().getDrawable( R.drawable.blank );
            	Message messagea = handler.obtainMessage(1, d);
                handler.sendMessage(messagea);
                Drawable drawable = fetchDrawable(urlString,ctx);
                Message messageb = handler.obtainMessage(1, drawable);
                handler.sendMessage(messageb);
            }
        };
        thread.start();
    }
	*/	
}