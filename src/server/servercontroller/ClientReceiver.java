package server.servercontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.servermodel.Course;
import server.servermodel.CourseCatalogue;
import server.servermodel.CourseOffering;
import server.servermodel.Registration;
import server.servermodel.Student;

public class ClientReceiver implements Runnable {

	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private String line;
	private String[] arr;
	private CourseCatalogue catalogue;
	private Student theStudent;

	public ClientReceiver(Socket s, CourseCatalogue c, Student st) {
		try {
			socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			socketOut = new PrintWriter(s.getOutputStream(), true);
			catalogue = c;
			theStudent = st;
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
				try {
					line = socketIn.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				arr = line.split(" ");
				Course found1 = catalogue.searchCat(arr[0], Integer.parseInt(arr[1]));
				if (found1 == null)
					socketOut.write("Course not found");
				else
					socketOut.write(found1.toString());
				break;

			case "add":
				try {
					line = socketIn.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				arr = line.split(" ");
				Course found2 = catalogue.searchCat(arr[0], Integer.parseInt(arr[1]));
				if (found2 == null)
					socketOut.write("Enrollment failed\nCourse not found");
				else {
					CourseOffering offering = found2.getCourseOfferingAt(Integer.parseInt(arr[2]) - 1);
					if (offering == null)
						socketOut.write("Enrollment failed\nNo offering found");
					else if (offering.getOfferingRegList().size() == offering.getSecCap())
						socketOut.write("Enrollment failed\nSection is full");
					else {
						Registration reg = new Registration();
						reg.startRegistration(theStudent, found2);
						socketOut.write("Registration successful");
					}
				}
				break;

			case "remove":

			case "viewall":

			case "viewstudent":

			case "quit":

			default:

			}
		}

	}

}
