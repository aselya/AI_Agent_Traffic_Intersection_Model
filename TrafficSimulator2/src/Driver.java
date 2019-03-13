import java.util.Random;



public class Driver {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver me = new Driver();
		me.DoIt();
		//me.DoIt2();
	}
	
	private void DoIt2() {
		// TODO Auto-generated method stub
		IntersectionQueues map = new IntersectionQueues();
		
		for (int i = 0; i < 30; i ++) {
			map.addTrafficToRandomLane(map.intersectionLanes );
		}
		
		map.IntersectionToString(map.intersectionLanes);
		
		
		
		SupervisingAgent supAgent = new SupervisingAgent(map.intersectionLanes, map);
		System.out.println(supAgent.intersectionLanes.toString());
	}


	public void DoIt() {
		IntersectionQueues map2 = new IntersectionQueues();
		
		int trainNumber = 0;
		int systemTime = 0;
		
		for (int i = 0; i < 30; i ++) {
			map2.addTrafficToRandomLane(map2.intersectionLanes );
		}
		
																		//Lane currLane, Lane currAcross, Lane currAdjacentLeft, Lane currAdjacentRight, Lane leftTurn
		FirstOrderLogicAgents agent1 = new FirstOrderLogicAgents ( map2.intersectionLanes[0], map2.intersectionLanes[1], map2.intersectionLanes[2], map2.intersectionLanes[3], map2.intersectionLanes[4]);
		FirstOrderLogicAgents agent2 = new FirstOrderLogicAgents ( map2.intersectionLanes[1], map2.intersectionLanes[0], map2.intersectionLanes[3], map2.intersectionLanes[2], map2.intersectionLanes[5]);
		FirstOrderLogicAgents agent3 = new FirstOrderLogicAgents ( map2.intersectionLanes[3], map2.intersectionLanes[2], map2.intersectionLanes[1], map2.intersectionLanes[0], map2.intersectionLanes[4]);
		FirstOrderLogicAgents agent4 = new FirstOrderLogicAgents ( map2.intersectionLanes[2], map2.intersectionLanes[3], map2.intersectionLanes[0], map2.intersectionLanes[1], map2.intersectionLanes[5]);		
		FirstOrderLogicAgents agentLeftNorth = new FirstOrderLogicAgents ( map2.intersectionLanes[4], map2.intersectionLanes[5], map2.intersectionLanes[3], map2.intersectionLanes[4], null);
		FirstOrderLogicAgents agentLeftSouth = new FirstOrderLogicAgents ( map2.intersectionLanes[5], map2.intersectionLanes[4], map2.intersectionLanes[4], map2.intersectionLanes[3], null);

		FirstOrderLogicAgents[] fOrderAgentArray = new FirstOrderLogicAgents[6];
		
		fOrderAgentArray[0] = agent1;
		fOrderAgentArray[1] = agent2;
		fOrderAgentArray[2] = agent3;
		fOrderAgentArray[3] = agent4;
		fOrderAgentArray[4] = agentLeftNorth;
		fOrderAgentArray[5] = agentLeftSouth;
		
		fOrderAgentArray[0].lane.laneLight.setCurrentColor(LightColor.green);
		fOrderAgentArray[1].lane.laneLight.setCurrentColor(LightColor.green);
		fOrderAgentArray[2].lane.laneLight.setCurrentColor(LightColor.red);
		fOrderAgentArray[3].lane.laneLight.setCurrentColor(LightColor.red);
		fOrderAgentArray[4].lane.laneLight.setCurrentColor(LightColor.red);
		fOrderAgentArray[5].lane.laneLight.setCurrentColor(LightColor.red);
		
		
		for (int i = 0 ; i < 12; i++) {
			systemTime += 3; //increases system time
			for (int x = 0 ; x < fOrderAgentArray.length; x ++) {
				fOrderAgentArray[x].controlFlowLoop( systemTime);
				
				
			}
			map2.addTrafficToRandomLane(map2.intersectionLanes );
			map2.addPeopleToRandomLane(map2.intersectionLanes);
			trainNumber = addTrain(0, 1, fOrderAgentArray, 20 , trainNumber, systemTime);

			System.out.println(i+" Cycle complete");
			
			for (int y = 0; y < fOrderAgentArray.length; y ++) {
				
			System.out.println(fOrderAgentArray[y].lane.laneName + " current light color: "+ fOrderAgentArray[y].lane.laneLight.getCurrentColor() +" number of cars: "+ fOrderAgentArray[y].lane.getLaneQueue().size() + " changed this turn: "+ fOrderAgentArray[y].lane.lightChangedThisTurn + " TrainLane queue size: "+fOrderAgentArray[y].lane.getTrainQueue().size() + " Pedestrian Queue Size: "+fOrderAgentArray[y].lane.pedestrianQueue.size() );
			if(fOrderAgentArray[y].lane.laneLight.getCurrentColor().equals(LightColor.green) || fOrderAgentArray[y].lane.laneLight.getCurrentColor().equals(LightColor.yellow)) {
				for (int z = 0 ; z < 3; z ++) {
				fOrderAgentArray[y].lane.laneQueue.poll();
				fOrderAgentArray[y].lane.trainQueue.poll();
			}	
			}
			
			
			fOrderAgentArray[y].lane.setActionTakenThisTurn(false);//resets values
			
			
			}
		}
		
		
		
		
		
	}
	public int addTrain(int lane1Index, int lane2Index, FirstOrderLogicAgents[] fOrderAgentArray, int rate , int trainNumber, int systemTime) {
		Random rand = new Random();

		// Obtain a number between [0 - 49].
		int n = rand.nextInt(100);
		
		if (n <= (rate/2)) {
			The_T train = new The_T(trainNumber, systemTime);
			fOrderAgentArray[lane1Index].lane.trainQueue.add(train);
			trainNumber ++;
			System.out.println("Train added");
		}else if(rate/2 <= n && n <= rate){
			The_T train = new The_T(trainNumber, systemTime);
			fOrderAgentArray[lane2Index].lane.trainQueue.add(train);
			System.out.println("Train added");
			trainNumber ++;
		}
		return trainNumber;
	}
	

}
	
	

