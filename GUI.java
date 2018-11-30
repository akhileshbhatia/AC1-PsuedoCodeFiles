//GUI

public class AppGUI{

	private static DatagramSocket socket = null;
	private DatagramSocket socket2;
	private InetAddress address;

	String inputCommand = null;
	String status= null;
	

	public static void main(String[] args) throws IOException {
		//on command received, initialize inputCommand
		broadcast(inputCommand, InetAddress.getByName("255.255.255.255"));

	}

//Broadcasting park/un-park Command

	    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
		        socket = new DatagramSocket();
		        socket.setBroadcast(true);
 
		        byte[] buffer = broadcastMessage.getBytes();
 
		        DatagramPacket packet 
		          = new DatagramPacket(buffer, buffer.length, address, 4445);
		        socket.send(packet);
		        socket.close();
	    }

//Get parking/unparking status
	public static String generateNotification(NotificationType) {
	// display notification
	}



}