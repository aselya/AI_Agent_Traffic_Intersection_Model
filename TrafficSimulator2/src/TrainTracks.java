
public class TrainTracks extends Lane{
	TrafficLight trackLight;
	String trackName;
	
	double adjustedWaitValue = 10;//represents a train being 10x more valueable than a single vehicle
	public TrainTracks(String title, TrafficLight light) {
		super(title);
		trackLight = super.getLaneLight();
		trackName = title+ "_trainTrack";
		System.out.println(trackName + ": created");
		
		
		
		// TODO Auto-generated constructor stub
	}
	public TrafficLight getTrackLight() {
		return trackLight;
	}
	public void setTrackLight(TrafficLight trackLight) {
		this.trackLight = trackLight;
	}
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public double getAdjustedWaitValue() {
		return adjustedWaitValue;
	}
	public void setAdjustedWaitValue(double adjustedWaitValue) {
		this.adjustedWaitValue = adjustedWaitValue;
	}
	
	
	

}
