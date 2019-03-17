//Store vehicles
public class Vehicle {
     int id;
     int arrivalTime;
      
     
     public Vehicle(int identitiy, int time) {
    	 id = identitiy;
    	 arrivalTime = time;
     }


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
}

