package client.clientcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import client.clientview.ClientView;
import client.clientview.LogInView;

/**
 * Client controller class handling the client view and communications with the server.
 * @author Alexander Price
 * @since April 13, 2020
 */
public class ClientController {
	
	private ClientView clientView;
	private LogInView logInView;
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	/**
	 * Creates a controller with the given ClientView and LogInView.
	 * @param clientView ClientView to display to
	 * @param logInView LogInView to get log in info from
	 */
	public ClientController(ClientView clientView, LogInView logInView) {
		this.clientView = clientView;
		this.clientView.setListeners(this);
		this.logInView = logInView;
		this.logInView.setListeners(this);
		this.logInView.setLocationRelativeTo(this.clientView);;
	}
	
	/**
	 * Attempts to connect to the server, catching if the server fails to connect and exiting.
	 */
	public void connect() {
		try {
			socket = new Socket("70.77.251.191", 25565);
			socketIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch (IOException e) {
			System.out.println("Connection could not be established. Please ensure server is running. Exiting...");
			System.exit(1);
		}
	}
	
	/**
	 * Attempts to log in to the connected server with the given credentials. Returns whether the operation was successful or not.
	 * @param user username to log in with
	 * @param password password to log in with
	 * @return whether or not the operation was successful
	 */
	public boolean attemptLogIn(String user, String password) {
		try {
			socketOut.println(user + " " + password);
			int result = Integer.parseInt(socketIn.readLine());
			switch(result) { //0 = no such user, 1 = student login, 2 = admin login
			case 0:
				return false;
			case 1:
				retrieveCourses(0);
				retrieveCourses(1);
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
	
	/**
	 * Attempts to enroll in a given course using the connected server. Returns null if successful, or the server response is the operation failed.
	 * @param name name and number of the course to enroll in
	 * @param section section of the course to enroll in
	 * @return server response
	 */
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
	
	/**
	 * Attempts to unenroll from a given course using the connected server. Returns null if successful, or the server response is the operation failed.
	 * @param name name and number of the course to unenroll from
	 * @param section section of the course to unenroll from
	 * @return server response
	 */
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
	
	/**
	 * Retrieves courses from the server. Parameter type is either 0 for the entire course catalog, or 1 for all enrolled
	 * courses for the currently logged in student.
	 * @param type type of course set to retrieve
	 */
	public void retrieveCourses(int type) {
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
	
	/**
	 * Attempts to add either a course or offering as an admin using the connected server. Server will create an offering
	 * if the course already exists, or create a new course with one offering if the course doesn't already exist.
	 * Returns null if successful, or the server response is the operation failed.
	 * @param courseName name and number of the course
	 * @param seats number of seats in the offering
	 * @return server response
	 */
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
	
	/**
	 * Cleanly disconnects from the server and closes all I/O streams.
	 */
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
