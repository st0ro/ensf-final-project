package client.clientcontroller;

import java.io.IOException;
import java.net.Socket;
import client.clientview.ClientView;

public class ClientController {
	
	private ClientView view;
	private Socket socket;

	public ClientController(ClientView clientView) {
		view = clientView;
		view.setController(this);
	}
	
	public void connect() {
		/*try {
			socket = new Socket("2134324234", 25565);
		} catch (IOException e) {
			System.out.println("Connection could not be established. Please ensure server is running. Exiting...");
			System.exit(1);
		}*/
	}

	public static void main (String args[]) {
		ClientController controller = new ClientController(new ClientView());
		controller.connect();
	}
	
}
