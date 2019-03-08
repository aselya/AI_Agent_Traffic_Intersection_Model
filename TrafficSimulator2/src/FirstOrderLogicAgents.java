
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
 *		 	
 * 
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
 *  (L_inital(red) ∧ L_across(red)) ∧ (L_adjacent1(Green) ∧ L_adjacent2(Green))
 *  ∧ Σ(L_initial(waitTime) ∧ L_Across(waitTime)) > Σ(L_adjacent1(waitTime) ∧ L_adjacent2(waitTime)) 
 *  ⇒ L_adjacent1(Yellow) ∧ L_adjacent2(Yellow)
 * 
 * Rule concerning changing color when not busy
 * 		If a lane is green and the lane across is green and a lane has a queue of a carInQueue value < 3 and the other lane has a queue length < 2 of other member
 * 		Then change both to yellow
 * 		
 */
	
Lane lane;
Lane across;
Lane adjacent1;
Lane adjacent2;
int totalWaitTime;
int numberOfCars;

public LightColor changeBasedOnWaitTime() {
	//(L_inital(red) ∧ L_across(red))
	if(lane.laneLight.currentColor.equals(LightColor.red)&&(across.laneLight.currentColor.equals(LightColor.red))){
		//(L_adjacent1(Green) ∧ L_adjacent2(Green))
		if(adjacent1.laneLight.currentColor.equals(LightColor.green)&&(adjacent2.laneLight.currentColor.equals(LightColor.green))){
			//Σ(L_initial(waitTime) ∧ L_Across(waitTime)) > Σ(L_adjacent1(waitTime) ∧ L_adjacent2(waitTime))
			if((lane.getWaitTimeValue() + across.getWaitTimeValue()) > (adjacent1.getWaitTimeValue()+adjacent2.getWaitTimeValue()) ) {
				//⇒ L_adjacent1(Yellow) ∧ L_adjacent2(Yellow)
				adjacent1.laneLight.setCurrentColor(LightColor.yellow);
				adjacent2.laneLight.setCurrentColor(LightColor.yellow);
			}
		}
	}
	return null;
}

//L_inital(Green) ∧ L_across¬(Green) ∨ L_across(Yellow) ⇒ L_across(Green)
public LightColor changeLightAcross() {
	//L_inital(Green)
	if(lane.laneLight.currentColor.equals(LightColor.green)) {
		//L_across¬(Green) ∨ L_across(Yellow)
		if(!across.laneLight.currentColor.equals(LightColor.green)||across.laneLight.currentColor.equals(LightColor.yellow) ) {
			//⇒ L_across(Green) 
			across.laneLight.setCurrentColor(LightColor.green);
		}
	}
	
	return null;
	
}


public LightColor changeToNextColor() {
	
	if (lane.laneLight.currentColor.equals(LightColor.green)){
		//L(Green) ⇒ L(Yellow)
		lane.laneLight.setCurrentColor(LightColor.yellow);
	}else if(lane.laneLight.currentColor.equals(LightColor.yellow)){
		//L(Yellow)⇒ L(Red)
		lane.laneLight.setCurrentColor(LightColor.red);
	}else {
		//L(Red)⇒ L(Green)
		lane.laneLight.setCurrentColor(LightColor.green);
	}
	return lane.laneLight.currentColor;
}

}//end class