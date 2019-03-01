import java.util.LinkedList;
import java.util.Queue;

public class Lane {
	String laneName;
	Queue<Vehicle> laneQueue = new LinkedList<>();
	double waitTimeValue = 0;
	TrafficLight laneLight;
	
	
	
	public Lane (String title) {
		laneName = title;
		laneLight = new TrafficLight(laneName);
		
	}
	
	
	public double calculateWaitTimeValue( int currentTime) {
		if (laneQueue.size() == 0) {
			return waitTimeValue = 0;
		}
		//takes the arrivial time of first car squares it and divides by the number of cars
		waitTimeValue = Math.pow((currentTime - laneQueue.peek().arrivalTime) , 2)/laneQueue.size();
		
		return waitTimeValue;
		
	}


	public String getLaneName() {
		return laneName;
	}


	public void setLaneName(String laneName) {
		this.laneName = laneName;
	}


	public Queue<Vehicle> getLaneQueue() {
		return laneQueue;
	}


	public void setLaneQueue(Queue<Vehicle> laneQueue) {
		this.laneQueue = laneQueue;
	}


	public double getWaitTimeValue() {
		return waitTimeValue;
	}


	public void setWaitTimeValue(double waitTimeValue) {
		this.waitTimeValue = waitTimeValue;
	}
	
}


