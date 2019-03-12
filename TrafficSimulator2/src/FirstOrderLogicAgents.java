
public class FirstOrderLogicAgents {
/*
 * The goal of this is to simulate how an intersection operating purley on first order logic works
 * 
 * First order logic assumptions
 * 		For all Lanes there is a light
 * 		∀ Lanes: Light(Lanes)
 * 		
 * 		For all Lights there are only 3 colors
 * 		∀ Light: Color(red) ∨ Color(yellow) ∨ Color(green)
 * 
 * Rule concerning order of color
 * 	1)If a Light is Green the next color must be yellow.
 * 	2)If a Light is Yellow the next color must be red.
 * 	3)If a Light is Red the next color must be green.
 * 		L(Green) ⇒ L(Yellow) 
 * 		L(Yellow)⇒ L(Red)
 * 		L(Red)⇒ L(Green)
 * Method that implements these rules: changeToNextColor()
 * 
 * Rule concerning lane not changing from red if adjacent lanes are not red
 * 		if the initial lane is red and the adjacent lanes are not red, no change can be made
 * 		(L_inital(Red)) ∧ ¬(L_adjacentLeft(Red) ∧ L_adjacentRight(red)) ⇒ ¬L_initial(Green)
 * Method that implements these rules: lookBothWaysBeforeChanging()
 * 
 * Rule concerning matching color of lane across
 * 		If the initial lane is green and the lane across is not green or Yellow then the L_across must be made green
 * 		L_inital(Green) ∧ L_across¬(Green) ∨ L_across(Yellow) ⇒ L_across(Green)
 * Method that implements these rules: public LightColor changeLightAcross()
 * 
 * 2 rules for initiating change in lights 
 * Rule concerning changing based on waitTime
 * 		If the sum of the wait time for one pair of lanes, with one train being worth 10X a vehicle, is greater than another and the first pair is not green and the others are
 * 		The second pair will be changed to yellow
 *  (L_inital(red) ∧ L_across(red)) ∧ ((L_adjacentLeft(Green) ∧ L_adjacentRight(Green))v((L_adjacentLeft(red) ∧ L_adjacentRight(red)))
 *  ∧ Σ(L_initial( Σ(waitTime + 10*L_initial(train) ) ∧ L_Across(Σ(waitTime + 10*L_across(train)) > Σ(L_adjacentLeft(Σ(waitTime + 10*L_adjacentLeft(train)) ∧ L_adjacentRight(Σ(waitTime + 10*L_adjacentRight(train))) 
 *  ⇒ L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)
 *  	//since changing a light is a two cycle process another rule needs to be added to make the change from yellow to red
 *  	(L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)) ⇒ (L_inital(Green) ∧ L_across(Green)) ∧ (L_adjacentLeft(Red) ∧ L_adjacentRight(Red))
 * Method that implements these rules:changeBasedOnWaitTime()
 * 
 * Rule concerning changing color when not busy
 * 		If a lane and the the one across do not have train
 * 		If a lane is green and the lane across is green and a lane has a queue of a carInQueue value <=3 and the other lane has a queue length <= 2 X of other member
 * 		(¬(L_initial(Train) ∧  ¬(L_across(Train) ) ∧
 * 		((L_initial(Green) ∧ L_across(Green)) ∧ ((L_initial(Size <= 3) ∧ L_across(Size <= 6))   
 * 		∨  ((L_initial(Size <= 6) ∧ L_across(Size <= 3)))
 * 		 ⇒ L_initial(Yellow) ∧ L_across(Yellow)
 * Method that implements these rules: changeToYellowWhenNotBusy
 * 
 * Rule concerning how a light in yellow can't stay yellow
 * 		If a light is Yellow it must turn to green
 * 		L_initial(yellow) ⇒ L_initial(red)
 * 		L_initial(green) ⇒ L_initial(yellow) ∨ L_initial(green)
 * 		L_initial(red) ⇒ L_initial(red) v L_initial(green)
 * Method that implements these rules: cantStayYellow()
 * 		
 * Rule concerning how only one change per turn
 * 		If a light changes color it can not change again until next cycle
 * 		For all Lanes there the boolean lightChangedThisTurn is false
 * 		∀ Lanes: lightChangedThisTurn(True) ⇒ ¬(Lanes(changeToNextColor))
 * Method that implements this rule: lanesHaveChanged()
 * 
 * Rule concerning how a left lane can not have greens or yellow going across or adjacent lights be red
 * 		if the lane across, adjacent left and adjacent right are not red the left can not be green
 * 		L_left(Red) v ¬L_across(Red) v ¬L_adjacentRight(Red) v ¬L_adjacentLeft(Red) ⇒ L_Left(red)
 * Method that implements this rule: noLeft()
 * 
 * Rule concerning how a straight lane can not cross a green or yellow left lane
 * 		if a left lane is not red 
 * 		L_initial(red) ∧ ¬L_acrossLeft(red) ∧ ¬L_adjacentRightTurn(red) ∧ ¬L_adjacentLeftTurn(red)  ⇒ L_initial(red)
 * Method that implements this rule: noStraightAgainstLeft()
 */

Lane lane;
Lane across;
Lane adjacentLeft;
Lane adjacentRight;
Lane leftTurnLane;
int totalWaitTime;
int numberOfCars;

public FirstOrderLogicAgents ( Lane currLane, Lane currAcross, Lane currAdjacentLeft, Lane currAdjacentRight, Lane leftTurn){
 lane = currLane;
 across= currAcross;
 adjacentLeft = currAdjacentLeft;
 adjacentRight = currAdjacentRight;
 leftTurnLane = leftTurn;
 System.out.println("FO lane agent created");
}

public boolean lanesHaveChanged() {
	//∀ Lanes: lightChangedThisTurn(True) ⇒ ¬(Lanes(changeToNextColor))
	if(lane.getActionTakenThisTurn() == true) {
		return true;
	}else if(across.getActionTakenThisTurn() == true) {
		return true;
	}else if (adjacentLeft.getActionTakenThisTurn() == true) {
		return true;
	}else if(adjacentRight.getActionTakenThisTurn() == true) {
			return true;
	}else if(across.getActionTakenThisTurn() == true) {
				return true;
	}else return false;
}

public void controlFlowLoop() {
	//if a change has already been made do not make another
	if (lane.getActionTakenThisTurn() == true) {
		System.out.println(lane.laneName+ " : change made by other lanes already" );
		return;
	}else if(lookBothWaysBeforeChanging() == true) {
		System.out.println(lane.laneName+ ": lookBothWaysBeforeChanging()" );
		return;
	}
	//if the light is already yellow it must go red next
	else if (cantStayYellow() == true) {
		System.out.println(lane.laneName+ ": cantStayYellow()" );
		return;
	}else if (noLeft()==true) {
		System.out.println(lane.laneName+ ": noLeft()" );
	}else if(noChangeIfLeftisNotRed () == true) {
		System.out.println(lane.laneName+ ": noChangeIfLeftisNotRed ()" );
	}
	
	
	else if (changeBasedOnWaitTime() == true) {
		System.out.println(lane.laneName+ ": changeBasedOnWaitTime()" );
		return;
	}
	else if (changeToYellowWhenNotBusy() == true){
		System.out.println(lane.laneName+ ": changeToYellowWhenNotBusy()" );

		return;
	}
	
	//if a light across is changed then a change is made
	else if (changeLightAcross() == true) {
		System.out.println(lane.laneName+ ": changeLightAcross()" );

		return;
	}else {
		System.out.println(lane.laneName+ ": no change made" );
	}
}


public boolean noChangeIfLeftisNotRed () {
	if(leftTurnLane != null) {
		if (!leftTurnLane.laneLight.currentColor.equals(LightColor.red)) {
			lane.actionTakenThisTurn = true;
			return true;
		}
	}
	
	return false;
}



 
public boolean noLeft() {
	//L_left()
	if (lane.leftTurnLane == true) {
		//L_left(Red)
		if(lane.laneLight.currentColor.equals(LightColor.red)) {
			//v ¬L_across(Red) v ¬L_adjacentRight(Red) v ¬R_adjacent(Red)
			
			if(!across.laneLight.currentColor.equals(LightColor.red)||
				!adjacentRight.getLaneLight().getCurrentColor().equals(LightColor.red)||
				!adjacentLeft.getLaneLight().getCurrentColor().equals(LightColor.red)) {
				lane.setActionTakenThisTurn(true);
				return true;
			}
		}
	}
	
	return false;
}


public boolean lookBothWaysBeforeChanging() {
//(L_inital(Red)) ∧ ¬(L_adjacentLeft(Red) ∧ L_adjacentRight(red)) 
	if(lane.laneLight.currentColor.equals(LightColor.red)) {
		//∧ ¬(L_adjacentLeft(Red) ∧ L_adjacentRight(red))
		if ((adjacentRight.laneLight.currentColor.equals(LightColor.green)||adjacentRight.laneLight.currentColor.equals(LightColor.yellow)) && adjacentLeft.laneLight.currentColor.equals(LightColor.green)||adjacentLeft.laneLight.currentColor.equals(LightColor.yellow)){
			//⇒ ¬L_initial(Green)
			lane.setActionTakenThisTurn(true);
			return true;
		}
	}
	return false;
}



public boolean cantStayYellow() {
	//L_initial(yellow) ⇒ L_initial(red)
	if(lane.laneLight.currentColor.equals(LightColor.yellow)){
		if(lane.getActionTakenThisTurn() == false) {
			
		changeToNextColor(lane);
		lane.setActionTakenThisTurn(true);}
	}

	return lane.getActionTakenThisTurn();
}

public boolean changeToYellowWhenNotBusy() {
	//(¬(L_initial(Train) ∧  ¬(L_across(Train) )
	if(lane.getTrainQueue().size() == 0 && across.getTrainQueue().size() == 0) {
	
	//(L_initial(Green) ∧ L_across(Green))
	if(lane.laneLight.currentColor.equals(LightColor.green)&&(across.laneLight.currentColor.equals(LightColor.green))){
		//∧ ((L_initial(Size <= 3) ∧ L_across(Size <= 6) ∧ (¬(L_initial(Train) ∧  ¬(L_across(Train) )
		
		if (lane.getLaneQueue().size()<=3 && across.getLaneQueue().size() <= 6) {
			//L_initial(Yellow) ∧ L_across(Yellow)
			changeToNextColor(lane);
			//lane.laneLight.setCurrentColor(LightColor.yellow);
			//lane.setActionTakenThisTurn(true);
			changeToNextColor(across);
			//across.laneLight.setCurrentColor(LightColor.yellow);
			//across.setActionTakenThisTurn(true);
			return true;
		//∨  (L_initial(Size <= 6) ∧ L_across(Size <= 3))
		}else if(lane.getLaneQueue().size()<=6 && across.getLaneQueue().size() <= 3) {
			//L_initial(Yellow) ∧ L_across(Yellow)
			changeToNextColor(lane);
			//lane.laneLight.setCurrentColor(LightColor.yellow);
			//lane.setActionTakenThisTurn(true);
			changeToNextColor(across);
			//across.laneLight.setCurrentColor(LightColor.yellow);
			//across.setActionTakenThisTurn(true);
			return true;
		} 
	}
	}
	return false;
}

public boolean changeBasedOnWaitTime() {
	System.out.println("pair 1" +(((lane.getLaneQueue().size()+ 10*lane.getTrainQueue().size()) + (across.getLaneQueue().size()+ 10*across.getTrainQueue().size())) +" pair 2: " + ((adjacentLeft.getLaneQueue().size() + 10*adjacentLeft.getTrainQueue().size()) +(adjacentRight.getLaneQueue().size() + + 10*adjacentRight.getTrainQueue().size())) ));
	
	//since this method initiates changes across all lanes, it first must be checked to make sure none of the lanes have changed yet
	/*if (lanesHaveChanged() == true) {
		return false;
	}
	*/
	//(L_inital(red) ∧ L_across(red))
	if(lane.laneLight.currentColor.equals(LightColor.red)&&(across.laneLight.currentColor.equals(LightColor.red))){
		//(L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)) ⇒ (L_inital(Green) ∧ L_across(Green)) ∧ (L_adjacentLeft(Red) ∧ L_adjacentRight(Red))
		if(adjacentLeft.laneLight.currentColor.equals(LightColor.yellow)&&(adjacentRight.laneLight.currentColor.equals(LightColor.yellow))) {
			changeToNextColor(adjacentLeft);
			changeToNextColor(adjacentRight);
			changeToNextColor(across);
			changeToNextColor(lane);
			return true;
		}
		
		//(L_adjacentLeft(Green) ∧ L_adjacentRight(Green))
		if(adjacentLeft.laneLight.currentColor.equals(LightColor.green)&&(adjacentRight.laneLight.currentColor.equals(LightColor.green))){
			 // Σ(L_initial( Σ(waitTime + 10*L_initial(train) ) ∧ L_Across(Σ(waitTime + 10*L_across(train)) > Σ(L_adjacentLeft(Σ(waitTime + 10*L_adjacentLeft(train)) ∧ L_adjacentRight(Σ(waitTime + 10*L_adjacentRight(train))) 

			//Σ(L_initial(waitTime) ∧ L_Across(waitTime)) > Σ(L_adjacentLeft(waitTime) ∧ L_adjacentRight(waitTime))
			if(((lane.getLaneQueue().size()+ 10*lane.getTrainQueue().size()) + (across.getLaneQueue().size()+ 10*across.getTrainQueue().size())) > ((adjacentLeft.getLaneQueue().size() + 10*adjacentLeft.getTrainQueue().size()) +(adjacentRight.getLaneQueue().size() + 10*adjacentRight.getTrainQueue().size())) ) {
				changeToNextColor(adjacentLeft);
				//⇒ L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)
				//adjacentLeft.laneLight.setCurrentColor(LightColor.yellow);
				//adjacentLeft.setActionTakenThisTurn(true);
				changeToNextColor(adjacentRight);
				//adjacentRight.laneLight.setCurrentColor(LightColor.yellow);
				//adjacentRight.setActionTakenThisTurn(true);
				//prevents further changes
				lane.setActionTakenThisTurn(true);
				across.setActionTakenThisTurn(true);
				return true;
			}
			}
			
			
			//(L_adjacentLeft(Red) ∧ L_adjacentRight(Red))
			if(adjacentLeft.laneLight.currentColor.equals(LightColor.red)&&(adjacentRight.laneLight.currentColor.equals(LightColor.red))){
				System.out.println("adjright and left are red");
				 // Σ(L_initial( Σ(waitTime + 10*L_initial(train) ) ∧ L_Across(Σ(waitTime + 10*L_across(train)) > Σ(L_adjacentLeft(Σ(waitTime + 10*L_adjacentLeft(train)) ∧ L_adjacentRight(Σ(waitTime + 10*L_adjacentRight(train))) 

				//Σ(L_initial(waitTime) ∧ L_Across(waitTime)) > Σ(L_adjacentLeft(waitTime) ∧ L_adjacentRight(waitTime))
				if(((lane.getLaneQueue().size()+ 10*lane.getTrainQueue().size()) + (across.getLaneQueue().size()+ 10*across.getTrainQueue().size())) > ((adjacentLeft.getLaneQueue().size() + 10*adjacentLeft.getTrainQueue().size()) +(adjacentRight.getLaneQueue().size()+ 10*adjacentRight.getTrainQueue().size())) ) {
					changeToNextColor(lane);
					//⇒ L_initial(Yellow) ∧ L_across(Yellow)
					changeToNextColor(across);
					//prevents further changes
					adjacentRight.setActionTakenThisTurn(true);
					adjacentLeft.setActionTakenThisTurn(true);
					return true;
				}else {
					changeToNextColor(adjacentLeft);
					changeToNextColor(adjacentRight);
					lane.setActionTakenThisTurn(true);
					across.setActionTakenThisTurn(true);
				}
			
			
			}
		}
		
	return false;
}

//L_inital(Green) ∧ L_across¬(Green) ∨ L_across(Yellow) ⇒ L_across(Green)
public boolean changeLightAcross() {
	//L_inital(Green)
	if(lane.laneLight.currentColor.equals(LightColor.green)) {
		//L_across¬(Green) ∨ L_across(Yellow)
		if(!across.laneLight.currentColor.equals(LightColor.green)||across.laneLight.currentColor.equals(LightColor.yellow) ) {
			//⇒ L_across(Green) 
			if(across.getActionTakenThisTurn() == false) {
			changeToNextColor(across);}
			return true;
		}
	}
	return false;
}


public boolean changeToNextColor(Lane laneToChange) {
	if(laneToChange.getActionTakenThisTurn() == true) {
		return false;
	}
	else if (laneToChange.laneLight.currentColor.equals(LightColor.green)){
		//L(Green) ⇒ L(Yellow)
		laneToChange.laneLight.setCurrentColor(LightColor.yellow);
		laneToChange.setActionTakenThisTurn(true);
		return true;
	}else if(laneToChange.laneLight.currentColor.equals(LightColor.yellow)){
		//L(Yellow)⇒ L(Red)
		laneToChange.laneLight.setCurrentColor(LightColor.red);
		laneToChange.setActionTakenThisTurn(true);
		return true;
	}else {
		//L(Red)⇒ L(Green)
		laneToChange.laneLight.setCurrentColor(LightColor.green);
		laneToChange.setActionTakenThisTurn(true);
		return true;
	}
}

}//end class