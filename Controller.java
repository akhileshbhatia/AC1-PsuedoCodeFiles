	public class Controller{

		private Processor imageProcessor;
		private Processor signalProcessor;
		private Processor postProcessor;
		private Sensors sensors;
		private VehicleSystem navigationService;
		private String carState;

		public Controller(){
			this.initializeSensors();
			this.initializeProcessors();
			this.initializeVehicleServices();
			this.carState = "Idle";
		}

		public void initializeSensors(){
			this.sensors = new Sensors();
		}

		public void initializeProcessors(){
			this.imageProcessor = new ImageProcessor();
			this.signalProcessor = new SignalProcessor();
			this.postProcessor = new PostProcessor();
		}

		public void initializeVehicleServices(){
			this.navigationService = new VehicleSystem();
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
			return this.currentCarLocation;
		}

		public void setCarState(){
			//update car state on the basis of user's current location
		}

		public String getCarState(){
			//evaluate car state and return the corresponding string
			//possible car states are - Idle, Moving, Slot_Reached,Parked,Un-parked
			this.carState;
		}

		public boolean evaluateSlot(){
			//evaluates the slot using the cameras and the proximity sensors (through the processors)
			//returns true if slot ok and false if slot not ok
		}

		public String drive(String expectedCarState, Location location){
			Location currentCarLocation=this.getCurrentCarLocation();
			boolean slotEvaluated = false;
			while(currentCarLocation != location){
				status = this.getDecision();
				switch(status.gear){  
					case "Forward":
					//navigation service will control the hardware
					this.navigationService.forward(status.angle,status.speed);
					this.setCarState();
					break;

					case "Reverse":
					this.navigationService.reverse(status.angle,status.speed);
					this.setCarState();
					break;

					case "Stop":
					this.navigationService.neutral();
					this.setCarState();
					break;
				}
				currentCarLocation = this.getCurrentCarLocation();
				carState = this.getCarState();
				if(expectedCarState == "Parked" && slotEvaluated == false){
					if(carState == "Slot_Reached"){
						slotEvaluated = true;
						boolean slotOk = this.evaluateSlot(location);
						if(!slotOk){
							break;
						}
					}
				}
			}
			return carState;
		}
		public static void Main(String args[]){
			GUI gui = new GUI();
			String command = gui.getCommand();
			gui.addListener(
				new onCommandReceived(Location userlocation){
					//initiliazing decision maker (which will initialize the processors, sensors and vehicle system)
					Controller controller = new Controller();
					Status status;
					String finalCarState="";
					if(command == "Park"){
						int maxSlots=0, maxAttempts=5, m, n;
						outerloop:
						for(n=0 ; n<maxAttempts ; n++){
							Location[] locations = controller.imageProcessor.getLocations();
							maxSlots = locations.length();
							for(m=0; m<maxSlots; m++){
								finalCarState = controller.drive("Parked",locations[m]);
								if(finalCarState == "Parked"){
									break outerloop;	
								}
							}
						}
						finalCarState != "Parked" ? "Parking Failed" : finalCarState;
					}
					else{ // when command == "Un-Parked"
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
		}