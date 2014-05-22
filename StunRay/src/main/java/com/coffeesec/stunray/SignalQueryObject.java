package com.coffeesec.stunray;

import java.util.HashMap;

import android.widget.ArrayAdapter;

public class SignalQueryObject {
	public HashMap<String, CellTower> towerMap; //Hold all of the data regarding cell towers
	public ArrayAdapter<String> resultsAdapter; //Adapter for pushing data into the list view
	public String lon, lat;
	
	public SignalQueryObject(){
		lon = "";
		lat = "";
		towerMap = new HashMap<String, CellTower>();
	}
	
	public SignalQueryObject(ArrayAdapter<String> ra){
		resultsAdapter = ra;
	}
	
	public void setTargetAdapter(ArrayAdapter<String> ra){
		resultsAdapter = ra;
	}
	
	public void getNearbyTowers(){
		
	}
	
	public CellTower getTowerInfo(String key){
		return towerMap.get(key);
	}
	
	public void setLonLat(String longitude, String latitude){
		lon = longitude;
		lat = latitude;
	}
}
