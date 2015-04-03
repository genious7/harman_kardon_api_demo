package com.michaelchentejada.harmankardon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * A broadcast receiver that starts the main service on boot
 * @author Michael Chen
 */
public class MainReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			//Start the service
			Intent i = new Intent(context, MainService.class);
			context.startService(i);	
		}
	}

}
