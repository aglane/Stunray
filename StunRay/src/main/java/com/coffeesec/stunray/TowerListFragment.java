package com.coffeesec.stunray;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TowerListFragment extends android.app.Fragment implements LocationListener {
	private OpenSignalQueryObject sqo;
	private ArrayAdapter<String> resultsAdapter;
	private ListView lvTowers;
	private LocationManager locationManager;
	private String provider;
	private Location location;
    private View myView;
    private TextView curCoords;
    private Button btnGetNearby;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		myView = inflater.inflate(R.layout.fragment_tower_list, container, false);
        curCoords = (TextView) myView.findViewById(R.id.current_coords);
		//Grab view for result list
		lvTowers = (ListView) myView.findViewById(R.id.lvTowerList);
		//Create the adapter
		final ArrayList<String> list = new ArrayList<String>();
		list.add("No data");
		resultsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.tower_list_item, R.id.towerName, list);
		//Instantiate the query object before getting location data
		sqo = new OpenSignalQueryObject(getActivity());
		sqo.setTargetAdapter(resultsAdapter);
		//Set the list view's adapter
		lvTowers.setAdapter(resultsAdapter);
		
		btnGetNearby = (Button) myView.findViewById(R.id.btnRefreshList);
		btnGetNearby.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
			        // fetch data
					sqo.getNearbyTowers();
			    } else {
			        // display error
			    	Toast toast = Toast.makeText(getActivity(), "No network connection!", Toast.LENGTH_SHORT);
			    	toast.show();
			    }
			}
			
		});

		//Define click action for list items
		lvTowers.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
			          int position, long id) {
				Intent intent = new Intent(getActivity(), TowerDetailsActivity.class);
				String towerKey = (String) parent.getItemAtPosition(position);
				CellTower tower = sqo.getTowerInfo(towerKey);
				if(tower == null) tower = new CellTower();
                intent.putExtra("TowerName", tower.Name);
                intent.putExtra("TowerNetwork", tower.Network);
                intent.putExtra("TowerNetwork2G", tower.Network2G);
                intent.putExtra("TowerNetwork3G", tower.Network3G);
                intent.putExtra("TowerNetwork4G", tower.Network4G);
                getActivity().startActivity(intent);
			}
			
		});
		
		return myView;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the location manager
	    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the location provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    location = locationManager.getLastKnownLocation(provider);
	    
	    if (location != null) {
	    	//Now set the location data (gets passed to the query object)
	        onLocationChanged(location);
	    }
	}

	/* Request updates at startup */
	@Override
	public void onResume() {
	  super.onResume();
	  locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	/* Remove the locationlistener updates when Activity is paused */
	@Override
	public void onPause() {
	  super.onPause();
	  locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location loc) {
        String lon = String.valueOf(loc.getLongitude());
        String lat = String.valueOf(loc.getLatitude());
        if(curCoords != null) {
            curCoords.setText("Current: " + lon + ", " + lat);
            if(lon.length() > 0 && lat.length() > 0 && !btnGetNearby.isEnabled()) btnGetNearby.setEnabled(true); //Enable once we have a set of coordinates
        }
		if(sqo != null){
			sqo.setLonLat(lon,lat);
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
