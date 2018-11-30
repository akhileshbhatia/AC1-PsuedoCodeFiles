Public class VehicleSystem{
//communication with vehicle hardware using acceleration, braking, steering 
//and gear service 

	SteeringService steeringService;
	AccelerationService accelerationService;
	BrakingService brakingService;
	GearService gearService;


	Public VehicleSystem(){

	}

	public void forward(Angle angle, Speed speed){
	//Accelerate forward in vehicleAngle direction with vehicleSpeed km/hr
	//Comunicate with the hardware system.
	}

	public void reverse(Angle angle, Speed speed){
	//Accelerate backward with vehicleAngle direction with vehicleSpeed km/hr.
	//Comunicate with the hardware system.
	}

	public void neutral(){
	// Apply brakes
	//Communicate with hardware system
	}

	//other methods to turn vehicle system on and off
}
