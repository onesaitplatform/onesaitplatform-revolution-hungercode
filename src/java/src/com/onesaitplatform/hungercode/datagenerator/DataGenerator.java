package com.onesaitplatform.hungercode.datagenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataGenerator {

	private static final int COORDINATE_SETS = 10;
	private static final int MIN_VEHICLE_ID = 1000000; 
	private static int MIN_TYPE_ID = 1900;
	private static int MIN_USER_ID = 28000000;
	private static Random aleatorios;
	private static SortedMap<Long, HcHistoricalDataPoint> historico = new TreeMap<>();
	private static List<String> changeTypes = new ArrayList<>(Arrays.asList("Move", "Reserve", "Release"));
	private static List<String> vehicleTypeNames = new ArrayList<String>(Arrays.asList("Car","Motorcycle", "Scooter", "Minivan"));
	private static List<String> vehicleStates = new ArrayList<String>(Arrays.asList("Available", "To charge", "To repair", "Reserved", "In use"));
	private static List<String> damageTypes = new ArrayList<String>(Arrays.asList("None"));


	public static void main(String[] args) {
		aleatorios = new Random();
		
		// Vehicle types
		List<Integer> vehicleMaxPax = new ArrayList<Integer> (Arrays.asList(4,     2,            2,        7));
		List<Integer> ids = generaVehicleTypes(vehicleTypeNames.size());
		System.out.print(generateVehicleTypesJson(vehicleTypeNames, ids, vehicleMaxPax));
		
		// Vehicle states

		// Users
		List<String> usernames = new ArrayList<String>(Arrays.asList("PacoAlegre", "CarmiñaLagarto", "AppleAndroid", "AhorroExtremo"));
		System.out.print(generateUsers(usernames));
		
		// vehicle Logs
		
		double north = 40.472659;
		double south = 40.394216;
		double east = -3.658012;
		double west = -3.728298;
		

		double [][] coordinatesets = new double[COORDINATE_SETS][2];
		List<String> locationPathStrings = new ArrayList<>();
		coordinatesets = generateRndCoordinates(COORDINATE_SETS, north, south, east, west);
		int [] vehicleIds = generaVehicleIds(COORDINATE_SETS/2);
		for (int i = 0; i < COORDINATE_SETS-1; i = i+2) {
			double[] init = coordinatesets[i];
			double[] end = coordinatesets[i+1];
			double[][] locationPath = generatePathCoordinates(12, init[0], init[1], end[0], end[1]);
			locationPathStrings.add(generateTripFile(locationPath, vehicleIds[i/2], "vehicleState", "stateDescription"));
		}
	    System.out.println(generateVehicles(vehicleIds, ids, coordinatesets));
		for (String unPath:locationPathStrings) {
			System.out.print(unPath);
		}
		for(Entry<Long, HcHistoricalDataPoint> oneReg:historico.entrySet()) {
			System.out.println(oneReg.getValue().toJsonString());
		}
	}
	
	//{"HG_Vehicle":{ "id":28.6,"idTag":"string","batteryLevel":28.6,"batteryTemp":28.6,"totalKM":28.6,"vehicleType":28.6,"position":{"coordinates":[28.6,28.6],"type":"Point"},"damageList":[],"vehicleState":28.6,"hasDamage":true,"damageType":28.6}}
	private static String generateVehicles(int [] vehicleIds, List<Integer> vehicleTypeIds, double [][] coordSets) {
		StringBuffer vehicles = new StringBuffer();
		for (int i = 0; i < vehicleIds.length; i++) {
			vehicles.append("{\"HG_Vehicle\":{ \"id\":");
			vehicles.append(vehicleIds[i]);
			vehicles.append(",\"idTag\":\"");
			vehicles.append(String.format("%7dIDTAG", vehicleIds[i]));
			vehicles.append("\",\"batteryLevel\":28.6,\"batteryTemp\":28.6,\"totalKM\":28.6,\"vehicleType\":");
			vehicles.append(vehicleTypeIds.get(0));
			vehicles.append(",\"position\":{\"coordinates\":[");
			vehicles.append(coordSets[i][1]);
			vehicles.append(",");
			vehicles.append(coordSets[i][0]);
			vehicles.append("],\"type\":\"Point\"},\"damageList\":[],\"vehicleState\":28.6,\"hasDamage\":false,\"damageType\":0.0}}\n");
		}
		return new String(vehicles);
	}
	


	private static int[] generaVehicleIds(int nofVehicles) {
		int [] result = new int[nofVehicles]; 
		for (int i = 0; i < nofVehicles; i++) {
			result[i] = aleatorios.nextInt(MIN_VEHICLE_ID)+MIN_VEHICLE_ID;
		}
		return result;
	}


	private static double[][] generateRndCoordinates(int nof_sets, double north, double south, double east, double west) {
		double [][] coordinatesets = new double[nof_sets][2];
		double latRange = north-south;
		double lonRange = west-east;
		for (int i = 0; i < nof_sets; i++) {
			coordinatesets[i][0] = aleatorios.nextDouble()*latRange+south;
			coordinatesets[i][1] = aleatorios.nextDouble()*lonRange+east;
		}
		return coordinatesets;
	}

	private static double[][] generatePathCoordinates(int howManySteps, double fromLat, double fromLon, double toLat,
			double toLon){
		double [][] steps = new double[howManySteps][2];
		double stepLat = (toLat - fromLat)/(howManySteps + 1);
		double stepLon = (toLon - fromLon)/(howManySteps + 1);
		for (int i = 0; i < howManySteps; i++) {
			steps[i][0] = fromLat + stepLat*i;
			steps[i][1] = fromLon + stepLon*i;
		}
		return steps;
	}

	/**
	{
	"DeviceLog": {
		"location": {
			"coordinates": [28.6, 28.6],
			"type": "Point"
		},
		"status": "string",
		"level": "string",
		"message": "string",
		"extraOptions": "string",
		"device": "string",
		"timestamp": "2014-01-30T17:14:00Z"
	}
}
*/
	private static String generateTripFile(double[][] steps, int vehicleId, String stateName, String description) {
		StringBuffer route = new StringBuffer();
		int i = 0;
		int changeTypeInit = changeTypes.indexOf("Reserve");
		String vehicleIdTag = String.format("%7dIDTAG", vehicleId);
		double batteryLevel = 100;
		double batteryTemp = 30.0;
		double totalKM = vehicleId;
		int vehicleType = vehicleTypeNames.indexOf("Car");
		double latitude = steps[0][0];
		double longitude = steps[0][1];
		int vehicleStateInit = vehicleStates.indexOf("In use");
		boolean hasDamageInit = false;
		int damageTypeInit = damageTypes.indexOf("None");
		HcHistoricalDataPoint startInfo = new HcHistoricalDataPoint(changeTypeInit, vehicleId, vehicleIdTag, batteryLevel,
				batteryTemp, totalKM, vehicleType, latitude, longitude, vehicleStateInit, hasDamageInit, damageTypeInit);
		historico.put(startInfo.getId() , startInfo);

		for (double[] oneStep:steps){
			route.append("{\"DeviceLog\":{ \"location\":{\"coordinates\": [");
			route.append(oneStep[1]);
			route.append(", ");
			route.append(oneStep[0]);
			route.append("],\"type\":\"Point\"},\"status\":\"");
			route.append(stateName);
			route.append("\",\"level\":\"Full\",\"message\":\"");
			route.append("All Systems Nominal.");
			route.append("\",\"extraOptions\":\"");
			route.append("Default Options");
			route.append("\",\"device\":\"");
			route.append(String.format("HG_VehicleID %7d", vehicleId));
			route.append("\",\"timestamp\":\"2019-09-30T17:");
			route.append(String.format("%02d", 7 + i));
			route.append(":");
			route.append(String.format("%02d", aleatorios.nextInt(19))); //inexactitud del timestamp: hasta 19 segundos tras el minuto.
			route.append("Z\"}}\n");
			int changeType = changeTypes.indexOf("Move");
			batteryLevel -= i;
			batteryTemp += i;
			totalKM += i;
			latitude = oneStep[0];
			longitude = oneStep[1];
			int vehicleState = vehicleStates.indexOf("In use");
			boolean hasDamage = false;
			int damageType = damageTypes.indexOf("None");
			HcHistoricalDataPoint infoPunto = new HcHistoricalDataPoint(changeType, vehicleId, vehicleIdTag, batteryLevel,
					batteryTemp, totalKM, vehicleType, latitude, longitude, vehicleState, hasDamage, damageType);
			historico.put(infoPunto.getId() , infoPunto);
			i++;
		}
		int changeTypeEnd = changeTypes.indexOf("Release");
		int vehicleStateEnd = vehicleStates.indexOf("Available");
		HcHistoricalDataPoint releaseInfo = new HcHistoricalDataPoint(changeTypeEnd, vehicleId, vehicleIdTag, batteryLevel,
				batteryTemp, totalKM, vehicleType, latitude, longitude, vehicleStateEnd, hasDamageInit, damageTypeInit);
		historico.put(releaseInfo.getId() , releaseInfo);

		return new String(route);
	}

	private static List<Integer> generaVehicleTypes(int nofTypes){
		List<Integer> types = new ArrayList<>(nofTypes);
		for (int i = 0; i < nofTypes;) {
			int nextType = aleatorios.nextInt(MIN_TYPE_ID)+MIN_TYPE_ID;
			if (!types.contains(nextType)) {
				types.add(nextType);
				i++;
			}
		}
		return types;
	}
	
	
//	{"HG_VehicleType":{ "id":28.6,"typeName":"string","maxPassenger":28.6}}
	private static String generateVehicleTypesJson(List<String> typeNames, List<Integer> vehicleTypesIds, List<Integer> maxPax) {
		StringBuffer vTypes = new StringBuffer();
		int i = 0;
		for (Integer oneType:vehicleTypesIds) {
			vTypes.append("{\"HG_VehicleType\":{ \"id\":");
			vTypes.append(oneType);
			vTypes.append(",\"typeName\":\"");
			vTypes.append(typeNames.get(i%typeNames.size()));
			vTypes.append("\",\"maxPassenger\":");
			vTypes.append(maxPax.get(i%maxPax.size()));
			vTypes.append("}}\n");
			i++;
		}
		return new String(vTypes);
	}

	// {"HG_User":{ "id":28.6,"userName":"string","password":"string","minBonus":28.6,"email":"string"}}
	private static String generateUsers(List<String> usernames) {
		StringBuffer users = new StringBuffer();
		List<Integer> userIds = new ArrayList<>();
		for(int i = 0; i < usernames.size();) {
			Integer newId = aleatorios.nextInt(MIN_USER_ID) + MIN_USER_ID;
			if (!userIds.contains(newId)) {
				userIds.add(newId);
				users.append("{\"HG_User\":{ \"id\":");
				users.append(String.format("%8d", newId));
				users.append(",\"userName\":\"");
				users.append(usernames.get(i));
				users.append("\",\"password\":\"");
				users.append(String.format("%sP45S%d", usernames.get(i), i));
				users.append("\",\"minBonus\":");
				users.append(String.format("%2.2f", 0.0f));
				users.append(", \"email\":\"");
				users.append(usernames.get(i) + "@hungercode.com");
				users.append("\"}}\n");
				i++;
			}
			
		}
		return new String(users);
	}

	// {"HG_Historical":{ "id":28.6,"changeType":28.6,"vehicle":28.6,"idTag":"string","batteryLevel":28.6,"batteryTemp":28.6,"totalKM":28.6,"vehicleType":28.6,"position":{"coordinates":[28.6,28.6],"type":"Point"},"vehicleState":28.6,"hasDamage":true,"damageType":28.6}}
	private static String generateHistorical(){
		StringBuffer historico = new StringBuffer();
		historico.append("{\"HG_Historical\":{ \"id\":28.6,\"changeType\":28.6,\"vehicle\":28.6,\"idTag\":\"string\",\"batteryLevel\":28.6,\"batteryTemp\":28.6,\"totalKM\":28.6,\"vehicleType\":28.6,\"position\":{\"coordinates\":[28.6,28.6],\"type\":\"Point\"},\"vehicleState\":28.6,\"hasDamage\":true,\"damageType\":28.6}}\n");
		return new String(historico);
	}
	
}

