package com.coffeesec.stunray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class OpenSignalQueryObject extends SignalQueryObject {
	private String API_KEY;
    Context context;

	public OpenSignalQueryObject(Context c){
        //Set context, used for creating dialogs and whatnot later
        context = c;
        //Set the API Key from the config files
        API_KEY = context.getResources().getString(R.string.open_signal_key);
	}

	public void getNearbyTowers() {
		try {        
			String latitude = URLEncoder.encode(lat, "UTF-8");
			String longitude = URLEncoder.encode(lon, "UTF-8");
			String queryURL = String.format("http://api.opensignal.com/v2/networkrank.json?lat=%s&lng=%s&distance=10&apikey=%s",latitude,longitude,API_KEY);
	        new GetNearbyTowersTask().execute(queryURL);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }  
	}
	
	private class GetNearbyTowersTask extends AsyncTask<String, Void, String> {

        ProgressDialog progDialog;
        @Override
        protected void onPreExecute()
        {
            progDialog = ProgressDialog.show(context, "Updating Tower List","Downloading list of nearby towers", true);

        };
        @Override
        protected String doInBackground(String... urls) {
              
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
            	return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            progDialog.dismiss();
        	//Parse the string if it isn't null
        	if(result == null){
                Toast toast = Toast.makeText(context, "Unable to retrieve web page. URL may be invalid.", Toast.LENGTH_SHORT);
                toast.show();
        	} else {
    	        //Test parsing the data and pull out specific lines
        		towerMap = new HashMap<String, CellTower>();
        		ArrayList<String> towerNames = new ArrayList<String>();
    	        try{
    	        	JSONObject jObj = new JSONObject(result);
    	        	JSONObject netRank = jObj.getJSONObject("networkRank");
    	        	JSONArray names = netRank.names();
    	        	for(int i = 0; i < netRank.length(); i++){
    		        	CellTower newTower = new CellTower();
    	        		String name = names.getString(i);
    	        		JSONObject netObj = netRank.getJSONObject(name);
    	        		newTower.Name = name;
    	        		JSONObject carrier;
    	        		if(netObj.has("type4G")){
    	        			carrier = netObj.getJSONObject("type4G");
    	        			newTower.Network = carrier.getString("networkName");
    	        			Network network4G = new Network();
    	        			network4G.Type = 4;
    	        			network4G.Name = carrier.getString("networkName");
    	        			network4G.NetworkId = carrier.getString("networkId");
    	        			if(carrier.has("averageRssiAsu")) network4G.AverageRssiAsu = Float.parseFloat(carrier.getString("averageRssiAsu"));
    	        			if(carrier.has("averageRssiDb")) network4G.AverageRssiDb = Float.parseFloat(carrier.getString("averageRssiDb"));
    	        			if(carrier.has("sampleSizeRSSI")) network4G.SampleSizeRssi = Integer.parseInt(carrier.getString("sampleSizeRSSI"));
    	        			if(carrier.has("downloadSpeed")) network4G.DownloadSpeed = Float.parseFloat(carrier.getString("downloadSpeed"));
    	        			if(carrier.has("uploadSpeed")) network4G.UploadSpeed = Float.parseFloat(carrier.getString("uploadSpeed"));
    	        			if(carrier.has("pingTime")) network4G.PingTime = Float.parseFloat(carrier.getString("pingTime"));
    	        			if(carrier.has("reliability")) network4G.Reliability = Float.parseFloat(carrier.getString("reliability"));
    	        			newTower.Network4G = network4G;
    	        		} 
    	        		if(netObj.has("type3G")){
    	        			carrier = netObj.getJSONObject("type3G");
    	        			newTower.Network = carrier.getString("networkName");
    	        			Network network3G = new Network();
    	        			network3G.Type = 3;
    	        			network3G.Name = carrier.getString("networkName");
    	        			network3G.NetworkId = carrier.getString("networkId");
    	        			if(carrier.has("averageRssiAsu")) network3G.AverageRssiAsu = Float.parseFloat(carrier.getString("averageRssiAsu"));
    	        			if(carrier.has("averageRssiDb")) network3G.AverageRssiDb = Float.parseFloat(carrier.getString("averageRssiDb"));
    	        			if(carrier.has("sampleSizeRSSI")) network3G.SampleSizeRssi = Integer.parseInt(carrier.getString("sampleSizeRSSI"));
    	        			if(carrier.has("downloadSpeed")) network3G.DownloadSpeed = Float.parseFloat(carrier.getString("downloadSpeed"));
    	        			if(carrier.has("uploadSpeed")) network3G.UploadSpeed = Float.parseFloat(carrier.getString("uploadSpeed"));
    	        			if(carrier.has("pingTime")) network3G.PingTime = Float.parseFloat(carrier.getString("pingTime"));
    	        			if(carrier.has("reliability")) network3G.Reliability = Float.parseFloat(carrier.getString("reliability"));
    	        			newTower.Network3G = network3G;
    		        	} 
    	        		if (netObj.has("type2G")){
    		        		carrier = netObj.getJSONObject("type2G");
    		        		newTower.Network = carrier.getString("networkName");
    	        			Network network2G = new Network();
    	        			network2G.Type = 2;
    	        			network2G.Name = carrier.getString("networkName");
    	        			network2G.NetworkId = carrier.getString("networkId");
    	        			if(carrier.has("averageRssiAsu")) network2G.AverageRssiAsu = Float.parseFloat(carrier.getString("averageRssiAsu"));
    	        			if(carrier.has("averageRssiDb")) network2G.AverageRssiDb = Float.parseFloat(carrier.getString("averageRssiDb"));
    	        			if(carrier.has("sampleSizeRSSI")) network2G.SampleSizeRssi = Integer.parseInt(carrier.getString("sampleSizeRSSI"));
    	        			network2G.DownloadSpeed = 0.0f;
    	        			network2G.UploadSpeed = 0.0f;
    	        			network2G.PingTime = 0.0f;
    	        			network2G.Reliability = 0.0f;
    	        			newTower.Network2G = network2G;
    		        	}
    	        		towerMap.put(newTower.Name, newTower);
    	        		towerNames.add(newTower.Name);
    	        	}
    	        } catch (JSONException e){
    	            Log.e("JSON Parser", "Error parsing data " + e.toString());
    	        }
                if(resultsAdapter != null) {
                	resultsAdapter.clear();
                	resultsAdapter.addAll(towerNames);
                	resultsAdapter.notifyDataSetChanged();
                }
        	}
        	
       }
    }
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        //int response = conn.getResponseCode();
	        is = conn.getInputStream();

	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
	        StringBuilder sb = new StringBuilder();

	        String line = null;
	        while ((line = reader.readLine()) != null)
	        {
	            sb.append(line + "\n");
	        }
	        
	        return sb.toString();
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
}
