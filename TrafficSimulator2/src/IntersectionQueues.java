import java.util.LinkedList; 
import java.util.Queue; 
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
	
	public void removePeopleFromLane(Lane lane) {
		int peopleCrossed = 0;
		while (lane.getPedestrianQueue().size()> 0) {
			lane.getPedestrianQueue().poll();
			peopleCrossed ++;
		}
		System.out.println(peopleCrossed+ ": People have crossed");
	}
	
	public void removeVehiclesFromLane (Lane lane, int cycleTime) {
		removePeopleFromLane(lane);
		for (int i = 0; i < cycleTime/2; i ++) { //it takes about 2 seconds for a vehicle to cross an intersection
				//System.out.println("first vehicle in queue:"+lane.getLaneQueue().peek().id);
				//System.out.println("lane size before removal:"+lane.getLaneQueue().size());
			
			if(lane.getTrainQueue().size()> 0) {
				lane.getTrainQueue().poll();
				System.out.println("Train departed");
			}
			
			lane.getLaneQueue().poll();
			if (lane.getLaneQueue().size()>0) {
				System.out.println("new first vehicle in queue:"+lane.getLaneQueue().peek().id);
				System.out.println("lane size after removal:"+lane.getLaneQueue().size());
			}else {
					System.out.println("lane is empty");
				break;
			}
		}
		//removing the people from the lane too
		
		
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
		int maxCarsAdded = 4;
		if (rushHour = true){
			maxCarsAdded = 8;
		}
		
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



