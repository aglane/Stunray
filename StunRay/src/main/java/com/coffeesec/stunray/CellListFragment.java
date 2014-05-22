package com.coffeesec.stunray;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CellListFragment extends android.app.Fragment {
    private CellQueryObject sqo;
    private ArrayAdapter<String> resultsAdapter;
    private ListView lvTowers;
    private View myView;
    private Button btnGetNearby;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_cell_list, container, false);
        //Grab view for result list
        lvTowers = (ListView) myView.findViewById(R.id.lvTowerList);
        //Create the adapter
        final ArrayList<String> list = new ArrayList<String>();
        list.add("No data");
        resultsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.tower_list_item, R.id.towerName, list);
        //Instantiate the query object before getting location data
        sqo = new CellQueryObject(getActivity());
        sqo.setTargetAdapter(resultsAdapter);
        //Set the list view's adapter
        lvTowers.setAdapter(resultsAdapter);

        btnGetNearby = (Button) myView.findViewById(R.id.btnRefreshList);
        btnGetNearby.setOnClickListener(new View.OnClickListener(){
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
}
