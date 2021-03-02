import java.util.ArrayList;
import java.util.List;

class SchoolBus{
	// This ArrayList represents the distance traveled after reaching a stop.
	// The first element represents the distance once the first stop is reached from the Depot.
	// The last element represents the distance once the Depot has been reached from the last stop.
	ArrayList<Integer> cumulativeDistances;
	// This ArrayList contains a name for each stop and lines up with the cumulativeDistances variable.
	// The last element will always be "Depot"
	ArrayList<String> stopNames;

	public SchoolBus(ArrayList<Integer> newCumulativeDistances, ArrayList<String> newStopNames){
		this.cumulativeDistances = newCumulativeDistances;
		this.stopNames = newStopNames;
	}

	// Find the smallest distance between stops. If stops A and B are the closest, return "A-B".
	// The return format is "StartStop-EndStop"
	// The largest distance apart two stops will be is 100.
	// If two pairs of stops have the same closest distance, return the later pair of stops.
	public String smallestDistanceBetweenStops(){
		int minDistance = 101;
		String startStop = "";
		String endStop = "";

		for(int i = 1; i < cumulativeDistances.size();i++){
			if (cumulativeDistances.get(i) - cumulativeDistances.get(i-1) <= minDistance){
				minDistance = cumulativeDistances.get(i) - cumulativeDistances.get(i-1);
				startStop = stopNames.get(i-1);
				endStop = stopNames.get(i);
			}
		}

		return startStop + "-" + endStop;
	}

	// Given a speed return an ArrayList corresponding to the times it take to travel between each stop
	public ArrayList<Integer> computeTimesBetweenStops(int speed){
		ArrayList<Integer> times = new ArrayList<Integer>();

		for(int i = 1; i < cumulativeDistances.size(); i++){
			times.add((cumulativeDistances.get(i) - cumulativeDistances.get(i-1))/speed);
		}

		return times;
	}

	// Return the last stop the SchoolBus can reach given a certain distance it can travel
	public String lastStopPossible(int distance){
		for(int i = 0; i < cumulativeDistances.size(); i++){
			if (cumulativeDistances.get(i) > distance){
				return this.stopNames.get(i-1);
			}
		}
		return "Depot";
	}

	// Return where the SchoolBus is given an array of speeds between stops and a time to locate the school bus
	// If the SchoolBus is at a stop return the stop name
	// If the SchoolBus is between stops return "StartStop-EndStop"
	public String locateSchoolBus(int time, ArrayList<Integer> speeds){
		int currTime = 0;
		for(int i = 0; i < cumulativeDistances.size(); i++){
			int distanceToLastStop;
			if (i==0){
				distanceToLastStop = cumulativeDistances.get(i);
			}else{
				distanceToLastStop = cumulativeDistances.get(i-1);
			}

			int timeAtEndOfStop = distanceToLastStop / speeds.get(i);
			currTime += timeAtEndOfStop;
			if (currTime == time){
				return this.stopNames.get(i);
			}else if (currTime > time){
				if (i==0){
					return "Depot-" + this.stopNames.get(i);
				}
				return this.stopNames.get(i-1) + "-" + this.stopNames.get(i);
			}
		}

		return "Depot";

	}

	public static void main(String[] args){
		ArrayList<Integer> distances = new ArrayList<Integer>();
		distances.add(10);
		distances.add(14);
		distances.add(20);
		distances.add(30);
		ArrayList<String> names = new ArrayList<String>();
		names.add("A");
		names.add("B");
		names.add("C");
		names.add("Depot");

		SchoolBus b = new SchoolBus(distances,names);
		System.out.println(b.smallestDistanceBetweenStops());
		System.out.println(b.computeTimesBetweenStops(2));
		System.out.println(b.lastStopPossible(19));
		System.out.println(b.lastStopPossible(30));

		ArrayList<Integer> speeds = new ArrayList<Integer>();
		speeds.add(1);
		speeds.add(2);
		speeds.add(3);
		speeds.add(2);
		System.out.println(b.locateSchoolBus(11, speeds));
	}
}