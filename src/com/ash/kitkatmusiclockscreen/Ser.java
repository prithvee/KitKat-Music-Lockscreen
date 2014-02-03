package com.ash.kitkatmusiclockscreen;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Ser extends Service {
	IntentFilter iF = new IntentFilter();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		iF.addCategory("ComponentInfo");
	    iF.addCategory("com.spotify.mobile.android.service.SpotifyIntentService");
	    iF.addCategory("com.spotify.mobile.android.service.SpotifyService");
	 
	 
	    iF.addAction("com.spotify.mobile.android.ui.widget.SpotifyWidget");
	    iF.addAction("ComponentInfo");
	    iF.addAction("com.spotify");
	    iF.addAction("com.spotify.mobile.android.service.SpotifyIntentService");
	    iF.addAction("com.spotify.mobile.android.service.SpotifyService");
	 
	 
	    iF.addAction("com.android.music.metachanged");
	    iF.addAction("com.android.music.playstatechanged");
	    iF.addAction("com.android.music.playbackcomplete");
	    iF.addAction("com.android.music.queuechanged");
	    iF.addAction("com.spotify.mobile.android.ui");
		registerReceiver(mReceiver, iF);
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	    iF.addCategory("ComponentInfo");
	    iF.addCategory("com.spotify.mobile.android.service.SpotifyIntentService");
	    iF.addCategory("com.spotify.mobile.android.service.SpotifyService");
	 
	 
	    iF.addAction("com.spotify.mobile.android.ui.widget.SpotifyWidget");
	    iF.addAction("ComponentInfo");
	    iF.addAction("com.spotify");
	    iF.addAction("com.spotify.mobile.android.service.SpotifyIntentService");
	    iF.addAction("com.spotify.mobile.android.service.SpotifyService");
	 
	 
	    iF.addAction("com.android.music.metachanged");
	    iF.addAction("com.android.music.playstatechanged");
	    iF.addAction("com.android.music.playbackcomplete");
	    iF.addAction("com.android.music.queuechanged");
	    iF.addAction("com.spotify.mobile.android.ui");
		registerReceiver(mReceiver, iF);
		
		
		
		
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		
		iF.addCategory("ComponentInfo");
	    iF.addCategory("com.spotify.mobile.android.service.SpotifyIntentService");
	    iF.addCategory("com.spotify.mobile.android.service.SpotifyService");
	 
	 
	    iF.addAction("com.spotify.mobile.android.ui.widget.SpotifyWidget");
	    iF.addAction("ComponentInfo");
	    iF.addAction("com.spotify");
	    iF.addAction("com.spotify.mobile.android.service.SpotifyIntentService");
	    iF.addAction("com.spotify.mobile.android.service.SpotifyService");
	 
	 
	    iF.addAction("com.android.music.metachanged");
	    iF.addAction("com.android.music.playstatechanged");
	    iF.addAction("com.android.music.playbackcomplete");
	    iF.addAction("com.android.music.queuechanged");
	    iF.addAction("com.spotify.mobile.android.ui");
		registerReceiver(mReceiver, iF);
		
		return Service.START_STICKY;
	}
	
	public String artist;
	public String album;
	public String track;
	public String path;
	boolean isMusicPlaying;
	int ids;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		 
        @SuppressLint("UseValueOf")
		@SuppressWarnings("rawtypes")
		@Override
        public void onReceive(Context context, Intent intent)
        {

            Log.e("onReceive launched", "kekse");
            
            		Bundle b = intent.getExtras();
            		Set<String> set = b.keySet();
            		Iterator it = set.iterator();
            		while(it.hasNext()==true)
            		{
            			Log.e("Musicss"," "+it.next());
            		}
            		
                    String action = intent.getAction();
                    String cmd = intent.getStringExtra("command");

                    String com = intent.getStringExtra("ComponentInfo");

                    Log.e("mIntentReceiver.onReceive ", action + " / " + cmd+ " / "+ com);
                    
                    artist = intent.getStringExtra("artist");
                    album = intent.getStringExtra("album");
                    track = intent.getStringExtra("track");
                    ids = intent.getIntExtra("id", 0);
                    isMusicPlaying = intent.getBooleanExtra("playing", false);
                    String state = new Boolean(isMusicPlaying).toString();
                    /*Toast.makeText(context,
							"State type is: " + state,
							Toast.LENGTH_SHORT).show();
                    /*try {
						Object ob = b.get("playing");
						Class cls = ob.getClass();

						Toast.makeText(context,
								"State type is: " + cls.getName(),
								Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Toast.makeText(context,
								"State type failed!! ",
								Toast.LENGTH_SHORT).show();
					}*/
                    Log.e("Music",artist+":"+album+":"+track);
                    //Toast.makeText(context, "Command : "+cmd+"n Artist : "+artist+" Album :"+album+"Track : "+track+" " , Toast.LENGTH_SHORT).show();
                    path = match(track,album,ids,context);
                    update();
        }
        
        
};
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	TextView tvsong;
    TextView tvalbum;
    TextView tvartist;
    TextView tvpath;
    Drawable adw;
    Drawable dw;
    public MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
    void update()
    {   
    	 /*tvsong = (TextView) findViewById(R.id.song);
		 tvalbum = (TextView) findViewById(R.id.album);
		 tvartist = (TextView) findViewById(R.id.artist);
		 tvpath = (TextView) findViewById(R.id.path);*/
		
    	//if(mReceiver!=null)
	    //{
    		try 
    		    {
    				tvsong.setText(" "+track);
    				tvalbum.setText(" "+album);
    				tvartist.setText(" "+artist);
    				tvpath.setText(" "+path);
    			}
    		    catch (Exception e)
    			{
    				// TODO: handle exception
    			}
    			
    			
    			
    			//String tr = (Environment.getExternalStorageDirectory().getAbsolutePath()+"/hindi songs/"+track+".mp3");
    			File fl = new File(path);
    			//LinearLayout bgd = (LinearLayout) findViewById(R.id.bg);
    			
    		    if(fl.exists())
    		    {

	    		    try {
	    					    myRetriever.setDataSource(path);

	    			} catch (Exception e) {
	    				// TODO: handle exception
	    			}
	    		    
	    		    
	    		    byte[] artwork;
	    		    
	    		    
	    		    artwork = myRetriever.getEmbeddedPicture();

	    		    try {
	    				if (artwork != null && isMusicPlaying) {
	    					Bitmap bMap = BitmapFactory.decodeByteArray(artwork, 0,artwork.length);
	    					@SuppressWarnings("deprecation")
	    					Intent intent = new Intent("ART3");
	    					intent.putExtra("art", bMap);
	    					intent.putExtra("artist", artist);
	    					intent.putExtra("album", album);
	    					intent.putExtra("track", track);
	    					intent.putExtra("path", path);
	    					intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
	    					sendBroadcast(intent);
	    					
	    					Intent intent2 = new Intent("ART");
	    					intent2.putExtra("state", "running");
	    					intent2.putExtra("art", bMap);
	    					intent2.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
	    					sendBroadcast(intent2);
	    				} 
	    				else{
	    					
	    					//bgd.setBackgroundColor(Color.parseColor("#333333"));
	    					Intent intent2 = new Intent("ART");
	    					intent2.putExtra("state", "not");
	    					intent2.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
	    					sendBroadcast(intent2);
	    					
	    					Intent intent = new Intent("ART3");
	    					intent.putExtra("artist", artist);
	    					intent.putExtra("album", album);
	    					intent.putExtra("track", track);
	    					intent.putExtra("path", path);
	    					intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
	    					sendBroadcast(intent);
	    				}
	    			} catch (Exception e) {
	    				// TODO: handle exception
	    				
	    			}
    		
    		    }
    		    else
    		    {
    		    	//bgd.setBackgroundColor(Color.parseColor("#333333"));
					Intent intent2 = new Intent("ART");
					intent2.putExtra("state", "not");
					intent2.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
					sendBroadcast(intent2);
					
					Intent intent = new Intent("ART3");
					intent.putExtra("artist", artist);
					intent.putExtra("album", album);
					intent.putExtra("track", track);
					intent.putExtra("path", path);
					intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
					sendBroadcast(intent);
				}
	    }

	public Drawable dorig = null;
    public int i = 0; 
    public static Drawable drw ;
    private String[] STAR = { "*" };
    Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    public String match(String name,String albname , int idss,Context context) 
        { 
            Cursor cursor;
            Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            String spath = "x";
            try {
				if (isSDPresent) {
					cursor = context.getContentResolver().query(allsongsuri, STAR, selection,null, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								String songname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

								int song_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

								String fullpath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

								String albumname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));

								try {
									if(songname.equals(name))
									{
										spath = fullpath;
										break;
									}

									else if(song_id == idss)
									{
										spath = fullpath;
										break;
									}
									
									else if (albumname.equals(albname)) {
										spath = fullpath;
										break;
									}
									
								} catch (Exception e) {
									// TODO: handle exception
								}

							} while (cursor.moveToNext());
						}
						//cursor.close();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return spath;
        }

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
