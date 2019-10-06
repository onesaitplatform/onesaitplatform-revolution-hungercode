package com.onesaitplatform.hungercode.datagenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class DataGenerator {

	private static final int COORDINATE_SETS = 10;
	private static final int MIN_VEHICLE_ID = 1000000; 
	private static int MIN_TYPE_ID = 1900;
	private static Random aleatorios;


	public static void main(String[] args) {
		aleatorios = new Random();
		
		List<String> vehicleTypeNames = new ArrayList<String>(Arrays.asList("Car","Motorcycle", "Scooter", "Minivan"));
		List<Integer> vehicleMaxPax = new ArrayList<Integer> (Arrays.asList(4,     2,            2,        7));
		List<Integer> ids = generaVehicleTypes(vehicleTypeNames.size());
		System.out.print(generateVehicleTypesJson(vehicleTypeNames, ids, vehicleMaxPax));
		
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
		for (String unPath:locationPathStrings) {
			System.out.print(unPath);
		}
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
	 * {"DeviceLog":{ 
	 * 			"location":{
	 * 				"coordinates":{"latitude":28.6,"longitude":28.6},
	 * 				"type":"Point"
	 * 				},
	 * 			"status":"string",
	 * 			"level":"string",
	 * 			"message":"string",
	 * 			"extraOptions":"string",
	 * 			"device":"string",
	 * 			"timestamp":"2014-01-30T17:14:00Z"}}
	 */
	private static String generateTripFile(double[][] steps, int vehicleId, String stateName, String description) {
		StringBuffer route = new StringBuffer();
		int i = 0;
		for (double[] oneStep:steps){
			route.append("{\"DeviceLog\":{ \"location\":{\"coordinates\":{\"latitude\":");
			route.append(oneStep[0]);
			route.append(",\"longitude\":");
			route.append(oneStep[1]);
			route.append("},\"type\":\"Point\"},\"status\":\"");
			route.append(stateName);
			route.append("\",\"level\":\"Full\",\"message\":\"");
			route.append("All Systems Nominal.");
			route.append("\",\"extraOptions\":\"");
			route.append("Default Options");
			route.append("\",\"device\":\"");
			route.append(String.format("HG_Vehicle: %7d", vehicleId));
			route.append("\",\"timestamp\":\"2019-09-30T17:");
			route.append(String.format("%02d", 7 + i));
			route.append(":");
			route.append(String.format("%02d", aleatorios.nextInt(19))); //inexactitud del timestamp: hasta 19 segundos tras el minuto.
			route.append("Z\"}}\n");
			i++;
		}
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
}
