package server.servercontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.servermodel.Course;
import server.servermodel.CourseCatalogue;
import server.servermodel.CourseOffering;
import server.servermodel.DBManager;
import server.servermodel.Registration;
import server.servermodel.Student;

/**
 * A class for receiving and sending data to and from a client. 
 * Supports multi-threading.
 * 
 * @author James Zhou
 *
 */
public class ClientReceiver implements Runnable {

	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private CourseCatalogue catalogue;
	private Student theStudent;
	private ServerController controller;
	private DBManager database;

	/**
	 * Creates a ClientReceiver object from the provided arguments.
	 * @param s Socket the client-server communication is on
	 * @param c The catalogue of courses
	 * @param controller The main server running 
	 * @param db A DBManager for communication to the database
	 */
	public ClientReceiver(Socket s, CourseCatalogue c, ServerController controller, DBManager db) {
		try {
			socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			socketOut = new PrintWriter(s.getOutputStream(), true);
			catalogue = c;
			database = db;
			this.controller = controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs the program based on input from the client.
	 */
	@Override
	public void run() {
		boolean running = true;
		String[] args;
		try {
			while (true) { // login loop
				args = socketIn.readLine().split(" ");
				if (args[0].equals("admin") && args[1].equals("admin")) {
					socketOut.println(2);
					break;
				}
				Student student = controller.attemptLogin(args[0], args[1]);
				if (student != null) {
					theStudent = student;
					socketOut.println(1);
					break;
				}
				socketOut.println(0);
			}

			while (running) { // once logged in, read commands
				args = socketIn.readLine().split(" ");

				switch (args[0]) {
				case "get":
					if (args[1].equals("catalog")) {
						socketOut.println(catalogue.getCourseList().size());
						for (Course c : catalogue.getCourseList()) {
							socketOut.println(c.getCourseName() + " " + c.getCourseNum());
							socketOut.println(c.getOfferingList().size());
							for (CourseOffering co : c.getOfferingList()) {
								socketOut.println(co.getSecNum());
								socketOut.println(
										co.getOfferingRegList().size() + "/" + co.getSecCap() + " students enrolled");
							}
						}
					} else if (args[1].equals("enrolled")) {
						socketOut.println(theStudent.getRegList().size());
						for (Registration r : theStudent.getRegList()) {
							socketOut.println(r.getTheOffering().getTheCourse().getCourseName() + " "
									+ r.getTheOffering().getTheCourse().getCourseNum());
							socketOut.println("1");
							socketOut.println(r.getTheOffering().getSecNum());
							socketOut.println(r.getTheOffering().getOfferingRegList().size() + "/"
									+ r.getTheOffering().getSecCap() + " students enrolled");
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
							database.addRegistration(reg);
							socketOut.println("Registration successful");
						}
					}
					break;

				case "unenroll":
					Course found3 = catalogue.searchCat(args[1], Integer.parseInt(args[2]));
					database.removeRegistration(theStudent.getRegistration(found3));
					socketOut.println(theStudent.removeRegistration(found3));
					break;

				case "admin":
					String unparsedName = socketIn.readLine();
					String[] splitName = unparsedName.split(" ");
					boolean validNumber;
					int courseNumber = 0;
					try {
						courseNumber = Integer.parseInt(splitName[1]);
						validNumber = true;
					} catch (NumberFormatException e) {
						validNumber = false;
					}
					if (splitName.length == 2 && splitName[0].length() == 4 && splitName[1].length() == 3
							&& validNumber) {
						Course searchResult = catalogue.searchCat(splitName[0], courseNumber);
						int seats = Integer.parseInt(socketIn.readLine());
						if (searchResult == null) {
							Course newCourse = new Course(splitName[0], courseNumber);
							CourseOffering newOffering = new CourseOffering(1, seats);
							newCourse.addOffering(newOffering);
							catalogue.getCourseList().add(newCourse);
							database.addCourse(newCourse);
							database.addCourseOffering(newOffering);
						} else {
							CourseOffering newOffering = new CourseOffering(searchResult.getOfferingList().size() + 1,
									seats);
							searchResult.addOffering(newOffering);
							database.addCourseOffering(newOffering);
						}
						socketOut.println("success");
					} else {
						socketOut.println("fail");
						socketOut.println("Invalid course name!");
					}
					break;

				case "quit":
					running = false;
					System.out.println("Client disconnected!");
					break;

				default:
					socketOut.print("An error occured");
					continue;
				}
			}
		} catch (Exception e) {
			// The server should only throw an exception on unrecoverable errors.
			// If this happens, terminate the program
			System.out.println("Error communicating with client!. Terminating thread...");
			return;
		}
	}

}
