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
	private String[] arr;
	private CourseCatalogue catalogue;
	private Student theStudent;
	private ServerController controller;
	
	public ClientReceiver(Socket s, CourseCatalogue c, ServerController controller) {
		try {
			socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			socketOut = new PrintWriter(s.getOutputStream(), true);
			catalogue = c;
			this.controller = controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String line = "";
		String[] args;
		try {
			while(true) { //login loop
				args = socketIn.readLine().split(" ");
				Student student = controller.attemptLogin(args[0], args[1]);
				if(student != null) {
					theStudent = student;
					break;
				}
			}
			
			while(true) { //once logged in, read commands
				args = socketIn.readLine().split(" ");
				switch (args[0]) {
				case "get":
					if(args[1].equals("catalog")) {
						socketOut.println(catalogue.getCourseList().size());
						for(Course c: catalogue.getCourseList()) {
							socketOut.println(c.getCourseName() + " " + c.getCourseNum());
							socketOut.println(c.getOfferingList().size());
							for(CourseOffering co: c.getOfferingList()) {
								socketOut.println(co.getSecNum() + "/" + co.getSecCap() + " students enrolled");
							}
						}
					}
					else if(args[1].equals("enrolled")) {
						socketOut.println(theStudent.getRegList().size());
						for(Registration r: theStudent.getRegList()) {
							socketOut.println(r.getTheOffering().getTheCourse().getCourseName() + " " + r.getTheOffering().getTheCourse().getCourseNum());
							socketOut.println("1");
							socketOut.println(r.getTheOffering().getSecNum() + "/" + r.getTheOffering().getSecCap() + " students enrolled");
						}
					}
					break;
				case "enroll":
					// TODO check if theStudent is able to enroll let client know
					break;
				case "unenroll":
					// TODO check if theStudent if able to drop and if they can't pray to god because if that happens we fucked up
					break;
				case "add": // TODO change once admin is implemented
					line = socketIn.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				arr = line.split(" ");
				Course found2 = catalogue.searchCat(arr[0], Integer.parseInt(arr[1]));
				if (found2 == null)
					socketOut.write("Enrollment failed\nCourse not found");
				else {
					CourseOffering offering = found2.getCourseOfferingAt(Integer.parseInt(arr[3]) - 1);
					if (offering == null)
						socketOut.write("Enrollment failed\nNo offering found");
					else if (offering.getOfferingRegList().size() == offering.getSecCap())
						socketOut.write("Enrollment failed\nSection is full");
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

			case "unenroll":
				Course found3 = catalogue.searchCat(arr[1], Integer.parseInt(arr[2]));
				theStudent.removeCourse(found3);
				break;

			default:
				socketOut.print("An error occured");
				continue;
			}
		} catch (IOException e) {
				// AFAIK will only throw IOException on unrecoverable errors, terminate if somehow occurs (should be properly closed by client)
				// Could happen if server/client loses internet connection?
				System.out.println("Error communicating with client!. Terminating thread...");
		}
	}
}
