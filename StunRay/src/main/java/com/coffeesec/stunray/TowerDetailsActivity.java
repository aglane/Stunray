package com.coffeesec.stunray;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TowerDetailsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tower_details);
		Intent intent = getIntent();
		String name = intent.getStringExtra("TowerName");
		String network = intent.getStringExtra("TowerNetwork");
		Network network2G = intent.getParcelableExtra("TowerNetwork2G");
		Network network3G = intent.getParcelableExtra("TowerNetwork3G");
		Network network4G = intent.getParcelableExtra("TowerNetwork4G");
		
		TextView tvName = (TextView) findViewById(R.id.towerName);
		tvName.setText(name);
		TextView tvNetworkName = (TextView) findViewById(R.id.networkName);
		tvNetworkName.setText(network);
		
		//Set 4G Info
		TextView tv4GAsu = (TextView) findViewById(R.id.txtRSSIAsu4G);
		tv4GAsu.setText(Float.toString(network4G.AverageRssiAsu));
		TextView tv4GDb = (TextView) findViewById(R.id.txtRSSIDb4G);
		tv4GDb.setText(Float.toString(network4G.AverageRssiDb));
		TextView tv4GSample = (TextView) findViewById(R.id.txtSampleSize4G);
		tv4GSample.setText(Integer.toString(network4G.SampleSizeRssi));
		TextView tv4GDownload = (TextView) findViewById(R.id.txtDownload4G);
		tv4GDownload.setText(Float.toString(network4G.DownloadSpeed));
		TextView tv4GUpload = (TextView) findViewById(R.id.txtUpload4G);
		tv4GUpload.setText(Float.toString(network4G.UploadSpeed));
		TextView tv4GPing = (TextView) findViewById(R.id.txtPing4G);
		tv4GPing.setText(Float.toString(network4G.PingTime));
		TextView tv4GReliability = (TextView) findViewById(R.id.txtReliability4G);
		tv4GReliability.setText(Float.toString(network4G.Reliability));
		
		//Set 3G Info
		TextView tv3GAsu = (TextView) findViewById(R.id.txtRSSIAsu3G);
		tv3GAsu.setText(Float.toString(network3G.AverageRssiAsu));
		TextView tv3GDb = (TextView) findViewById(R.id.txtRSSIDb3G);
		tv3GDb.setText(Float.toString(network3G.AverageRssiDb));
		TextView tv3GSample = (TextView) findViewById(R.id.txtSampleSize3G);
		tv3GSample.setText(Integer.toString(network3G.SampleSizeRssi));
		TextView tv3GDownload = (TextView) findViewById(R.id.txtDownload3G);
		tv3GDownload.setText(Float.toString(network3G.DownloadSpeed));
		TextView tv3GUpload = (TextView) findViewById(R.id.txtUpload3G);
		tv3GUpload.setText(Float.toString(network3G.UploadSpeed));
		TextView tv3GPing = (TextView) findViewById(R.id.txtPing3G);
		tv3GPing.setText(Float.toString(network3G.PingTime));
		TextView tv3GReliability = (TextView) findViewById(R.id.txtReliability3G);
		tv3GReliability.setText(Float.toString(network3G.Reliability));
		
		//Set 2G Info
		TextView tv2GAsu = (TextView) findViewById(R.id.txtRSSIAsu2G);
		tv2GAsu.setText(Float.toString(network2G.AverageRssiAsu));
		TextView tv2GDb = (TextView) findViewById(R.id.txtRSSIDb2G);
		tv2GDb.setText(Float.toString(network2G.AverageRssiDb));
		TextView tv2GSample = (TextView) findViewById(R.id.txtSampleSize2G);
		tv2GSample.setText(Integer.toString(network2G.SampleSizeRssi));
	}
}
