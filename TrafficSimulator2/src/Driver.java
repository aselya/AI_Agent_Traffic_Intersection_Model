import java.util.Random;



public class Driver {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver me = new Driver();
		int totalCycles =18;

		double preScheduled = Math.round(me.prescheduled(totalCycles)* 100d) / 100d;
		double logic = Math.round(me.UseAI(totalCycles)* 100d) / 100d;
		
		
		
		if (logic < preScheduled){
			System.out.println("Total Avereage for first order logic simulation was better than the prescheduled is: " + logic + "vs"+ preScheduled);

		}else{
			System.out.println("Total Avereage for the prescheduled simulation was better than the first order logic is: " + preScheduled + "vs"+ logic);

		}
		

	}
	
	public double prescheduled(int cycles){
IntersectionQueues noAI = new IntersectionQueues();
NoAI_Agent noAgent =  new NoAI_Agent(noAI.intersectionLanes);
		
		int trainNumber = 0;
		int systemTime = 0;
		boolean rushHour = false;
		
		noAI.intersectionLanes[0].laneLight.setCurrentColor(LightColor.green);
		noAI.intersectionLanes[1].laneLight.setCurrentColor(LightColor.green);
		noAI.intersectionLanes[2].laneLight.setCurrentColor(LightColor.red);
		noAI.intersectionLanes[3].laneLight.setCurrentColor(LightColor.red);
		noAI.intersectionLanes[4].laneLight.setCurrentColor(LightColor.red);
		noAI.intersectionLanes[5].laneLight.setCurrentColor(LightColor.red);
		
		for (int i = 0; i < 30; i ++) {
			noAI.addTrafficToRandomLane(noAI.intersectionLanes, systemTime, rushHour );
		}
		
		System.out.println("REGULAR SCHEDULING //////////////////////////////////////////////");
		
		for (int z = 0; z < cycles; z ++){
		systemTime ++;
		noAI.addTrafficToRandomLane(noAI.intersectionLanes, systemTime, rushHour );
		int x = ((z) % 9);
		System.out.println("X = "+ x);
		
		if( 0 <=x  && x<=2){
			//laneNorthAndSouthGreen
			//laneNorthAndSouthYellow
			//laneNorthAndSouthRed
		noAgent.controlFlow(noAI.intersectionLanes[0], noAI.intersectionLanes[1], systemTime);
		}
		
		if( 3 <=x  && x<=5){
			//laneNorthAndSouthLeftsGreen
			//laneNorthAndSouthLeftsYellow
			//laneNorthAndSouthLeftsRed
		noAgent.controlFlow(noAI.intersectionLanes[2], noAI.intersectionLanes[3], systemTime);
		}
		
		if( 6 <=x  && x<=8){
			//laneEastAndWest
			//laneEastAndWestYellow
			//laneEastAndWestRed
		noAgent.controlFlow(noAI.intersectionLanes[4], noAI.intersectionLanes[5], systemTime);
		}
		
		}
		double totalAve = 0;
		for (int x = 0 ; x < noAI.intersectionLanes.length; x ++) {
			totalAve += noAI.intersectionLanes[x].getAverageWaitTime(systemTime);
		}
		totalAve = totalAve/ noAI.intersectionLanes.length;
		System.out.println("Total Avereage for prescheduled logic simulation is: " + totalAve);

		return totalAve;
	}



	public double UseAI(int cycles) {
		IntersectionQueues map2 = new IntersectionQueues();
		
		int trainNumber = 0;
		int systemTime = 0;
		int totalCycles = cycles;
		boolean rushHour = false;
		
		for (int i = 0; i < 30; i ++) {
			map2.addTrafficToRandomLane(map2.intersectionLanes, systemTime, rushHour );
			
			
			
		}
		
																		//Lane currLane, Lane currAcross, Lane currAdjacentLeft, Lane currAdjacentRight, Lane leftTurn
		//FirstOrderLogicAgents agent1 = new FirstOrderLogicAgents ( map2.intersectionLanes[0], map2.intersectionLanes[1], map2.intersectionLanes[2], map2.intersectionLanes[3], null);
		//FirstOrderLogicAgents agent2 = new FirstOrderLogicAgents ( map2.intersectionLanes[1], map2.intersectionLanes[0], map2.intersectionLanes[3], map2.intersectionLanes[2], null);
		
		FirstOrderLogicAgents agent1 = new FirstOrderLogicAgents ( map2.intersectionLanes[0], map2.intersectionLanes[1], map2.intersectionLanes[2], map2.intersectionLanes[3], map2.intersectionLanes[4]);
		FirstOrderLogicAgents agent2 = new FirstOrderLogicAgents ( map2.intersectionLanes[1], map2.intersectionLanes[0], map2.intersectionLanes[3], map2.intersectionLanes[2], map2.intersectionLanes[5]);
		FirstOrderLogicAgents agent3 = new FirstOrderLogicAgents ( map2.intersectionLanes[3], map2.intersectionLanes[2], map2.intersectionLanes[1], map2.intersectionLanes[0], map2.intersectionLanes[4]);
		FirstOrderLogicAgents agent4 = new FirstOrderLogicAgents ( map2.intersectionLanes[2], map2.intersectionLanes[3], map2.intersectionLanes[0], map2.intersectionLanes[1], map2.intersectionLanes[5]);		
		FirstOrderLogicAgents agentLeftNorth = new FirstOrderLogicAgents ( map2.intersectionLanes[4], map2.intersectionLanes[5], map2.intersectionLanes[3], map2.intersectionLanes[4], map2.intersectionLanes[0]);
		FirstOrderLogicAgents agentLeftSouth = new FirstOrderLogicAgents ( map2.intersectionLanes[5], map2.intersectionLanes[4], map2.intersectionLanes[4], map2.intersectionLanes[3], map2.intersectionLanes[1]);		
		//FirstOrderLogicAgents agentLeftNorth = new FirstOrderLogicAgents ( map2.intersectionLanes[4], map2.intersectionLanes[5], map2.intersectionLanes[3], map2.intersectionLanes[4], null);
		//FirstOrderLogicAgents agentLeftSouth = new FirstOrderLogicAgents ( map2.intersectionLanes[5], map2.intersectionLanes[4], map2.intersectionLanes[4], map2.intersectionLanes[3], null);

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
		
		
			
			for (int x = 0 ; x < fOrderAgentArray.length; x ++) {
				fOrderAgentArray[x].lane.setActionTakenThisTurn(true);
			}
		
		
		for (int i = 0 ; i < totalCycles; i++) {
			systemTime += 1; //increases system time
			for (int x = 0 ; x < fOrderAgentArray.length; x ++) {
				fOrderAgentArray[x].controlFlowLoop( systemTime);
				
				
			}
			map2.addTrafficToRandomLane(map2.intersectionLanes, systemTime, rushHour );
			
			map2.addPeopleToRandomLane(map2.intersectionLanes, systemTime, rushHour);
			trainNumber = addTrain(0, 1, fOrderAgentArray, 20 , trainNumber, systemTime);

			System.out.println(i+" Cycle complete");
			
			//for (int y = fOrderAgentArray.length-1; y < 0; y --){
			for (int y = 0; y < fOrderAgentArray.length; y ++) {
				Lane currLane = fOrderAgentArray[y].lane;
			System.out.println(currLane.laneName + " current light color: "+ currLane.laneLight.getCurrentColor() +" number of cars: "+ currLane.getLaneQueue().size() + " changed this turn: "+ currLane.actionTakenThisTurn + " TrainLane queue size: "+currLane.getTrainQueue().size() + " Pedestrian Queue Size: "+currLane.pedestrianQueue.size() );
			if(currLane.laneLight.getCurrentColor().equals(LightColor.green) || currLane.laneLight.getCurrentColor().equals(LightColor.yellow)) {
				for (int z = 0 ; z < 3; z ++) {
					
				if(currLane.laneQueue.size()>0){
					int newTotal = currLane.getTotalWaitTime() +  systemTime -  currLane.laneQueue.peek().arrivalTime ;
					currLane.setTotalWaitTime(newTotal);
				}
				currLane.laneQueue.poll();
				currLane.trainQueue.poll();
				while (currLane.pedestrianQueue.size()> 0) {
					currLane.pedestrianQueue.poll();
				}
			}	
			}
			
			
			fOrderAgentArray[y].lane.setActionTakenThisTurn(false);//resets values
			
			
			}
		}
		
		double totalAve = 0;
		
		for (int x = 0 ; x < fOrderAgentArray.length; x ++) {
			totalAve +=fOrderAgentArray[x].lane.getAverageWaitTime(systemTime);
		}
		totalAve = totalAve/fOrderAgentArray.length;
		System.out.println("Total Avereage for first order logic simulation is: " + totalAve);
		return totalAve;
		
		
	}
	public int addTrain(int lane1Index, int lane2Index, FirstOrderLogicAgents[] fOrderAgentArray, int rate , int trainNumber, int systemTime) {
		Random rand = new Random();

		// Obtain a number between [0 - 99].
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
	
	

