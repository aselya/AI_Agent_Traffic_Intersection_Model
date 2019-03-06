
public class TrafficLightAgent {

	TrafficLight pairedLight;
	Lane pairedLane;
	
	Lane lane;
	String name;
	
	
	
	/*AI functions that traffic light agent needs to have
	 * When lane is empty/low and light is green, switch to yellow
	 *      greenToYellowWhenNotBusy(Lane lane)
	 * When light is a yellow, switch to red, red -> green, green -> yellow
	 * 		lightNextColor (Lane lane) 
	 * When paired light is green, switch to green too
	 * 		swtichToGreenWhenPairedLightIsGreen( Lane lane, Lane pairedLane)
	 * Control Flow loop to make sure everything above is implimented
	*/
	
	
	public void controlFlow() {
		boolean actionTaken = false;
		if (swtichToGreenWhenPairedLightIsGreen( lane, pairedLane) != null) {
			System.out.println("matching paired light ");
			actionTaken = true;
			}else {
				System.out.println("paired light did not initiate switch to green");
				actionTaken = false;
			}
		
		if(actionTaken == false) {
			System.out.println("checking to see if the lane is busy: greenToYellowWhenNotBusy");
			greenToYellowWhenNotBusy(lane);
		}
		
	}
	
	
	
	//if yellow then go red
	public LightColor YellowToRed(Lane lane) {
		System.out.println("LightColor YellowToRed/lane.laneLight.currentColor.equals" + lane.laneLight.currentColor);
		if(lane.laneLight.currentColor.equals(LightColor.yellow)) {
			System.out.println("LightColor YellowToRed/lane.laneLight.currentColor.equals" + lane.laneLight.currentColor);

			lane.laneLight.setCurrentColor(LightColor.red);
			return LightColor.red;
		}else {
			System.out.println("YellowToRed: selected lane light is not yellow");
		}
		return null;
		
	}	
	
	//if green and queue < 3 switch to yellow
	public LightColor greenToYellowWhenNotBusy(Lane lane) {
			
		System.out.println("LightColor greenToYellowWhenNotBusy/lane.laneLight.currentColor.equals" + lane.laneLight.currentColor);
		if (lane.laneLight.currentColor.equals(LightColor.green)) {
			System.out.println("LightColor greenToYellowWhenNotBusy/lane.laneQueue.size()" + lane.laneQueue.size());
			if (lane.laneQueue.size() < 3) {
				lane.laneLight.setCurrentColor(LightColor.yellow);
				System.out.println("NEW LightColor greenToYellowWhenNotBusy/lane.laneLight.currentColor.equals" + lane.laneLight.currentColor);
				return LightColor.yellow;
			}else {
				System.out.println("LightColor greenToYellowWhenNotBusy/lane.laneQueue.size()" + lane.laneQueue.size() +"to busy to auto change");
				return LightColor.green;
			}
		}else {
			System.out.println("LightColor greenToYellowWhenNotBusy/ lane not green");
			}
		return null;
	}
	
	//if pair light is green then also turn green
	public LightColor swtichToGreenWhenPairedLightIsGreen( Lane lane, Lane pairedLane) {
		System.out.println("TrafficLightAgent/switchToGreen initiated");

		if (pairedLane.equals(null)) {
			System.out.println("TrafficLightAgent/switchToGreen no light paired");
			return null;
		}
		//if light is currently red and the paired light is green then switch
		System.out.println("TrafficLightAgent/switchToGreen current light color: " + lane.laneLight.getCurrentColor());

		if (lane.laneLight.getCurrentColor().equals(LightColor.red)) {
			System.out.println("TrafficLightAgent/switchToGreen current light color: " + lane.laneLight.getCurrentColor());
			//**System.out.println("TrafficLightAgent/switchToGreen paired light color: " + pairedLight.lightName + " "+ pairedLight.currentColor);
			System.out.println("TrafficLightAgent/switchToGreen paired light color: " + pairedLane.laneLight.lightName + " "+ pairedLane.laneLight.currentColor);

			
			//if(pairedLight.currentColor.equals(LightColor.green)) {
			if(pairedLane.laneLight.currentColor.equals(LightColor.green)) {	
				System.out.println("TrafficLightAgent/switchToGreen pair light is green switch to green too");
				lane.laneLight.setCurrentColor(LightColor.green);
				//System.out.println("TrafficLightAgent/switchToGreen "+lane.laneLight.getCurrentColor() + " : " + pairedLight.getCurrentColor() );	
				System.out.println("TrafficLightAgent/switchToGreen "+lane.laneLight.getCurrentColor() + " : " + pairedLane.laneLight.getCurrentColor() );	
				return lane.laneLight.getCurrentColor();
			}
		}
		System.out.println("swtichToGreen/ returning null");
		return null;
	}
	
	
	
	
	
	
	
	
	//logic for switching light colors
	//uses a finitie state machine with colors bing the states
	public LightColor lightNextColor (Lane lane) {
		//if a light is yellow then it turns red next
		if (lane.laneLight.currentColor.equals(LightColor.yellow)){
				System.out.println(lane.laneName +": had a current color of " + lane.laneLight.currentColor);
			lane.laneLight.setCurrentColor(LightColor.red);
				System.out.println(lane.laneName +": has a current color of " + lane.laneLight.currentColor);
			return lane.laneLight.currentColor;
			//if a light is red then it turns green
		}else if (lane.laneLight.currentColor.equals(LightColor.red)){
				System.out.println(lane.laneName +": had a current color of " + lane.laneLight.currentColor);
			lane.laneLight.currentColor = LightColor.green;
				System.out.println(lane.laneName +": has a current color of " + lane.laneLight.currentColor);
			return lane.laneLight.currentColor;
			//if a light is green then it turns yellow
		}else if (lane.laneLight.currentColor.equals(LightColor.green)){
				System.out.println(lane.laneName +": had a current color of " + lane.laneLight.currentColor);
			lane.laneLight.currentColor = LightColor.yellow;
				System.out.println(lane.laneName +": now has a current color of " + lane.laneLight.currentColor);

			return lane.laneLight.currentColor;}
			System.out.println("light change failed");
		return null;
	}
	
	
	
	
	
	public TrafficLightAgent (Lane assignedLane, String laneName) {
		lane = assignedLane;
		name = laneName+"_TrafficLightAgent";
		System.out.println(name + ": agent created");
	}



	public TrafficLight getPairedLight() {
		return pairedLight;
	}



	public void setPairedLight(TrafficLight pairedLight) {
		this.pairedLight = pairedLight;
	}



	public Lane getLane() {
		return lane;
	}



	public void setLane(Lane lane) {
		this.lane = lane;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Lane getPairedLane() {
		return pairedLane;
	}



	public void setPairedLane(Lane pairedLane) {
		this.pairedLane = pairedLane;
	}
	
}
