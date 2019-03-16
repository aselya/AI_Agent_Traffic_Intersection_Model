
public class NoAI_Agent {
	
	/*
	 * This class is used to show what happens when there is no AI agent
	 * 
	 * 
	 * 
	 */
	
	
	//simulation is set to start with north and south straight both green
	int turn =0;
	Lane[] lanesArray;
	
	
	public NoAI_Agent(Lane[] lanes){
		lanesArray = lanes;
	}
	
	public void controlFlow(Lane lane1,Lane lane2, int systemTime){
		changeLanePair (lane1, lane2);
		
		for (int y = 0; y < lanesArray.length; y ++) {
			Lane currLane = lanesArray[y];
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
		}
				
	}
	
	
	
	
	public void changeLanePair (Lane lane1, Lane lane2){
		changeToNextColor(lane1);
		changeToNextColor(lane2);	
	}
	
	//changes the light to the next color
	 public boolean changeToNextColor(Lane laneToChange) {
	 
	  if (laneToChange.laneLight.currentColor.equals(LightColor.green)) {
	   //L(Green) ⇒ L(Yellow)
	   laneToChange.laneLight.setCurrentColor(LightColor.yellow);
	   laneToChange.setActionTakenThisTurn(true);
	   return true;
	  } else if (laneToChange.laneLight.currentColor.equals(LightColor.yellow)) {
	   //L(Yellow)⇒ L(Red)
	   laneToChange.laneLight.setCurrentColor(LightColor.red);
	   laneToChange.setActionTakenThisTurn(true);
	   return true;
	  } else {
	   //L(Red)⇒ L(Green)
	   laneToChange.laneLight.setCurrentColor(LightColor.green);
	   laneToChange.setActionTakenThisTurn(true);
	   return true;
	  }
	 }
	
}
