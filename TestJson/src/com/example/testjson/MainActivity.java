package com.example.testjson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	}
	
	public void doStuff(View view){
		
		new SetData().execute("http://www.ehrscape.com:8081/rest/v1/demographics/party/21");
	}

	private class SetData extends AsyncTask<String, Void, List<String>> 
	{
		protected List<String> doInBackground(String... args) 
		{
			List<String> vrste = downloadUrlDomParser(args[0]);
			return vrste;
		}

		protected void onPostExecute(List<String> vrste) 
		{
			prikaziVsebino(vrste);
		}
	}
	
	private List<String>  downloadUrlDomParser(String url)// throws ParserConfigurationException, IOException, SAXException 
	{
		List<String> lista = new ArrayList<String>();

        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
        
 
        // getting JSON string from URL
        
        JSONObject json = jParser.getJSONFromUrl(url);
        
        
        
        String test = url;
       // lista.add(test);
       
		/*try 	//parse thingy
		{
			
			
			test = json.getString("action");
			lista.add(test);
			
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
         */
        return lista;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void prikaziVsebino(final List<String> vrste)
	{
		TextView tw = (TextView) findViewById(R.id.textView2);
		TextView tw2 = (TextView) findViewById(R.id.textView1);
		if(!vrste.isEmpty())
			tw.setText(vrste.get(0));
		//tw2.setText(vrste.get(1));
		
	}
	
	
}