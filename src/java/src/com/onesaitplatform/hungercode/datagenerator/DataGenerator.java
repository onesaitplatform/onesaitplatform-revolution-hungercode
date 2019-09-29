package com.onesaitplatform.hungercode.datagenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DataGenerator {

	private static final int COORDINATE_SETS = 10; 

	public static void main(String[] args) {
		
		double north = 40.472659;
		double south = 40.394216;
		double east = -3.658012;
		double west = -3.728298;

		double [][] coordinatesets = new double[COORDINATE_SETS][2];
		List<String> locationPathStrings = new ArrayList<>();
		coordinatesets = generateRndCoordinates(COORDINATE_SETS, north, south, east, west);
		for (int i = 0; i < COORDINATE_SETS-1; i = i+2) {
			double[] init = coordinatesets[i];
			double[] end = coordinatesets[i+1];
			double[][] locationPath = generatePathCoordinates(12, init[0], init[1], end[0], end[0]);
			locationPathStrings.add(generateTripFile(locationPath, i*100, "vehicleState", "stateDescription"));
		}
		for (String unPath:locationPathStrings) {
			System.out.print(unPath);
		}
	}
	

	private static double[][] generateRndCoordinates(int nof_sets, double north, double south, double east, double west) {
		Random aleatorios = new Random();
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
	
	private static String generateTripFile(double[][] steps, int vehicleId, String stateName, String description) {
		StringBuffer route = new StringBuffer();
		for (double[] oneStep:steps){
			route.append("{\"HG_VehicleState\":{ \"id\":");
			route.append(vehicleId);
			route.append(",\"stateName\":\"");
			route.append(stateName);
			route.append("\",\"description\":\"");
			route.append(description);
			route.append("\",\"Location\":(");
			route.append(oneStep[0]);
			route.append(",");
			route.append(oneStep[1]);
			route.append(")}}\n");
		}
		return new String(route);
	}
}
