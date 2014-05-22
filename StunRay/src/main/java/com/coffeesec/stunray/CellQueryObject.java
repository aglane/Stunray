package com.coffeesec.stunray;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;
import android.telephony.NeighboringCellInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alane on 5/7/14.
 */
public class CellQueryObject extends SignalQueryObject {
    Context context;
    TelephonyManager tel;


    public CellQueryObject(Context c){
        context = c;
        tel = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public void getNearbyTowers() {
        //new GetNearbyTowersTask().execute();
        List<CellInfo> neighCells = tel.getAllCellInfo();
        towerMap = new HashMap<String, CellTower>();
        ArrayList<String> towerNames = new ArrayList<String>();
        if(tel.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM){
            Log.e("JSON Parser", "GSM Phone!");
            GsmCellLocation gsmCell = (GsmCellLocation) tel.getCellLocation();
            CellTower newTower = new CellTower();
            newTower.Name = String.valueOf(gsmCell.getCid());
            Log.e("JSON Parser", newTower.Name);
            towerMap.put(newTower.Name, newTower);
            towerNames.add(newTower.Name);

        }
        if(neighCells != null){
            for(int i = 0; i < neighCells.size(); i++){
                Log.e("JSON Parser", "Neighbours!");
                if (neighCells.get(i) instanceof CellInfoGsm) {
                    CellInfoGsm cellInfo = (CellInfoGsm) neighCells.get(i);
                    CellTower newTower = new CellTower();
                    newTower.Name = String.valueOf(cellInfo.getCellIdentity());
                    towerMap.put(newTower.Name, newTower);
                    towerNames.add(newTower.Name);
                }
            }
        }
        if(resultsAdapter != null) {
            resultsAdapter.clear();
            resultsAdapter.addAll(towerNames);
            resultsAdapter.notifyDataSetChanged();
        }
    }

    private class GetNearbyTowersTask extends AsyncTask<String, Void, List<NeighboringCellInfo>> {

        ProgressDialog progDialog;

        protected void onPreExecute()
        {
            progDialog = ProgressDialog.show(context, "Updating Tower List","Downloading list of nearby towers", true);

        };

        protected List<NeighboringCellInfo> doInBackground(String ... params) {
            List<NeighboringCellInfo> neighCells = tel.getNeighboringCellInfo();
            return neighCells;
        }

        // onPostExecute displays the results of the AsyncTask.

        protected void onPostExecute(List<NeighboringCellInfo> result) {
            progDialog.dismiss();
            //Parse the string if it isn't null
            if(result == null){
                Toast toast = Toast.makeText(context, "Unable to retrieve web page. URL may be invalid.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                towerMap = new HashMap<String, CellTower>();
                ArrayList<String> towerNames = new ArrayList<String>();
                for(int i = 0; i < result.size(); i++){
                    NeighboringCellInfo cellInfo = result.get(i);
                    CellTower newTower = new CellTower();
                    newTower.Name = String.valueOf(cellInfo.getCid());
                    towerMap.put(newTower.Name, newTower);
                    towerNames.add(newTower.Name);
                }
            }
        }
    }


}
