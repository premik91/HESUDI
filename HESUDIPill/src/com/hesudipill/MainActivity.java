package com.hesudipill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
	
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    
	private boolean checkLogin(){
		return true;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkLogin()){
        	setContentView(R.layout.activity_main);
        	
            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.urnik);
     
            // preparing list data
            prepareListData();
     
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
     
            // setting list adapter
            expListView.setAdapter(listAdapter);
        	
        }else{
        	//pošlji na log-in stran
        }
        
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("8:00");
        listDataHeader.add("11:00");
        listDataHeader.add("14:00");
        listDataHeader.add("17:00");
        listDataHeader.add("20:00");
 
        // Adding child data
        List<String> ura8 = new ArrayList<String>();
        ura8.add("Zdravilo1, 3x");
        ura8.add("Zdravilo2, 1x");
        ura8.add("Zdravilo3, 1x");
        ura8.add("Zdravilo4, 2x");
 
        List<String> ura11 = new ArrayList<String>();
        ura11.add("Zdravilo2, 2x");
        ura11.add("Zdravilo3, 1x");
 
        List<String> ura14 = new ArrayList<String>();
        ura14.add("Zdravilo2, 1x");
        ura14.add("Zdravilo5, 2x");
        ura14.add("Zdravilo6, 3x");
        
        List<String> ura17 = new ArrayList<String>();
        ura17.add("Zdravilo2, 1x");
        ura17.add("Zdravilo5, 2x");
        ura17.add("Zdravilo6, 3x");
        
        List<String> ura20 = new ArrayList<String>();
        ura20.add("Zdravilo1, 3x");
        ura20.add("Zdravilo2, 1x");
        ura20.add("Zdravilo3, 1x");
        ura20.add("Zdravilo4, 2x");
 
        listDataChild.put(listDataHeader.get(0), ura8); // Header, Child data
        listDataChild.put(listDataHeader.get(1), ura11);
        listDataChild.put(listDataHeader.get(2), ura14);
        listDataChild.put(listDataHeader.get(3), ura17);
        listDataChild.put(listDataHeader.get(4), ura20);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
