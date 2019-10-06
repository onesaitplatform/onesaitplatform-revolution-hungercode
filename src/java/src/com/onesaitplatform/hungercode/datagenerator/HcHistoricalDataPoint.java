package com.onesaitplatform.hungercode.datagenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ManuelVicente
 *
 * Clase para implementar una entrada del histórico de vehículos. El JSON que lo representa es el siguiente:
 * {
	"HG_Historical": {
		"id": 28.6,
		"changeType": 28.6,
		"vehicle": 28.6,
		"idTag": "string",
		"batteryLevel": 28.6,
		"batteryTemp": 28.6,
		"totalKM": 28.6,
		"vehicleType": 28.6,
		"position": {
			"coordinates": [28.6, 28.6],
			"type": "Point"
		},
		"vehicleState": 28.6,
		"hasDamage": true,
		"damageType": 28.6
	}
}
*/
public class HcHistoricalDataPoint implements Comparable<HcHistoricalDataPoint>{
	
	private static AtomicLong nextId = new AtomicLong(0L);
	
	private long id;
	private int changeType;
	private int vehicleId;
	private String vehicleIdTag;
	private double batteryLevel;
	private double batteryTemp;
	private double totalKM;
	private int vehicleType;
	private double latitude;
	private double longitude;
	private int vehicleState;
	private boolean hasDamage;
	private int damageType;
	
	
	public HcHistoricalDataPoint(int changeType, int vehicleId, String vehicleIdTag, double batteryLevel,
			double batteryTemp, double totalKM, int vehicleType, double latitude, double longitude, int vehicleState,
			boolean hasDamage, int damageType) {
		super();
		this.id = nextId.incrementAndGet();
		this.changeType = changeType;
		this.vehicleId = vehicleId;
		this.vehicleIdTag = vehicleIdTag;
		this.batteryLevel = batteryLevel;
		this.batteryTemp = batteryTemp;
		this.totalKM = totalKM;
		this.vehicleType = vehicleType;
		this.latitude = latitude;
		this.longitude = longitude;
		this.vehicleState = vehicleState;
		this.hasDamage = hasDamage;
		this.damageType = damageType;
	}

	public String toJsonString(){
		StringBuffer resultado = new StringBuffer();
		resultado.append("{\"HG_Historical\":{ \"id\":");
		resultado.append(id);
		resultado.append(",\"changeType\":");
		resultado.append(changeType);
		resultado.append(",\"vehicle\":");
		resultado.append(vehicleId);
		resultado.append(",\"idTag\":\"");
		resultado.append(vehicleIdTag);
		resultado.append("\",\"batteryLevel\":");
		resultado.append(batteryLevel);
		resultado.append(",\"batteryTemp\":");
		resultado.append(batteryTemp);
		resultado.append(",\"totalKM\":");
		resultado.append(totalKM);
		resultado.append(",\"vehicleType\":");
		resultado.append(vehicleType);
		resultado.append(",\"position\":{\"coordinates\":[");
		resultado.append(longitude);
		resultado.append(",");
		resultado.append(latitude);
		resultado.append("],\"type\":\"Point\"},\"vehicleState\":");
		resultado.append(vehicleState);
		resultado.append(",\"hasDamage\":");
		resultado.append(hasDamage);
		resultado.append(",\"damageType\":");
		resultado.append(damageType);
		resultado.append("}}");
		return new String(resultado);		
	}


	public long getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getChangeType() {
		return changeType;
	}


	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}


	public int getVehicleId() {
		return vehicleId;
	}


	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}


	public String getVehicleIdTag() {
		return vehicleIdTag;
	}


	public void setVehicleIdTag(String vehicleIdTag) {
		this.vehicleIdTag = vehicleIdTag;
	}


	public double getBatteryLevel() {
		return batteryLevel;
	}


	public void setBatteryLevel(double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}


	public double getBatteryTemp() {
		return batteryTemp;
	}


	public void setBatteryTemp(double batteryTemp) {
		this.batteryTemp = batteryTemp;
	}


	public double getTotalKM() {
		return totalKM;
	}


	public void setTotalKM(double totalKM) {
		this.totalKM = totalKM;
	}


	public int getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public int getVehicleState() {
		return vehicleState;
	}


	public void setVehicleState(int vehicleState) {
		this.vehicleState = vehicleState;
	}


	public boolean isHasDamage() {
		return hasDamage;
	}


	public void setHasDamage(boolean hasDamage) {
		this.hasDamage = hasDamage;
	}


	public int getDamageType() {
		return damageType;
	}


	public void setDamageType(int damageType) {
		this.damageType = damageType;
	}

	@Override
	public int compareTo(HcHistoricalDataPoint o) {
		return Long.valueOf(id).compareTo(Long.valueOf(o.getId()));
	}

}
