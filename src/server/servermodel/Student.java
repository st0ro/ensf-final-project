package server.servermodel;

import java.util.ArrayList;

/**
 * The Student class for the course registration system.
 * 
 * @author Tony Fang
 */
public class Student {
	/**
	 * The name of the student
	 */
	private String studentName;
	/**
	 * The ID of the student
	 */
	private int studentId;
	/**
	 * The student's login password
	 */
	private String password;
	/**
	 * The ArrayList of registrations for this student
	 */
	private ArrayList<Registration> studentRegList;
	/**
	 * Constructs the Student object with specified values.
	 * @param studentName the name of the student
	 * @param studentId the ID of the student
	 * @param password the student's login password
	 */
	public Student(String studentName, int studentId, String password) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		this.setPassword(password);
		studentRegList = new ArrayList<Registration>();
	}
	/**
	 * Gets the student's name.
	 * @return the student's name
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * Sets the student's name.
	 * @param studentName the student's name
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * Gets the student's ID.
	 * @return the student's ID
	 */
	public int getStudentId() {
		return studentId;
	}
	/**
	 * Sets the student's ID.
	 * @param studentId the student's ID
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	/**
	 * Checks if the student is registered in the specified course course.
	 * @param c the target course
	 * @return true if the student is registered in the target course
	 */
	public boolean hasCourse(Course c) {
		for (Registration r : studentRegList) {
			if (r.getTheOffering().getTheCourse().equals(c))
				return true;
		}
		return false;
	}
	/**
	 * Converts the Student object to a String.
	 */
	@Override
	public String toString() {
		String st = "Student Name: " + getStudentName() + "\n" + "Student Id: " + getStudentId() + "\n\n";
		st += "All courses taken by this student: \n";
		for (Registration r : studentRegList) {
			st += r.getTheOffering();
			st += "\n";
		}
		return st;
	}
	/**
	 * Adds a registration to the student.
	 * @param registration the registration that is added
	 */
	public void addRegistration(Registration registration) {
		if (studentRegList.size() < 6)
			studentRegList.add(registration);
	}
	/**
	 * Removes a registration from the student.
	 * @param toRemove the course for the removed registration
	 * @return "Course removed" if registration is successfully removed, otherwise returns "fail"
	 */
	public String removeRegistration(Course toRemove) {
		for (Registration r : studentRegList) {
			if (r.getTheOffering().getTheCourse().equals(toRemove)) {
				r.getTheOffering().removeStudent(this);
				studentRegList.remove(r);
				return "Course removed.";
			}
		}
		return "fail";

	}
	/**
	 * Gets the student's password
	 * @return the student's password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the student's password
	 * @param password the student's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Gets the list of registrations
	 * @return the list of registrations
	 */
	public ArrayList<Registration> getRegList() {
		return studentRegList;
	}

}
