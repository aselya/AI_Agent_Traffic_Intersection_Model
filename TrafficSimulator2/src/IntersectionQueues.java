/*
 * Stores the intersection
 * Currently set to st paul/ commonwealth
 */
import java.util.Random;

public class IntersectionQueues {
	//one quueue per lane
	int carIDtracker = 0;
	int personID = 0;
	//lane name, is it a left turn lane, does it have pedestrians
	Lane northStraight = new Lane("northStraight", false, true);
	Lane southStraight = new Lane("southStraight", false, true);
	Lane eastStraight = new Lane("eastStraight", false, true);
	Lane westStraight = new Lane("westStraight", false, true);
	Lane northLeft = new Lane("northLeft", true, false);
	Lane southLeft = new Lane("southLeft", true, false);

	Lane intersectionLanes[] = new Lane[6];
	
	
	
	
	
	
	public IntersectionQueues() {
		//lanes are entered in consecutive pairs
		//not the best design and I'd like to go back and update when i have the time
		intersectionLanes[0] = northStraight;
		intersectionLanes[1] = southStraight;
		intersectionLanes[2] = eastStraight;
		intersectionLanes[3] = westStraight;
		intersectionLanes[4] = northLeft;
		intersectionLanes[5] = northLeft;
		
		
		
	}

	
	public void addRandomAmountOfPeopleToALane( Lane lane , int time, boolean rushHour  ) {
		Random rand = new Random();
		int maxPeople = 8;
		if (rushHour == true){
			maxPeople = 16;
		}
		int peopleAdded  = rand.nextInt(maxPeople);
		for (int i= 0; i < peopleAdded; i ++) {
			Pedestrians person = new Pedestrians(personID, time);
			personID ++;
			lane.getPedestrianQueue().add(person);
			//time ++;
		}
		System.out.println("people in lane: " + lane.getPedestrianQueue().size());
}
	
	public void addPeopleToRandomLane( Lane [] laneArray, int time, boolean rushHour  ) {
		
		Random rand = new Random();
		int laneSelected  = rand.nextInt(laneArray.length);
			if (laneArray[laneSelected] != null) {
				if (laneArray[laneSelected].hasPedestrians == true) {//make sure there are no people added to left turn lanes or lanes with no crosswalks
			System.out.println("lane selected to add people: " + laneArray[laneSelected].laneName);
			addRandomAmountOfPeopleToALane( laneArray[laneSelected], time,  rushHour);
			}
			}
			
}	
	
	
	
	public void addRandomAmountOfTrafficToALane( Lane lane, int time, boolean rushHour  ) {
		Random rand = new Random();
		int maxCarsAdded = 2;
		if (rushHour = true){
			maxCarsAdded =4;
		}
		//int carsAdded = 2;
		int carsAdded  = rand.nextInt(maxCarsAdded);
		for (int i= 0; i < carsAdded; i ++) {
			Vehicle newVehicle = new Vehicle(carIDtracker, time);
			carIDtracker ++;
			lane.getLaneQueue().add(newVehicle);
			lane.numberOfVehicles++;
		}
		System.out.println("cars in lane: " + lane.getLaneQueue().size());
}
	
	public void addTrafficToRandomLane( Lane [] laneArray, int time, boolean rushHour) {
		Random rand = new Random();
		int laneSelected  = rand.nextInt(laneArray.length);
			if (laneArray[laneSelected] != null) {
			System.out.println("lane selected to add traffic: " + laneArray[laneSelected].laneName);
			addRandomAmountOfTrafficToALane( laneArray[laneSelected], time, rushHour);
			}
			
			
}
	
	
}



