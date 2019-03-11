
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
 * Rule concerning matching color of lane across
 * 		If the initial lane is green and the lane across is not green or Yellow then the L_across must be made green
 * 		L_inital(Green) ∧ L_across¬(Green) ∨ L_across(Yellow) ⇒ L_across(Green)
 * Method that implements these rules: public LightColor changeLightAcross()
 * 
 * 2 rules for initiating change in lights 
 * Rule concerning changing based on waitTime
 * 		If the sum of the wait time for one pair of lanes is greater than another and the first pair is not green and the others are
 * 		The second pair will be changed to yellow
 *  (L_inital(red) ∧ L_across(red)) ∧ (L_adjacentLeft(Green) ∧ L_adjacentRight(Green))
 *  ∧ Σ(L_initial(waitTime) ∧ L_Across(waitTime)) > Σ(L_adjacentLeft(waitTime) ∧ L_adjacentRight(waitTime)) 
 *  ⇒ L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)
 *  	//since changing a light is a two cycle process another rule needs to be added to make the change from yellow to red
 *  	(L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)) ⇒ (L_inital(Green) ∧ L_across(Green)) ∧ (L_adjacentLeft(Red) ∧ L_adjacentRight(Red))
 * Method that implements these rules:changeBasedOnWaitTime()
 * 
 * Rule concerning changing color when not busy
 * 		If a lane is green and the lane across is green and a lane has a queue of a carInQueue value <=3 and the other lane has a queue length < 2 X of other member
 * 		Then change both to yellow
 * 		(L_initial(Green) ∧ L_across(Green)) ∧ ((L_initial(Size <= 3) ∧ L_across(Size <= 6))   ∨  (L_initial(Size <= 6) ∧ L_across(Size <= 3)))
 * 		⇒ L_initial(Yellow) ∧ L_across(Yellow)
 * Method that implements these rules: changeToYellowWhenNotBusy
 * 
 * Rule concerning how a light in yellow can't stay yellow
 * 		If a light is Yellow it must turn to green
 * 		L_initial(yellow) ⇒ L_initial(red)
 * 		L_initial(green) ⇒ L_initial(yellow) ∨ L_initial(green)
 * 		L_initial(red) ⇒ L_initial(red) v L_initial(green)
 * Method that implements these rules: cantStayYellow()
 * 		
 * other rules to consider
 * Rule conceringin how only one change per turn
 * 		If a light changes color it can not change again until next cycle
 * 		For all Lanes there the boolean lightChangedThisTurn is false
 * 		∀ Lanes: lightChangedThisTurn(Lanes)
 */

Lane lane;
Lane across;
Lane adjacentLeft;
Lane adjacentRight;
int totalWaitTime;
int numberOfCars;

public FirstOrderLogicAgents ( Lane currLane, Lane currAcross, Lane currAdjacentLeft, Lane currAdjacentRight){
 lane = currLane;
 across= currAcross;
 adjacentLeft = currAdjacentLeft;
 adjacentRight = currAdjacentRight;
 System.out.println("FO lane agent created");
}


public void controlFlowLoop() {
	//if a change has already been made do not make another
	if (lane.lightChangedThisTurn == true) {
		System.out.println(lane.laneName+ " : change made by other lanes already" );
		return;
	}
	else if (changeToYellowWhenNotBusy() == true){
		System.out.println(lane.laneName+ ": changeToYellowWhenNotBusy()" );

		return;
	}
	
	else if (changeBasedOnWaitTime() == true) {
		System.out.println(lane.laneName+ ": changeBasedOnWaitTime()" );
		return;
	}
	//if the light is already yellow it must go red next
	else if (cantStayYellow() == true) {
		System.out.println(lane.laneName+ ": cantStayYellow()" );
		return;
	}
	//if a light across is changed then a change is made
	else if (changeLightAcross() == true) {
		System.out.println(lane.laneName+ ": changeLightAcross()" );

		return;
	}else {
		System.out.println(lane.laneName+ ": no change made" );
	}
	
	//if the lanes become less crowded they change
	//if (changeToYellowWhenNotBusy() == true){
	//	return;
	//}
	//if (changeBasedOnWaitTime() == true) {
	//	return;
	//}
}


public boolean cantStayYellow() {
	//L_initial(yellow) ⇒ L_initial(red)
	if(lane.laneLight.currentColor.equals(LightColor.yellow)){
		if(lane.lightChangedThisTurn == false) {
		changeToNextColor(lane);
		lane.lightChangedThisTurn = true;}
	}

	return lane.lightChangedThisTurn;
}

public boolean changeToYellowWhenNotBusy() {
	
	//(L_initial(Green) ∧ L_across(Green))
	if(lane.laneLight.currentColor.equals(LightColor.green)&&(across.laneLight.currentColor.equals(LightColor.green))){
		//∧ ((L_initial(Size <= 3) ∧ L_across(Size <= 6))
		if (lane.getLaneQueue().size()<=3 && across.getLaneQueue().size() <= 6) {
			//L_initial(Yellow) ∧ L_across(Yellow)
			changeToNextColor(lane);
			//lane.laneLight.setCurrentColor(LightColor.yellow);
			//lane.lightChangedThisTurn = true;
			changeToNextColor(across);
			//across.laneLight.setCurrentColor(LightColor.yellow);
			//across.lightChangedThisTurn = true;
			return true;
		//∨  (L_initial(Size <= 6) ∧ L_across(Size <= 3))
		}else if(lane.getLaneQueue().size()>=6 && across.getLaneQueue().size() >= 3) {
			//L_initial(Yellow) ∧ L_across(Yellow)
			changeToNextColor(lane);
			//lane.laneLight.setCurrentColor(LightColor.yellow);
			//lane.lightChangedThisTurn = true;
			changeToNextColor(across);
			//across.laneLight.setCurrentColor(LightColor.yellow);
			//across.lightChangedThisTurn = true;
			return true;
		} 
	}
	
	return false;
}

public boolean changeBasedOnWaitTime() {
	//(L_inital(red) ∧ L_across(red))
	if(lane.laneLight.currentColor.equals(LightColor.red)&&(across.laneLight.currentColor.equals(LightColor.red))){
		//(L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)) ⇒ (L_inital(Green) ∧ L_across(Green)) ∧ (L_adjacentLeft(Red) ∧ L_adjacentRight(Red))
		if(adjacentLeft.laneLight.currentColor.equals(LightColor.yellow)&&(adjacentRight.laneLight.currentColor.equals(LightColor.yellow))) {
			changeToNextColor(adjacentLeft);
			changeToNextColor(adjacentRight);
			changeToNextColor(across);
			changeToNextColor(lane);
		}
		
		//(L_adjacentLeft(Green) ∧ L_adjacentRight(Green))
		if(adjacentLeft.laneLight.currentColor.equals(LightColor.green)&&(adjacentRight.laneLight.currentColor.equals(LightColor.green))){
			//Σ(L_initial(waitTime) ∧ L_Across(waitTime)) > Σ(L_adjacentLeft(waitTime) ∧ L_adjacentRight(waitTime))
			if((lane.getWaitTimeValue() + across.getWaitTimeValue()) > (adjacentLeft.getWaitTimeValue()+adjacentRight.getWaitTimeValue()) ) {
				changeToNextColor(adjacentLeft);
				//⇒ L_adjacentLeft(Yellow) ∧ L_adjacentRight(Yellow)
				//adjacentLeft.laneLight.setCurrentColor(LightColor.yellow);
				//adjacentLeft.lightChangedThisTurn = true;
				changeToNextColor(adjacentRight);
				//adjacentRight.laneLight.setCurrentColor(LightColor.yellow);
				//adjacentRight.lightChangedThisTurn = true;
				return true;
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
			changeToNextColor(across);
			return true;
		}
	}
	return false;
}


public boolean changeToNextColor(Lane laneToChange) {
	if(laneToChange.lightChangedThisTurn == true) {
		return false;
	}
	else if (laneToChange.laneLight.currentColor.equals(LightColor.green)){
		//L(Green) ⇒ L(Yellow)
		laneToChange.laneLight.setCurrentColor(LightColor.yellow);
		laneToChange.lightChangedThisTurn = true;
		return true;
	}else if(laneToChange.laneLight.currentColor.equals(LightColor.yellow)){
		//L(Yellow)⇒ L(Red)
		laneToChange.laneLight.setCurrentColor(LightColor.red);
		laneToChange.lightChangedThisTurn = true;
		return true;
	}else {
		//L(Red)⇒ L(Green)
		laneToChange.laneLight.setCurrentColor(LightColor.green);
		laneToChange.lightChangedThisTurn = true;
		return true;
	}
}

}//end class