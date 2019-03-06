public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver me = new Driver();
		me.DoIt();
	}

	private void DoIt() {
		// TODO Auto-generated method stub
		IntersectionQueues map = new IntersectionQueues();
		
		for (int i = 0; i < 30; i ++) {
			map.addTrafficToRandomLane(map.intersectionLanes );
		}
		
		map.compareLaneWaitTimeValues();
		//map.removeVehiclesFromLane(map.eastStraight, 10);
		map.IntersectionToString(map.intersectionLanes);
		
		
		SupervisingAgent supAgent = new SupervisingAgent(map.intersectionLanes, map);
		System.out.println(supAgent.intersectionLanes.toString());
	}

	
	
}
