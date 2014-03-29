package fri.hesudi.falldetect;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	private double FALL_THRESHOLD = 20.0;
	private int ALERT_TIMEOUT = 20000;
	private double[] window = new double[50];
	private String state;
	private boolean fall, prev_fall;
	private MediaPlayer wav_alarm, wav_help;
	private TextView display;
	private long notificationDisplayed = 0;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accel_main);
        
        display=(TextView)findViewById(R.id.textView1);
        
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        for (int i=0;i<window.length;i++){
			window[i]=0;
		}
        state="none";
		wav_alarm=MediaPlayer.create(getBaseContext(), R.raw.alarm);
		wav_help=MediaPlayer.create(getBaseContext(), R.raw.help);
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			AddData(event.values[0],event.values[1],event.values[2]);
			posture_recognition(window,event.values[1]);
			FallDetection(window);
			
			SystemState(state);
		}
	}
	
	private void FallDetection(double[] window) {
		prev_fall = fall;
		if (window.length > 1) {
			double max = window[0];
			double maxi = 0;
			
			double min = window[0];
			double mini = 0;
			for (int i=0; i<window.length; i++) {
				if(window[i]>max) {
					max = window[i];
					maxi = i;
				}
				else if(window[i]<min) {
					min= window[i];
					mini = i;
				}
			}
			if (maxi > mini && max - min > FALL_THRESHOLD) {
				fall = true;
			}
			else {
				fall = false;
			}
		}
		else {
			fall = false;
		}
	}
	   
	private void posture_recognition(double[] window, double ay) {
		int zrc=compute_zrc(window);
		if(zrc<2){
			if(Math.abs(ay)<5){
				state="sitting";
			}
			else{
				state="standing";
			}
		}
		else{
			if(zrc>4){
				state="walking";
			}
			else{
				state="none";
			}
		}
		display.setText(state);
	}
	
	private int compute_zrc(double[] window) {
		int count=0;
		for(int i=1;i<=window.length-1;i++){
			if((window[i]-10)<0.5 && (window[i-1]-10)>0.5){
				count=count+1;
			}
		}
		return count;
	}
	
	private void SystemState(String state) {
		if (fall && !prev_fall) {
			/*
				if(curr_state.equalsIgnoreCase("sitting")){
				if(curr_state.equalsIgnoreCase("standing")){
				if(curr_state.equalsIgnoreCase("walking")){
			*/
			//if(!curr_state.equalsIgnoreCase("none")){
				
				Log.w(state, "TAG");
				
				/*if (!isFinishing()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("A fall has been detected!");
					builder.setMessage("Are you OK?");
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setPositiveButton("Everything OK", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int whichButton) {
							Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
						}});
					builder.setNegativeButton("No.", new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog, int whichButton) {
						        Toast.makeText(MainActivity.this, "ALARM!", Toast.LENGTH_SHORT).show();
						        wav_help.start();
						    }});
					final AlertDialog alert = builder.create();
					wav_fall.start();
					alert.show();
					new Handler().postDelayed(new Runnable() {
					    public void run() {
					    	if (alert.isShowing()) {
					    		wav_alarm.start();
					    		new Handler().postDelayed(new Runnable() {
								    public void run() {
								    	if (alert.isShowing()) {
								    		alert.getButton(DialogInterface.BUTTON_NEGATIVE).performClick();
								    	}
								    }
								}, ALERT_TIMEOUT/2);
					    	}
					    }
					}, ALERT_TIMEOUT/2);
				}
				else {*/
					showNotification();
				//}
			//}
		}
	}
	
	private boolean isNotificationVisible() {
		return notificationDisplayed > System.currentTimeMillis()-ALERT_TIMEOUT;
		/*
	    Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
	    PendingIntent test = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE);
	    return test != null;
	    */
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	   super.onNewIntent(intent);
	   if (intent.hasExtra("methodName")){
		   if(intent.getStringExtra("methodName").equals("OK")){
			   cancelNotification(0);
		   }
		   else if(intent.getStringExtra("methodName").equals("NO")){
			   cancelNotification(0);
			   Toast.makeText(MainActivity.this, "ALARM!", Toast.LENGTH_SHORT).show();
			   wav_help.start();
		   }
	   }
	}
	
	public void showNotification(){
		if (notificationDisplayed < System.currentTimeMillis()-1000) { 
	        Intent intentOK = new Intent(MainActivity.this, MainActivity.class).putExtra("methodName","OK");
	        Intent intentNO = new Intent(MainActivity.this, MainActivity.class).putExtra("methodName","NO");
	        PendingIntent pIntentOK = PendingIntent.getActivity(MainActivity.this, 0, intentOK, PendingIntent.FLAG_UPDATE_CURRENT);
	        PendingIntent pIntentNO = PendingIntent.getActivity(MainActivity.this, 1, intentNO, PendingIntent.FLAG_UPDATE_CURRENT);
	        Notification.Builder builder = new Notification.Builder(this)
	        	.setSmallIcon(R.drawable.icon3)
	            .setContentTitle("A fall has been detected!")
	            .setContentText("Are you OK?")
	            .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fall))
	            .addAction(0, "Everything OK", pIntentOK)
	            .addAction(0, "No", pIntentNO);
	
	        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	        notificationManager.notify(0, builder.build());
	        
	        notificationDisplayed = System.currentTimeMillis();
	        
	        new Handler().postDelayed(new Runnable() {
			    public void run() {
			    	if (isNotificationVisible()) {
			    		wav_alarm.start();
			    		new Handler().postDelayed(new Runnable() {
						    public void run() {
						    	if (isNotificationVisible()) {
						    		cancelNotification(0);
						    		Toast.makeText(MainActivity.this, "ALARM!", Toast.LENGTH_SHORT).show();
							        wav_help.start();
						    	}
						    }
						}, ALERT_TIMEOUT/2);
			    	}
			    }
			}, ALERT_TIMEOUT/2);
		}
    }

    public void cancelNotification(int notificationId){
        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
        notificationDisplayed = 0;
    }
	
	private void AddData(double ax, double ay, double az) {
		double a_norm=Math.sqrt(ax*ax+ay*ay+az*az);
		for(int i=0;i<=window.length-2;i++){
			window[i]=window[i+1];
		}
		window[window.length-1]=a_norm; 
	}
	
	public void exit(View view) {
		finish();
	}
}
