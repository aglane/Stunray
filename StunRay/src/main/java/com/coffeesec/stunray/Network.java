package com.coffeesec.stunray;

import android.os.Parcel;
import android.os.Parcelable;

public class Network implements Parcelable {
	String Name;
	String NetworkId;
	int Type;
	float AverageRssiAsu;
	float AverageRssiDb;
	int SampleSizeRssi;
	float DownloadSpeed;
	float UploadSpeed;
	float PingTime;
	float Reliability;
	
	public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
		public Network createFromParcel(Parcel in) {
			return new Network(in);
		}

		public Network[] newArray(int size) {
			return new Network[size];
		}
	};
	
	public Network(){
		//Used for creating empty dummy networks in CellTower objects
	}
	
	public Network(Parcel in){
		readFromParcel(in);
	}
	
	public Network(String name, String networkId, int type, float asu, float db, int sampleSize, float dlSpeed, float ulSpeed, float ping, float reliability){
		Name = name;
		NetworkId = networkId;
		Type = type;
		AverageRssiAsu = asu;
		AverageRssiDb = db;
		SampleSizeRssi = sampleSize;
		DownloadSpeed = dlSpeed;
		UploadSpeed = ulSpeed;
		PingTime = ping;
		Reliability = reliability;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Name);
		dest.writeString(NetworkId);
		dest.writeInt(Type);
		dest.writeFloat(AverageRssiAsu);
		dest.writeFloat(AverageRssiDb);
		dest.writeInt(SampleSizeRssi);
		dest.writeFloat(DownloadSpeed);
		dest.writeFloat(UploadSpeed);
		dest.writeFloat(PingTime);
		dest.writeFloat(PingTime);
	}
	
	private void readFromParcel(Parcel in){
		Name = in.readString();
		NetworkId = in.readString();
		Type = in.readInt();
		AverageRssiAsu = in.readFloat();
		AverageRssiDb = in.readFloat();
		SampleSizeRssi = in.readInt();
		DownloadSpeed = in.readFloat();
		UploadSpeed = in.readFloat();
		PingTime = in.readFloat();
		Reliability = in.readFloat();
	}
}
