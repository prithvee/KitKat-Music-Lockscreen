package com.ash.kitkatmusiclockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartRec extends BroadcastReceiver {

	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
		{    
			Intent serviceIntent = new Intent(context, Ser.class);
			context.startService(serviceIntent);
        } 
		
	}

}
