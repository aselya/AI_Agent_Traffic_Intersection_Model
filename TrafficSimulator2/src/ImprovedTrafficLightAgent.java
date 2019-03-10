
public class ImprovedTrafficLightAgent {

	/*
	 * The intitial agent I designed was pretty limited
	 * The goal with this agent is to take what I have learned from building the first
	 * and expand upon it
	 * 
	 * Agent enviornment
	 * 		Lane queue size
	 * 		Time since last green
	 * 		Time since turned gree
	 * 		Average time spent green
	 * 		Average cars per green
	 * 
	 * Intelegence operations
	 * 		stays in the 
	 * 
	 * 
	 * Agent states
	 * 	s		    Queue lenght < 3
	 * 	t	Green  |  Yellow
	 * 	a	Yellow |  RED
	 * 	r	Red    |  RED
	 *  t
	 *  
	 *  
	 *  first initiate a change via queue length
	 *  then initiate a change based on comparision to average
	 *  
	 * 
	 *  agent learns overtime based on when the supervising agent takes action
	 *  to predict moves it will take
	 */
	int currentTime;
	Lane lane;
	int timeSinceLastGreen;
	int timeSinceTurnedGreen;
	double averageTimeSpentGreen;
	double averageCarsPerGreen;
	int[] CarsPerGreenRecent = new int[4];
	int[] CarsInQueueWithSupervisorInitiatedChange = new int[4];
	
	public void controlFlow ( int Time) {
		currentTime = currentTime; // updates the time to reflect start of a new turn
		
		if(lane.laneLight.currentColor.equals(LightColor.green)) {
			changeBasedOnQueueLength();
		}
	}
	
	
	public boolean changeBasedOnPreviousSupervisisorActions() {
		return true;
	}
	
	
	/*
	 *  Finite State table for queue length input
	 * 	s		    Queuelength < 3| Queuelength > 3
	 * 	t	Green  |  Yellow       | 
	 * 	a	Yellow |  Yellow	   |	
	 * 	r	Red    |  RED		   |
	 *  t
	 */
	public boolean changeBasedOnQueueLength() {
		//size is less than 3 because it takes a car about 3
		//seconds to cross the intersection ensuring that a minimal amount
		//of cars will be left
		if(lane.getLaneQueue().size() < 3) {
			if(lane.laneLight.currentColor.equals(LightColor.green)) {
				lane.laneLight.setCurrentColor(LightColor.yellow);
				return true;
			}else {
				
			}
		}
		return false;
	}	
	
	
	
	
	
		
	}
	
	
	 
	
	
	
	
	
	
	

