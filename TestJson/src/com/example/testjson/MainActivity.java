package com.example.testjson;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	//private String naslov="http://www.ehrscape.com:8081/rest/v1/demographics/party/21";
	//private String naslov="http://headers.jsontest.com/";
	//private String naslov="http://admin:ehrscape123@www.ehrscape.com:8081/rest/v1/demographics/party/21";
	
	//Tale dela:
	private String naslov="http://www.ehrscape.com:8081/rest/v1/session/?username=demo&password=demo34";
	//Bad request:
	//private String naslov="http://admin:ehrscape123@www.ehrscape.com:8081/rest/v1/session/";
	
	
	private String naslov2="http://www.ehrscape.com:8081/rest/v1/demographics/party/21/";
	
	public void doStuff(View view){
		
		new LoginPost().execute(naslov);
		
	}
	
	public void doOtherStuff(View view){
		new SetData().execute(naslov2);
	}
	
//LOGIN ASYNC THINGY
	
	private class LoginPost extends AsyncTask<String, Void, List<String>> 
	{
		protected List<String> doInBackground(String... args) 
		{
			List<String> vrste = parseLogin(args[0]);
			return vrste;
		}

		protected void onPostExecute(List<String> vrste) 
		{
			loginPost(vrste);
		}
	}
 
	private List<String>  parseLogin(String url)// throws ParserConfigurationException, IOException, SAXException 
	{
		List<String> lista = new ArrayList<String>();

        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
        
        // getting JSON string from URL
        
        JSONObject json = jParser.getJSONFromUrl(url);
        
        if(json==null){
        	lista.add("NULL ODGOVOR");
        	return lista;
        }
        	
        String test = url;
        //lista.add(test);
       
		try 	//parse thingy
		{
			
			
			test = json.getString("sessionId");
			lista.add(test);
			lista.add(json.toString());
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
         
        return lista;
	}
	
	public void loginPost(final List<String> vrste)
	{
		TextView tw = (TextView) findViewById(R.id.textView2);
		TextView tw2 = (TextView) findViewById(R.id.textView1);
		tw.setText(naslov2+"?sessionId="+vrste.get(0));
		tw2.setText(vrste.get(1));
		
		naslov2=naslov2+"?sessionId="+vrste.get(0);
		
	}
	
	//GET DATA ASYNC THINGY
	
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
        
        if(json==null){
        	lista.add("NULL ODGOVOR");
        	return lista;
        }
        
        String test = url;
       // lista.add(test);
       
		//try 	//parse thingy
		//{
			
			
			//test = json.getString("Host");
			//lista.add(test);
			lista.add(json.toString());
			
		//} 
		//catch (JSONException e) 
		//{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
         
        return lista;
	}
	
	public void prikaziVsebino(final List<String> vrste)
	{
		//TextView tw = (TextView) findViewById(R.id.textView2);
		TextView tw2 = (TextView) findViewById(R.id.textView1);
		//tw.setText(vrste.get(0));
		tw2.setText(vrste.get(0));
		
	}
	
	
}
