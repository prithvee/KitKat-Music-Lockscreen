package com.ash.kitkatmusiclockscreen;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {
	
	public IntentFilter iF;
	public String filename;
	public List<String> result;
	
	public MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
    public Uri selectedAudio;
    public String artist;
	public String album;
	public String track;
	public String path;
	public String aartist;
	public String aalbum;
	public String atrack;
	public String apath;
	int ids;
	private BroadcastReceiver Br;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    
			ActionBar bar = getSupportActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#ead585")));
			bar.setTitle("KitKat Lockscreen");
		
	    
	    /*Intent i = new Intent();
	    i.setClassName( "com.ash.currentplayingmusic","com.ash.currentplayingmusic.Ser");
	    bindService( i, null, Context.BIND_AUTO_CREATE);
	    this.startService(i);
	    
	    
	    IntentFilter iF = new IntentFilter();
	 
	 
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
		//registerReceiver(mReceiver, iF);
		//aupdate();*/
	    
	    /*
	    Intent serviceIntent = new Intent(getBaseContext(), Ser.class);
		startService(serviceIntent);
*/
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.act, menu);
	    return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.help:{
	        	//Toast.makeText(getBaseContext(), "Help" , Toast.LENGTH_SHORT).show();
	        	Intent intent = new Intent(this, HowTo.class);
	            startActivity(intent);  
	            return true;}

	        case R.id.about:{
	        	//Toast.makeText(getBaseContext(), "About" , Toast.LENGTH_SHORT).show();
	        	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
	        	final TextView message = new TextView(this);
	        	  // i.e.: R.string.dialog_message =>
	        	            // "Test this dialog following the link to dtmilano.blogspot.com"
	        	  //final SpannableString s = new SpannableString(this.getText(R.string.xda));
	        	  //Linkify.addLinks(s, Linkify.WEB_URLS);
	        	message.setMovementMethod(LinkMovementMethod.getInstance());
	        	message.setText(R.string.xda);
	        	  
	        	//dlgAlert.setMessage("Developed by: Prithviraj Shetty" +"\nprithvee@xda");
	        	dlgAlert.setView(message);
	        	dlgAlert.setTitle("About");
	        	dlgAlert.setIcon(R.drawable.ic_action_about);
	        	dlgAlert.setPositiveButton("OK",
	        		    new DialogInterface.OnClickListener() {
	        		        public void onClick(DialogInterface dialog, int which) {
	        		          //dismiss the dialog  
	        		        }
	        		    });
	        	dlgAlert.setCancelable(true);
	        	dlgAlert.create().show(); 
	        	
	            return true;}
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
				
		
	

			public void Clk(View v)
			{
				Toast.makeText(getBaseContext(), "Lockscreen background reset to default" , Toast.LENGTH_SHORT).show();
				
				//bgd.setBackgroundColor(Color.parseColor("#333333"));
				Intent intent = new Intent("ART2");
				intent.putExtra("state", "not");
				intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				sendBroadcast(intent);
				
			}
			public void strt(View v)
			{
				Toast.makeText(getBaseContext(), "Lockscreen background service started" , Toast.LENGTH_SHORT).show();
				Intent serviceIntent = new Intent(this, Ser.class);
			    startService(serviceIntent);
			    IntentFilter filter = new IntentFilter("ART3");
				registerReceiver(Br, filter);
			}
			
			public void stp(View v)
			{
				Toast.makeText(getBaseContext(), "Lockscreen background service stopped" , Toast.LENGTH_SHORT).show();
				Intent serviceIntent = new Intent(this, Ser.class);
			    stopService(serviceIntent);
			}
	
			/*@Override
		    public void onBackPressed() {
		    }
	    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
	 
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
	                       
	                            //Toast.makeText(context, "got this id: "+ ids , Toast.LENGTH_SHORT).show();
	                            Log.e("Music",artist+":"+album+":"+track);
	                            //Toast.makeText(context, "Command : "+cmd+"n Artist : "+artist+" Album :"+album+"Track : "+track+" " , Toast.LENGTH_SHORT).show();
	                            path = match(track,album,ids);
	                            update();
	                }
	                
	                
	    };
	    
	    public Drawable dorig = null;
	    public int i = 0; 
	    public static Drawable drw ;
	    private String[] STAR = { "*" };
	    Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	    @SuppressWarnings("deprecation")
		public String match(String name,String albname , int idss) 
	        { 
	            Cursor cursor;
	            Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
	            String spath = "x";
	            try {
					if (isSDPresent) {
						cursor = managedQuery(allsongsuri, STAR, selection,null, null);

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
	    
	    Drawable adw;
	    Drawable dw;
	    void update()
	    {   
	    	 tvsong = (TextView) findViewById(R.id.song);
    		 tvalbum = (TextView) findViewById(R.id.album);
    		 tvartist = (TextView) findViewById(R.id.artist);
    		 tvpath = (TextView) findViewById(R.id.path);
    		
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
	    			LinearLayout bgd = (LinearLayout) findViewById(R.id.bg);
	    			
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
		    				if (artwork != null) {
		    					Bitmap bMap = BitmapFactory.decodeByteArray(artwork, 0,artwork.length);
		    					@SuppressWarnings("deprecation")
		    					Drawable sdw = new BitmapDrawable(bMap);
		    					dw = sdw;
		    					Intent intent = new Intent("ART");
		    					intent.putExtra("art", bMap);
		    					intent.putExtra("state", "running");
		    					intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		    					sendBroadcast(intent);
		    					sdw.setAlpha(210);
		    					bgd.setBackground(sdw);
		    					
		    				} 
		    				else{
		    					
		    					bgd.setBackgroundColor(Color.parseColor("#333333"));
		    					Intent intent = new Intent("ART");
		    					intent.putExtra("state", "not");
		    					intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		    					sendBroadcast(intent);
		    				}
		    			} catch (Exception e) {
		    				// TODO: handle exception
		    				
		    			}
	    		
	    		    }
	    		    else
	    		    {
	    		    	bgd.setBackgroundColor(Color.parseColor("#333333"));
    					Intent intent = new Intent("ART");
    					intent.putExtra("state", "not");
    					intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    					sendBroadcast(intent);
    				}
	    		    
	    		    if(mReceiver!=null)
	    		    {
	    		    	adw = dw;
	    		    	aartist = artist;
	    				aalbum = album;
	    				atrack = track;
	    		    	apath = path;
	    		    }
	    		    /*else
	    		    {
	    		    	
	    		    }*/
    	   /* }*/
	    	
	    //}
	    
	    
	    
	    /*private void aupdate()
	    {

		    if(adw!=null)
		    	{
				    tvsong = (TextView) findViewById(R.id.song);	
			   		tvalbum = (TextView) findViewById(R.id.album);
			   		tvartist = (TextView) findViewById(R.id.artist);
			   		tvpath = (TextView) findViewById(R.id.path);
			   		tvsong.setText(" "+atrack);
					tvalbum.setText(" "+aalbum);
					tvartist.setText(" "+aartist);
					tvpath.setText(" "+apath);
			   		LinearLayout bgd = (LinearLayout) findViewById(R.id.bg);
			   		adw.setAlpha(210);
			   		bgd.setBackground(adw);
		    	}
		}*/
	    
	    
	
	

	/*@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    super.onActivityResult(requestCode, resultCode, data);

	    if (resultCode == Activity.RESULT_OK)
	    {
	            selectedAudio = data.getData();
	            MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
	            myRetriever.setDataSource(this, selectedAudio); // the URI of audio file
	            setArtwork(myRetriever);
	    }
	    //...
	}*/
	
	/*public boolean setArtwork(MediaMetadataRetriever myRetriever)
	{
	    byte[] artwork;
	    ImageView ivArtwork = (ImageView) findViewById(R.id.imageView1);
	    artwork = myRetriever.getEmbeddedPicture();

	    if (artwork != null)
	    {
	        Bitmap bMap = BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
	        ivArtwork.setImageBitmap(bMap);

	        return true;
	    }
	    else
	    {
	        ivArtwork.setImageBitmap(null);

	        return false;
	    }
	}/*

	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(mReceiver, iF);
	}*/
	
	
	
	/*public static void findFilePath(File dir, String filename, List<String> result) {

	    // get all files in dir
	    File[] files = dir.listFiles();
	    for( File f : files ){
	        if( f.isDirectory() ){
	            // Recurse
	            findFilePath(dir, filename, result);
	        }

	        else{
	            if( f.getName().equals("filename") ){
	                String absPath = f.getAbsolutePath();
	                result.add(absPath.substring(0, absPath.lastIndexOf(File.separator)));
	            }
	        }
	    }
	}
}*/


}






