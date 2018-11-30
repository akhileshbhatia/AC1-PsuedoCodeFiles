	public class Controller{

		private Processor imageProcessor;
		private Processor signalProcessor;
		private Processor postProcessor;
		private Sensors sensors;
		private VehicleSystem navigationService;
		private String carState;

		public Controller(){
			initializeSensors();
			initializeProcessors();
			initializeVehicleServices();
			carState = "idle";
		}

		public void initializeSensors(){
			sensors = new Sensors();
		}

		public void initializeProcessors(){
			imageProcessor = new ImageProcessor();
			signalProcessor = new SignalProcessor();
			postProcessor = new PostProcessor();
		}

		public void initializeVehicleServices(){
			navigationService = new VehicleSystem();
		}

		public void terminateProcessor(){
			// code to turn off the processors.
		}

		public void terminateVehicleServices(){
			// code to turn off the Vehicle system.
		}

		public void terminateSensors(){
			// code to turn off the sensor
		}

		//Decision maker to decide which direction to move
		public Status getDecision(){
		//use combination of data from image, signal and post processor to take next movement decision(example below)
		//status.gear = 'Forward'  --- decided on the basis of input from image and signal processor
		//status.speed = 5 --- decided on the basis of image processor
		//status.angle = Math.Pi/2 ---decided on the basis of image processor and cameras
		}

		//method to get the state of car after every move
		public String getCurrentCarLocation(){
			//get the current state of the car from the processors
			return currentCarLocation;
		}

		public void setCarState(){
			//update car state on the basis of user's current location
		}

				
		public static void Main(String args[]){

			GUI gui = new GUI();
			String command = gui.getCommand();
			gui.addListener(
				new onCommandReceived(Location userlocation){
					//initiliazing decision maker (which will initialize the processors, sensors and vehicle system)
					Controller controller = new Controller();
					Status status;
					String finalCarState;
					if(command == "Park"){
						int maxSlots=0, maxAttempts=5, m, n;
						outerloop:
						for(n=0 ; n<maxAttempts ; n++){
							Location[] locations = controller.imageProcessor.getLocations();
							maxSlots = locations.length();
							for(m=0; m<maxSlots; m++){
								finalCarState = controller.drive("Parked",locations[m]);
								if(finalCarState == "Parked" || finalCarState == "Parking failed"){
									break outerloop;	
								}
							}
						}
					}
					else{
						finalCarState = controller.drive("Un-Parked",userlocation);
					}
					//call  services based on car state
					switch(finalCarState){
						case "Parked":
						gui.generateNotification(Parking.successNotification);
						controller.terminateProcessor();
						controller.terminateVehicleServices();
						controller.terminateSensors();
						break;

						case "Parking failed":
						gui.generateNotification(Parking.failureNotification);
						controller.drive("Un-Parked",userlocation);
						break;

						case "Un-Parked":
						gui.generateNotification(Parking.unparkedSuccessNotification);
						controller.terminateProcessor();
						controller.terminateSensors();
						break;
					}
				}

			}

			public String drive(String finalCarState, Location location){
			Location currentCarLocation=getCurrentCarLocation();
			while(currentCarLocation != location){
				status = controller.getDecision();

				switch(status.gear){  
					case "Forward":
					//navigation service will control the hardware
					controller.navigationService.forward(status.angle,status.speed);
					setCarState();
					break;

					case "Reverse":
					controller.navigationService.reverse(status.angle,status.speed);
					setCarState();
					break;

					case "Stop":
					controller.navigationService.neutral();
					setCarState();
					break;
				}

				currentCarLocation = controller.getCurrentCarLocation();
			}

			return carState;
		}
		}