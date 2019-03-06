
public class SupervisingAgent {

	
	/*AI functions that supervising agent needs to have
	 * 
	 * start system timer and turn system
	 * 
	 * for each turn
	 * 		initiate control flows of each light
	 * 
	 * 		Find the light with the largest traffic value in 2 turns if it is already green then advance two turns worth
	 * 					call standardturn methods
	 * 				else
	 * 					initiate a change of light sequence 
	 * 
	 * 
	 * if a light is green initiate reduction of the queue
	*/
	int time; //system time 
	int turn; //turns are used because lights can't instantly change from green to red
	
	//command queue
	
	IntersectionQueues currentIntersection;
	TrafficLightAgent trafficLightAgentsArray[];
	Lane intersectionLanes[];
	
	SupervisingAgent(Lane arrayOfintersectionLanes[] , IntersectionQueues current ){
		currentIntersection = current;
		intersectionLanes = new Lane [arrayOfintersectionLanes.length];
		for (int i = 0; i < arrayOfintersectionLanes.length; i++) {
			intersectionLanes[i] = arrayOfintersectionLanes[i];
		}
		
		
		buildTrafficLightAgents (intersectionLanes);
		AssignPairs(trafficLightAgentsArray);
		
		trafficLightAgentsArray[3].lane.laneLight.setCurrentColor(LightColor.green);
		trafficLightAgentsArray[2].lane.laneLight.setCurrentColor(LightColor.green);
		trafficLightAgentsArray[1].lane.laneLight.setCurrentColor(LightColor.green);
		trafficLightAgentsArray[0].lane.laneLight.setCurrentColor(LightColor.green);
		System.out.println("SupervisingAgent/switchToGreen test");
		//trafficLightAgentsArray[0].swtichToGreenWhenPairedLightIsGreen(trafficLightAgentsArray[0].lane, trafficLightAgentsArray[0].pairedLane);
	
		//trafficLightAgentsArray[0].lane.laneLight.setCurrentColor(LightColor.green);
		//trafficLightAgentsArray[0].greenToYellowWhenNotBusy(trafficLightAgentsArray[0].lane);
		/*
		for (int x = 0; x < 6; x++) {
			System.out.println("initiating control flow for lane agent");
		trafficLightAgentsArray[0].controlFlow();
			if(x % 2 == 0) {trafficLightAgentsArray[0].lane.laneLight.setCurrentColor(LightColor.red);
			System.out.println("EVEN\n\n");
		
			}
		}
		*/
		standardTurnMethods ();
	}
	
	
	
	
	public void standardTurnMethods () {
		if (allPairedLightsMatch() == true) {
		reduceTrafficInGreenLightLanes();
		}
		
		
		
	}
	
	
	
	public boolean allPairedLightsMatch () {
		int light1= 0;
		int light2 = 1;
		boolean pairdLightsMatch = true;
		
		while (light2 <= currentIntersection.intersectionLanes.length) {
			if (currentIntersection.intersectionLanes[light1].laneLight.currentColor.equals(currentIntersection.intersectionLanes[light2].laneLight.currentColor)) {
				System.out.println("light "+currentIntersection.intersectionLanes[light1].laneName + "matches " +currentIntersection.intersectionLanes[light2].laneName  );
			}else {
				System.out.println("light "+currentIntersection.intersectionLanes[light1].laneName + "DOES NOT match " +currentIntersection.intersectionLanes[light2].laneName  );
				
				return pairdLightsMatch = false;
			}
			
			//since the lights are arranged in pairs adding 2 goes to next one
			light1 += 2;
			light2 += 2;
		}
		
		return pairdLightsMatch;
		
	}
	
	
	
	public void reduceTrafficInGreenLightLanes() {	
		for (int i = 0; i < (currentIntersection.intersectionLanes.length )  ; i ++) {
			System.out.println("i = " +i);
			if (currentIntersection.intersectionLanes[i].laneLight.currentColor.equals(LightColor.green) || currentIntersection.intersectionLanes[i].laneLight.currentColor.equals(LightColor.yellow)) {
					System.out.println("lane color: "+ currentIntersection.intersectionLanes[i].laneLight.currentColor);
					System.out.println("intersectionLanes[i].getLaneQueue().size() was" +intersectionLanes[i].getLaneQueue().size());
				currentIntersection.removeVehiclesFromLane(intersectionLanes[i], 6);
					System.out.println("intersectionLanes[i].getLaneQueue().size() now" +intersectionLanes[i].getLaneQueue().size());
			}
		}
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
	
	
	
	
	
	

