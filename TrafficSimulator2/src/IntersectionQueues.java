import java.util.LinkedList; 
import java.util.Queue; 
import java.util.Random;

public class IntersectionQueues {
	//one quueue per lane
	int carIDtracker = 0;
	int time = 0;
	Lane northStraight = new Lane("northStraight");
	Lane southStraight = new Lane("southStraight");
	Lane eastStraight = new Lane("eastStraight");
	Lane westStraight = new Lane("westStraight");


	Lane intersectionLanes[] = new Lane[4];
	
	public int compareLaneWaitTimeValues () {
		double maxValue = 0;
		int maxValueIndex =0;
		for (int i = 0; i < intersectionLanes.length; i++ ) {
			System.out.println("intersectionLanes[i]: "+ intersectionLanes[i]);
			if (intersectionLanes[i].laneName.isEmpty()) {
					System.out.println("end of lanes reached");
				break;
			}else if (intersectionLanes[i].calculateWaitTimeValue( time) > maxValue){
						System.out.println("previous maxvalue: " + maxValue );
					maxValue =intersectionLanes[i].calculateWaitTimeValue( time);
					maxValueIndex = i;
					System.out.println("new maxvalue: " + maxValue );
					System.out.println("new maxvalueIndex: " + maxValueIndex );
					
				}
			} 
		
		System.out.println("final maxvalue: " + maxValue );
		System.out.println("final maxvalueIndex: " + maxValueIndex );
		return maxValueIndex;
	}
	
	
	
	
	
	public IntersectionQueues() {
		//lanes are entered in consecutive pairs
		//not the best design and I'd like to go back and update when i have the time
		intersectionLanes[0] = northStraight;
		intersectionLanes[1] = southStraight;
		intersectionLanes[2] = eastStraight;
		intersectionLanes[3] = westStraight;
		
		
		
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
			System.out.println("lane selected to add traffic: " + laneArray[laneSelected].laneName);
		addRandomAmountOfTrafficToALane( laneArray[laneSelected]);
}
	
	public String IntersectionToString (Lane [] laneArray) {
		String str = "\n";
		for(int i = 0; i < laneArray.length; i ++) {
			str = str +"\n" + laneArray[i].laneName + ": number of vehicles "+ laneArray[i].laneQueue.size() + " with a waiting value of: " + laneArray[i].calculateWaitTimeValue(time);
		}
		System.out.println(str);
		
		return str;
	}
}



