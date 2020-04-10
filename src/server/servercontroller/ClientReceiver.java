package server.servercontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientReceiver implements Runnable {

	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private String line;

	public ClientReceiver(Socket s) {
		try {
			socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			socketOut = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				line = socketIn.readLine();
			} catch (IOException e) {
				socketOut.write("Invalid input, please try again");
				continue;
			}
			switch (line) {
			case "search":

			case "add":

			case "remove":

			case "viewall":

			case "viewstudent":

			case "quit":

			default:

			}
		}

	}

}
