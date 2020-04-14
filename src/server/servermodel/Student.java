package server.servermodel;

import java.util.ArrayList;

public class Student {

	private String studentName;
	private int studentId;
	private String password;
	private ArrayList<Registration> studentRegList;

	public Student(String studentName, int studentId, String password) {
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

	public boolean hasCourse(Course c) {
		for (Registration r : studentRegList) {
			if (r.getTheOffering().getTheCourse().equals(c))
				return true;
		}
		return false;
	}

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

	public void addRegistration(Registration registration) {
		if (studentRegList.size() < 6)
			studentRegList.add(registration);
	}

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
