package com.michaelchentejada.harmankardon;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MainService extends Service{
	public static final String START_INTENT = "start";
	public static final String STOP_INTENT = "stop";
	
	boolean mHKServiceRunning = false;
	private Messenger messenger = null;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startBKService();
		
		if (intent.getAction() == null) {
			Log.i("Debug", "Blank intent");
//		}else if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
//			onWifiChange();
		}else if (intent.getAction().equals(START_INTENT)){
			play();
		}else if(intent.getAction().equals(STOP_INTENT)){
			pause();
		}else {
			Log.e("Debug", intent.getAction());
		} 
		
	
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void startBKService(){
		if (messenger == null) {
			ServiceConnection sConn = new ServiceConnection() {			
				@Override
				public void onServiceDisconnected(ComponentName name) {
					messenger = null;
				}
				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
				// We are connected to the service
					messenger = new Messenger(service);
				}
				};
				
			bindService(new Intent("com.harman.commom.device.model.imp.ConvertService"),
			sConn, Context.BIND_AUTO_CREATE);	
		}
	}

	private void onWifiChange(){
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	    
	    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    
	    String macAddress = wifiInfo.getBSSID();
	    
	    if (netInfo.isConnected()) {
	    	onAPChange(macAddress);
			Log.d("Debug", macAddress);
		}else {
		}   
	}
	
	private void onAPChange(final String MacAdress){
		if (MacAdress.equals("00:f7:6f:d0:71:6c")) {
			play();
		}
	}
	
	private void play(){
		try {
            if(messenger != null) {
            	Log.d("Debug","Sending Play Request to HKConnect.");
                String val = ""; //not yet implemented
                Message msg = Message.obtain(null, ConvertService.MSG_ID_START_PLAYING_ALL);

                msg.replyTo = new Messenger(new ResponseHandler());

                // We pass the value and set in msg object
                Bundle b = new Bundle();
                b.putString("data", val);
                msg.setData(b);

                //send message to HKConnect app
                messenger.send(msg);
            } else {
            	Log.d("Debug","Please tap the connect button before attempting to communicate with the HK Controller App.");
            }
        } catch (RemoteException e) {
        	Log.e("Debug",e.toString());
        }
	}
	
	private void pause(){
        try {
            if(messenger != null) {
                Log.d("Debug","Sending Pause Request to HKConnect.");
                String val = ""; //not yet implemented
                Message msg = Message.obtain(null, ConvertService.MSG_ID_STOP_PLAYING_ALL);

                msg.replyTo = new Messenger(new ResponseHandler());

                // We pass the value and set in msg object
                Bundle b = new Bundle();
                b.putString("data", val);
                msg.setData(b);

                //send message to HKConnect App
                messenger.send(msg);
            } else {
            	Log.d("Debug","Please tap the connect button before attempting to communicate with the HK Controller App.");
            }
        } catch (RemoteException e) {
        	Log.e("Debug", e.toString());
        }
	}
	
	//Send Query Status event handler - Button 4
    public void sendQuery() {

        try {
            if(messenger != null) {
            	Log.d("Debug", "Sending Query to HKConnect.");
                String val = ""; //not yet implemented
                Message msg = Message.obtain(null, ConvertService.MSG_ID_QUERY_DEVICE);

                msg.replyTo = new Messenger(new ResponseHandler());

                // We pass the value and set in msg object
                Bundle b = new Bundle();
                b.putString("data", val);
                msg.setData(b);

                //send message to HKConnect app
                messenger.send(msg);
            } else {
            	Log.d("Debug", "Please tap the connect button before attempting to communicate with the HK Controller App.");
            }

        } catch (RemoteException e) {
        	Log.d("Debug", e.toString());
        }

    }
	
	// This class handles the Service response
    private final class ResponseHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            int respCode = msg.what;
            String str = msg.getData().getString("respData");
            
            switch (respCode) {
                default:
                	if (str.length() == 25) {
					}else{
						Log.d("Debug", "Length:" + str.length()+ "Bytes | " + "Data: " + str);
					}
                    break;
            }
        }
    }
}
