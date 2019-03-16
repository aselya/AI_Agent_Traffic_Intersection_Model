import java.util.LinkedList;
import java.util.Queue;

public class Lane {
	String laneName;
	int numberOfVehicles;
	int totalWaitTime;
	double averageWaitTime = 0;
	double waitTimeValue = 0;
	boolean actionTakenThisTurn;
	boolean leftTurnLane;
	boolean hasPedestrians;
	TrafficLight laneLight;
	FirstOrderLogicAgents firstOrderAgent;
	Queue<Vehicle> laneQueue = new LinkedList<>();
	Queue<The_T> trainQueue = new LinkedList<>();
	Queue<Pedestrians> pedestrianQueue = new LinkedList<>();
	
	
	
	
	public Lane (String title, boolean left, boolean pedestrians) {
		laneName = title;
		hasPedestrians = pedestrians;
		laneLight = new TrafficLight(laneName);
		leftTurnLane = left;
		
	}
	
	public double getAverageWaitTime (int currentTime){
		double average = 0;
		
		int tempWaitTimeValue = 0;
		int tempTotalVehicles = 0;
		for (int i = 0; i < laneQueue.size(); i++) {
		Vehicle currentVehicle = laneQueue.remove();
		tempWaitTimeValue += (currentTime - currentVehicle.arrivalTime); //calculates the difference between arrival time and current time
		laneQueue.add(currentVehicle);
		tempTotalVehicles++;
		
		}
		
		for (int i = 0; i < trainQueue.size(); i++) {
			Vehicle train = trainQueue.remove();
			tempWaitTimeValue += (currentTime - train.arrivalTime); //calculates the difference between arrival time and current time
			laneQueue.add(train);
			tempTotalVehicles++;
			
			}
		
		
		tempWaitTimeValue += totalWaitTime;
		average = tempWaitTimeValue/numberOfVehicles;
		System.out.println("Average wait time for "+laneName+ ": is "+ average);
		return average;
	}
	
	
	public double calculateWaitTimeValue( int currentTime) {
		if (laneQueue.size() == 0) {
			return waitTimeValue = 0;
		}
		//takes the arrivial time of first car squares it and divides by the number of cars
		
		//this cycles through the queue adding the total wait times for each vehicle
		waitTimeValue = 0;
		for (int i = 0; i < laneQueue.size(); i++) {
		Vehicle currentVehicle = laneQueue.remove();
		waitTimeValue += (currentTime - currentVehicle.arrivalTime); //calculates the difference between arrival time and current time
		laneQueue.add(currentVehicle);
		}
		
		waitTimeValue = 0;
		for (int i = 0; i < trainQueue.size(); i++) {
		The_T currentVehicle = trainQueue.remove();
		waitTimeValue += (currentTime - currentVehicle.arrivalTime); //calculates the difference between arrival time and current time
		trainQueue.add(currentVehicle);
		}
		
		//cycles through the pedestrian queue and adds wait times, but each pedestrian is worth less than a car
		for (int i = 0; i < pedestrianQueue.size(); i++) {
			Pedestrians person = pedestrianQueue.remove();
			waitTimeValue += (currentTime - person.arrivalTime)/4; //calculates the difference between arrival time and current time and adjusts for people being less important to traffic flow than cars
			pedestrianQueue.add(person);
			}
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


	public TrafficLight getLaneLight() {
		return laneLight;
	}


	public void setLaneLight(TrafficLight laneLight) {
		this.laneLight = laneLight;
	}




	public Boolean getActionTakenThisTurn() {
		return actionTakenThisTurn;
	}


	public void setActionTakenThisTurn(Boolean actionTakenThisTurn) {
		this.actionTakenThisTurn = actionTakenThisTurn;
	}


	public FirstOrderLogicAgents getFirstOrderAgent() {
		return firstOrderAgent;
	}


	public void setFirstOrderAgent(FirstOrderLogicAgents firstOrderAgent) {
		this.firstOrderAgent = firstOrderAgent;
	}


	public Queue<The_T> getTrainQueue() {
		return trainQueue;
	}


	public void setTrainQueue(Queue<The_T> trainQueue) {
		this.trainQueue = trainQueue;
	}


	public boolean isLeftTurnLane() {
		return leftTurnLane;
	}


	public void setLeftTurnLane(boolean leftTurnLane) {
		this.leftTurnLane = leftTurnLane;
	}


	public Queue<Pedestrians> getPedestrianQueue() {
		return pedestrianQueue;
	}


	public void setPedestrianQueue(Queue<Pedestrians> pedestrianQueue) {
		this.pedestrianQueue = pedestrianQueue;
	}


	public void setActionTakenThisTurn(boolean actionTakenThisTurn) {
		this.actionTakenThisTurn = actionTakenThisTurn;
	}


	public boolean isHasPedestrians() {
		return hasPedestrians;
	}


	public void setHasPedestrians(boolean hasPedestrians) {
		this.hasPedestrians = hasPedestrians;
	}


	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}


	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}


	public int getTotalWaitTime() {
		return totalWaitTime;
	}


	public void setTotalWaitTime(int totalWaitTime) {
		this.totalWaitTime = totalWaitTime;
	}
	
}


