package com.coffeesec.stunray;

public class CellTower {
	//Object representing cell tower, with name, network, cell id, etc.
	public String Name;
	public String Network;
	public Network Network2G, Network3G, Network4G;
	
	public CellTower(){
		Name = "New Tower";
		Network = "Cell Network";
		Network2G = new Network();
		Network3G = new Network();
		Network4G = new Network();
	}
}
