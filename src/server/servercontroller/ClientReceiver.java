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
		String[] args;
		try {
			while (true) { // login loop
				args = socketIn.readLine().split(" ");
				Student student = controller.attemptLogin(args[0], args[1]);
				if (student != null) {
					theStudent = student;
					socketOut.println(1);
					break;
				}
				socketOut.println(0);
			}

			while (true) { // once logged in, read commands
				args = socketIn.readLine().split(" ");

				switch (args[0]) {
				case "get":
					if (args[1].equals("catalog")) {
						socketOut.println(catalogue.getCourseList().size());
						for (Course c : catalogue.getCourseList()) {
							socketOut.println(c.getCourseName() + " " + c.getCourseNum());
							socketOut.println(c.getOfferingList().size());
							for (CourseOffering co : c.getOfferingList()) {
								socketOut.println(co.getOfferingRegList().size() + "/" + co.getSecCap() + " students enrolled");
							}
						}
					} else if (args[1].equals("enrolled")) {
						socketOut.println(theStudent.getRegList().size());
						for (Registration r : theStudent.getRegList()) {
							socketOut.println(r.getTheOffering().getTheCourse().getCourseName() + " "
									+ r.getTheOffering().getTheCourse().getCourseNum());
							socketOut.println(r.getTheOffering().getSecNum());
							socketOut.println(r.getTheOffering().getOfferingRegList().size() + "/" + r.getTheOffering().getSecCap()
									+ " students enrolled");
						}
					}
					break;

				case "enroll":
					if (theStudent.getRegList().size() == 6) {
						socketOut.println("fail");
						socketOut.println("You cannot register for more than 6 courses");
						break;
					}
					Course found2 = catalogue.searchCat(args[1], Integer.parseInt(args[2]));
					if (found2 == null) {
						socketOut.println("fail");
						socketOut.println("Enrollment failed, course not found");
					} else if (theStudent.hasCourse(found2)) {
						socketOut.println("fail");
						socketOut.println("You are already registered for this course");
					} else {
						CourseOffering offering = found2.getCourseOfferingAt(Integer.parseInt(args[3]) - 1);
						if (offering == null) {
							socketOut.println("fail");
							socketOut.println("Enrollment failed, no offering found");
						} else if (offering.getOfferingRegList().size() == offering.getSecCap()) {
							socketOut.println("fail");
							socketOut.println("Enrollment failed, section is full");
						} else {
							Registration reg = new Registration();
							reg.completeRegistration(theStudent, offering);
							socketOut.println("Registration successful");
						}
					}
					break;

				case "unenroll":
					Course found3 = catalogue.searchCat(args[1], Integer.parseInt(args[2]));
					socketOut.println(theStudent.removeCourse(found3));
					break;

				case "add":
					// TODO: For the admin section
					break;

				case "quit":
					IOException e = new IOException();
					throw e;

				default:
					socketOut.print("An error occured");
					continue;
				}
			}
		} catch (IOException e) {
			// AFAIK will only throw IOException on unrecoverable errors, terminate if
			// somehow occurs (should be properly closed by client)
			// Could happen if server/client loses internet connection?
			System.out.println("Error communicating with client!. Terminating thread...");
			e.printStackTrace();
		}
	}

}
