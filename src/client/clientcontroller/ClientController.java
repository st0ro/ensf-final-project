// Written by A. Price
package client.clientcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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
			//socket = new Socket("localhost", 25565);
			socketIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch (IOException e) {
			System.out.println("Connection could not be established. Please ensure server is running. Exiting...");
			//System.exit(1);
		}
	}
	
	public boolean attemptLogIn(String user, String password) {
		try {
			socketOut.println(user + " " + password);
			int result = Integer.parseInt(socketIn.readLine());
			switch(result) { //0 = no such user, 1 = student login, 2 = admin login
			case 0:
				return false;
			case 1:
				retrieveCourses(0);
				//retrieveCourses(1);
				clientView.setAdmin(this);
				return true;
			case 2:
				retrieveCourses(0);
				clientView.setAdmin(this);
				return true;
			}
		} catch (IOException e) {
			System.out.println("Communication error! Exiting...");
			return false;
		}
		return true;
	}
	
	public String attemptEnroll(String name, int section) {
		try {
			socketOut.println("enroll "+ name + " " + section);
			String result = socketIn.readLine();
			if(result.equals("fail")) {
				return socketIn.readLine();
			}
		} catch (IOException e) {
			System.out.println("Communication error! Exiting...");
			System.exit(1);
		}
		return null;
		
	}
	
	public String attemptUnenroll(String name, int section) {
		try {
			socketOut.println("unenroll "+ name + " " + section);
			String result = socketIn.readLine();
			if(result.equals("fail")) {
				return socketIn.readLine();
			}
		} catch (IOException e) {
			System.out.println("Communication error! Exiting...");
			System.exit(1);
		}
		return null;
		
	}
	
	public void retrieveCourses(int type) { //0 = all, 1 = enrolled
		switch(type) {
		case 0:
			socketOut.println("get catalog");
			break;
		case 1:
			socketOut.println("get enrolled");
			break;
		}
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			int classes = Integer.parseInt(socketIn.readLine());
			for(int i = 0; i < classes; ++i) {
				String name = socketIn.readLine();
				int sections = Integer.parseInt(socketIn.readLine());
				for(int j = 1; j <= sections; ++j) {
					if(j == 1) {
						String[] line = {name, socketIn.readLine(), socketIn.readLine()};
						list.add(line);
					} else {
						String[] line = {"", socketIn.readLine(), socketIn.readLine()};
						list.add(line);
					}
				}
			}
		} catch(IOException e) {
			System.out.println("Communication error! Exiting...");
			System.exit(1);
		}
		list.trimToSize();
		String[][] temp = new String[list.size()][3];
		for(int i = 0; i < list.size(); i++) {
			temp[i] = list.get(i);
		}
		clientView.fillTable(temp, type);
	}
	
	public String attemptAdminAddOperation(String courseName, String seats) {
		try {
			socketOut.println("admin");
			socketOut.println(courseName);
			socketOut.println(seats);
			String result = socketIn.readLine();
			if(result.equals("fail")) {
				return socketIn.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void quit() {
		socketOut.println("quit");
		socketOut.close();
		try {
			socketIn.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String args[]) {
		ClientController controller = new ClientController(new ClientView(), new LogInView());
		controller.connect();
	}
	
}
