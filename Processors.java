public interface Processor{
	public Locations[] getLocations();
	//other methods for the image, post and signal processor
}

public class ImageProcessor implements Processor{
	@Override
	public Locations[] getLocations(){
		//gives array of locations (i.e. the latitude and longitude ) of all slots found in one search attempt
	}

	//other methods to combine input from cameras and lidar and pass to controller for decision making
}

public class PostProcessor implements Processor{
	//generates image for the image processor to consume

}


public class SignalProcessor implements Processor{
	//takes the signal input from proximity sensors to send to controller for making decisions regarding collision
}