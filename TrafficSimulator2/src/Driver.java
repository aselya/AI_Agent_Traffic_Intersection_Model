public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver me = new Driver();
		me.DoIt();
		me.DoIt2();
	}

	private void DoIt() {
		// TODO Auto-generated method stub
		IntersectionQueues map = new IntersectionQueues();
		
		for (int i = 0; i < 30; i ++) {
			map.addTrafficToRandomLane(map.intersectionLanes );
		}
		
		map.compareLaneWaitTimeValues();
		//map.removeVehiclesFromLane(map.eastStraight, 10);
		map.IntersectionToString(map.intersectionLanes);
		
		
		SupervisingAgent supAgent = new SupervisingAgent(map.intersectionLanes, map);
		//System.out.println(supAgent.intersectionLanes.toString());
	}

	
	public void DoIt2() {
		IntersectionQueues map2 = new IntersectionQueues();
		
		for (int i = 0; i < 30; i ++) {
			map2.addTrafficToRandomLane(map2.intersectionLanes );
		}
		
		
		FirstOrderLogicAgents agent1 = new FirstOrderLogicAgents ( map2.intersectionLanes[0], map2.intersectionLanes[1], map2.intersectionLanes[2], map2.intersectionLanes[3]);
		FirstOrderLogicAgents agent2 = new FirstOrderLogicAgents ( map2.intersectionLanes[1], map2.intersectionLanes[0], map2.intersectionLanes[3], map2.intersectionLanes[2]);
		FirstOrderLogicAgents agent3 = new FirstOrderLogicAgents ( map2.intersectionLanes[3], map2.intersectionLanes[2], map2.intersectionLanes[1], map2.intersectionLanes[0]);
		FirstOrderLogicAgents agent4 = new FirstOrderLogicAgents ( map2.intersectionLanes[2], map2.intersectionLanes[3], map2.intersectionLanes[0], map2.intersectionLanes[1]);

		FirstOrderLogicAgents[] fOrderAgentArray = new FirstOrderLogicAgents[4];
		
		fOrderAgentArray[0] = agent1;
		fOrderAgentArray[1] = agent2;
		fOrderAgentArray[2] = agent3;
		fOrderAgentArray[3] = agent4;
		
		fOrderAgentArray[0].lane.laneLight.setCurrentColor(LightColor.green);
		fOrderAgentArray[1].lane.laneLight.setCurrentColor(LightColor.green);
		fOrderAgentArray[2].lane.laneLight.setCurrentColor(LightColor.red);
		fOrderAgentArray[3].lane.laneLight.setCurrentColor(LightColor.red);
		
		for (int i = 0 ; i < 10; i++) {
			for (int x = 0 ; x < fOrderAgentArray.length; x ++) {
				fOrderAgentArray[x].controlFlowLoop();
				
			}
			System.out.println(i+" Cycle complete");
			for (int y = 0; y < fOrderAgentArray.length; y ++) {
				
			System.out.println(fOrderAgentArray[y].lane.laneName + " current light color: "+ fOrderAgentArray[y].lane.laneLight.getCurrentColor() +" number of cars: "+ fOrderAgentArray[y].lane.getLaneQueue().size() + " changed this turn: "+ fOrderAgentArray[y].lane.lightChangedThisTurn);
			if(fOrderAgentArray[y].lane.laneLight.getCurrentColor().equals(LightColor.green) || fOrderAgentArray[y].lane.laneLight.getCurrentColor().equals(LightColor.yellow)) {
				for (int z = 0 ; z < 3; z ++) {
				fOrderAgentArray[y].lane.laneQueue.poll();
			}	
			}
			
			
			fOrderAgentArray[y].lane.lightChangedThisTurn = false;//resets values
			
			
			}
		}
		
		
		
		
		
		
	}
	
	
}
