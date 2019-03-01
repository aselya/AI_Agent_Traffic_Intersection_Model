
public class SupervisingAgent {

	
	/* First find the lane pair with the highest value
	 *    turn both to green
	 *    
	 * 
	 */
	TrafficLightAgent trafficLightAgentsArray[];
	Lane intersectionLanes[];
	
	SupervisingAgent(Lane arrayOfintersectionLanes[] ){
		intersectionLanes = new Lane [arrayOfintersectionLanes.length];
		for (int i = 0; i < arrayOfintersectionLanes.length; i++) {
			intersectionLanes[i] = arrayOfintersectionLanes[i];
		}
		
		
		buildTrafficLightAgents (intersectionLanes);
		AssignPairs(trafficLightAgentsArray);
		
		trafficLightAgentsArray[1].lane.laneLight.setCurrentColor(LightColor.green);
		trafficLightAgentsArray[0].lane.laneLight.setCurrentColor(LightColor.red);
		System.out.println("SupervisingAgent/switchToGreen test");
		trafficLightAgentsArray[0].switchToGreen(trafficLightAgentsArray[0].lane, trafficLightAgentsArray[0].pairedLane);
	
	}
	
	
	
	
	public void buildTrafficLightAgents (Lane intersectionLanes[]) {
		trafficLightAgentsArray = new TrafficLightAgent [intersectionLanes.length];
		for (int i = 0; i < trafficLightAgentsArray.length; i++) {
				//System.out.println("lane name taken from intersectionLanes[i].laneName:"+ intersectionLanes[i].laneName);
			TrafficLightAgent newLightAgent = new TrafficLightAgent(intersectionLanes[i], intersectionLanes[i].laneName );
				//System.out.println(intersectionLanes[i].laneName);
			trafficLightAgentsArray[i] = newLightAgent;
			System.out.println("buildTrafficLightAgents/ trafficLightAgentsArray[i].name: "+ trafficLightAgentsArray[i].lane.laneLight.currentColor);
		}
		}
		

		
	public void AssignPairs (TrafficLightAgent trafficLightAgentsArray[]) {
		for (int i = 0; i < trafficLightAgentsArray.length; i+= 2) {
			trafficLightAgentsArray[i].setPairedLane(trafficLightAgentsArray[i+1].lane);
			trafficLightAgentsArray[i+1].setPairedLane(trafficLightAgentsArray[i].lane);
			
			//trafficLightAgentsArray[i].setPairedLight(trafficLightAgentsArray[i+1].lane.laneLight);
			//trafficLightAgentsArray[i+1].setPairedLight(trafficLightAgentsArray[i].lane.laneLight);
				//System.out.println(trafficLightAgentsArray[i].pairedLight.lightName);
				System.out.println(trafficLightAgentsArray[i].pairedLane.laneName +" paired with "+ trafficLightAgentsArray[i+1].pairedLane.laneName);		
		}
		
		
		}

	}
	
	
	
	
	
	

