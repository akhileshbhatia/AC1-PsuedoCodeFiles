public interface SensingHardware{
	// methods to get the data from the LIDAR, proximity sensors and cameras
}

public class LIDAR implements SensingHardware{
	//get methods to obtain LIDAR data
	getLidarData();
}

public class ProximitySensors implements SensingHardware{
	//get methods to obtain data from proximity sensors
	getProximityData();
}

public class Cameras implements SensingHardware{
	//get methods to obtain camera data
	getCameraData();
}

