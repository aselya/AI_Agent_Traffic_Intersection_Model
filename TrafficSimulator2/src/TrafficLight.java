//stores the traffic light 
public class TrafficLight {
	LightColor currentColor;
	int numberOfLanes;
	String lightName;
	
	
	
	public TrafficLight( String name) {
		lightName = lightName + name+"TrafficLight";
		System.out.println("new traffic ligth created named: " +lightName);
	}




	public String getLightName() {
		return lightName;
	}




	public void setLightName(String lightName) {
		this.lightName = lightName;
	}




	public LightColor getCurrentColor() {
		return currentColor;
	}




	public void setCurrentColor(LightColor currentColor) {
		this.currentColor = currentColor;
	}




	public int getNumberOfLanes() {
		return numberOfLanes;
	}




	public void setNumberOfLanes(int numberOfLanes) {
		this.numberOfLanes = numberOfLanes;
	}





	
	
}
