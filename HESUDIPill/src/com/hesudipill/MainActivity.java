package com.hesudipill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {
	
    ExpandableListAdapter listAdapterSchedule;
    ExpandableListView expListViewSchedule;
    List<String> listDataHeaderSchedule;
    HashMap<String, List<String>> listDataChildSchedule;
    
    ExpandableListAdapter listAdapterList;
    ExpandableListView expListViewList;
    List<String> listDataHeaderList;
    HashMap<String, List<String>> listDataChildList;
    
    private TabHost myTabHost;
    
	private boolean checkLogin(){
		return true;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkLogin()){
        	setContentView(R.layout.activity_main);
        	
        	myTabHost = (TabHost) findViewById(R.id.tabhost);
        	myTabHost.setup();
        	
        	//Adding tabs            
            myTabHost.addTab(myTabHost.newTabSpec("tab_schedule").setIndicator("Schedule").setContent(R.id.onglet1));
            myTabHost.addTab(myTabHost.newTabSpec("tab_list").setIndicator("List").setContent(R.id.onglet2));

        	
            // get the listview
            expListViewSchedule = (ExpandableListView) findViewById(R.id.schedule);
            expListViewList= (ExpandableListView) findViewById(R.id.list);
     
            // preparing list data
            prepareListData();
     
            listAdapterSchedule = new ExpandableListAdapter(this, listDataHeaderSchedule, listDataChildSchedule);
            listAdapterList = new ExpandableListAdapter(this, listDataHeaderList, listDataChildList);
     
            // setting list adapter
            expListViewSchedule.setAdapter(listAdapterSchedule);
            expListViewList.setAdapter(listAdapterList);
        	
        }else{
        	//pošlji na log-in stran
        }
        
    }

    private void prepareListData() {
        listDataHeaderSchedule = new ArrayList<String>();
        listDataChildSchedule = new HashMap<String, List<String>>();
        
        listDataHeaderList = new ArrayList<String>();
        listDataChildList = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeaderSchedule.add("8:00");
        listDataHeaderSchedule.add("11:00");
        listDataHeaderSchedule.add("14:00");
        listDataHeaderSchedule.add("17:00");
        listDataHeaderSchedule.add("20:00");
 
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
 
        listDataChildSchedule.put(listDataHeaderSchedule.get(0), ura8); // Header, Child data
        listDataChildSchedule.put(listDataHeaderSchedule.get(1), ura11);
        listDataChildSchedule.put(listDataHeaderSchedule.get(2), ura14);
        listDataChildSchedule.put(listDataHeaderSchedule.get(3), ura17);
        listDataChildSchedule.put(listDataHeaderSchedule.get(4), ura20);
        
     	// Adding child data
        listDataHeaderList.add("Zdravilo1");
        listDataHeaderList.add("Zdravilo2");
        listDataHeaderList.add("Zdravilo3");
        listDataHeaderList.add("Zdravilo4");
        listDataHeaderList.add("Zdravilo5");
        listDataHeaderList.add("Zdravilo6");
        
        // Adding child data
        List<String> zdravilo1 = new ArrayList<String>();
        zdravilo1.add("8:00, 3x");
        zdravilo1.add("20:00, 3x");
        
        List<String> zdravilo2 = new ArrayList<String>();
        zdravilo2.add("8:00, 1x");
        zdravilo2.add("11:00, 2x");
        zdravilo2.add("14:00, 1x");
        zdravilo2.add("17:00, 1x");
        zdravilo2.add("20:00, 1x");
        
        List<String> zdravilo3 = new ArrayList<String>();
        zdravilo3.add("8:00, 1x");
        zdravilo3.add("11:00, 1x");
        zdravilo3.add("20:00, 1x");
        
        List<String> zdravilo4 = new ArrayList<String>();
        zdravilo4.add("8:00, 2x");
        zdravilo4.add("20:00, 2x");
        
        List<String> zdravilo5 = new ArrayList<String>();
        zdravilo5.add("14:00, 2x");
        zdravilo5.add("17:00, 2x");
        
        List<String> zdravilo6 = new ArrayList<String>();
        zdravilo6.add("14:00, 3x");
        zdravilo6.add("17:00, 3x");
        
        listDataChildList.put(listDataHeaderList.get(0), zdravilo1);
        listDataChildList.put(listDataHeaderList.get(1), zdravilo2);
        listDataChildList.put(listDataHeaderList.get(2), zdravilo3);
        listDataChildList.put(listDataHeaderList.get(3), zdravilo4);
        listDataChildList.put(listDataHeaderList.get(4), zdravilo5);
        listDataChildList.put(listDataHeaderList.get(5), zdravilo6);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
