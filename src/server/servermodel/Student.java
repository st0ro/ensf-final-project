package server.servermodel;

import java.util.ArrayList;

public class Student {
	
	private String studentName;
	private int studentId;
	private String password;
	private ArrayList<Registration> studentRegList;
	
	public Student (String studentName, int studentId, String password) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		this.setPassword(password);
		studentRegList = new ArrayList<Registration>();
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		st += "All courses taken by this student: \n";
		for (Registration r : studentRegList) {
			st += r.getTheOffering();
			st += "\n";
		}
		return st;
	}

	public void addRegistration(Registration registration) {
		if (studentRegList.size() < 6)
			studentRegList.add(registration);
		else
			System.out.println("This student is already taking 6 courses. Unable to register for this course.");
	}
	
	public String removeCourse(Course course) {
		for (Registration r: studentRegList) {
			if (course.equals(r.getTheOffering().getTheCourse())) {
				studentRegList.remove(r);
				return "Course removed.";
			}
		}
		return "This student is not taking this course.";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Registration> getRegList() {
		return studentRegList;
	}

}
