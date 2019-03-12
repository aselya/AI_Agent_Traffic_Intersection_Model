import java.util.LinkedList; 
import java.util.Queue; 
import java.util.Random;

public class IntersectionQueues {
	//one quueue per lane
	int carIDtracker = 0;
	int time = 0;
	Lane northStraight = new Lane("northStraight", false);
	Lane southStraight = new Lane("southStraight", false);
	Lane eastStraight = new Lane("eastStraight", false);
	Lane westStraight = new Lane("westStraight", false);
	Lane northLeft = new Lane("northLeft", true);
	Lane southLeft = new Lane("southLeft", true);

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
	
	
	
	public void removeVehiclesFromLane (Lane lane, int cycleTime) {
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
	}
	
	
	
	
	
	public void addRandomAmountOfTrafficToALane( Lane lane  ) {
		Random rand = new Random();
		int carsAdded  = rand.nextInt(4);
		for (int i= 0; i < carsAdded; i ++) {
			Vehicle newVehicle = new Vehicle(carIDtracker, time);
			carIDtracker ++;
			lane.getLaneQueue().add(newVehicle);
			time ++;
		}
		System.out.println("cars in lane: " + lane.getLaneQueue().size());
}
	
	public void addTrafficToRandomLane( Lane [] laneArray  ) {
		Random rand = new Random();
		int laneSelected  = rand.nextInt(laneArray.length);
			if (laneArray[laneSelected] != null) {
			System.out.println("lane selected to add traffic: " + laneArray[laneSelected].laneName);
			addRandomAmountOfTrafficToALane( laneArray[laneSelected]);
			}
}
	
	
}



