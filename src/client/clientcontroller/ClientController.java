package client.clientcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import client.clientview.ClientView;
import client.clientview.LogInView;

public class ClientController {
	
	private ClientView clientView;
	private LogInView logInView;
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;

	public ClientController(ClientView clientView, LogInView logInView) {
		this.clientView = clientView;
		this.clientView.setListeners(this);
		this.logInView = logInView;
		this.logInView.setListeners(this);
		this.logInView.setLocationRelativeTo(this.clientView);;
	}
	
	public void connect() {
		try {
			socket = new Socket("70.77.251.191", 25565);
			socketIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch (IOException e) {
			System.out.println("Connection could not be established. Please ensure server is running. Exiting...");
			//System.exit(1);
		}
	}
	
	public boolean attemptLogIn(String user, String password) {
		/*try {
			socketOut.println(user);
			socketOut.println(password);
			int result = Integer.parseInt(socketIn.readLine());
			switch(result) { //0 = no such user, 1 = student login, 2 = admin login
			case 0:
				return false;
			case 1:
				return true;
			case 2:
				clientView.setAdmin();
				return true;
			}
		} catch (IOException e) {
			System.out.println("Communication error! Exiting...");
			System.exit(1);
		}*/
		return true;
	}
	public static void main (String args[]) {
		ClientController controller = new ClientController(new ClientView(), new LogInView());
		controller.connect();
	}
	
}
