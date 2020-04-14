package server.servermodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//This class is simulating a database for our
//program
public class DBManager {
	
	private ArrayList <Course> courseList;
	private ArrayList <Student> studentList;
	private CourseCatalogue courses;
	private Scanner courseInput;
	private Scanner studentInput;

	public DBManager () {
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();
		courses = new CourseCatalogue();
		try {
			courseInput = new Scanner(new File("database.txt"));
			studentInput = new Scanner(new File("students.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList <Course> readFromDataBase() {
		String courseName, studentName, studentId, password;
		int courseNum, secNum, secCap;
		while (courseInput.hasNext()) {
			courseName = courseInput.next();
			courseNum = Integer.parseInt(courseInput.next());
			for (Course c: courseList) {
				if (!c.getCourseName().equals(courseName) || c.getCourseNum() != courseNum)
					courseList.add(new Course(courseName, courseNum));
			}
			secNum = Integer.parseInt(courseInput.next());
			secCap = Integer.parseInt(courseInput.next());
			courses.createCourseOffering(courses.searchCat(courseName, courseNum), secNum, secCap);
			courseInput.nextLine();
		}
		while (studentInput.hasNext()) {
			studentName = studentInput.next();
			studentId = studentInput.next();
			password = studentInput.next();
			studentList.add(new Student(studentName, studentId, password));
			studentInput.nextLine();
		}
		courseInput.close();
		studentInput.close();
		return courseList;
	}
	
	public Student searchStudent(String id) {
		for (Student s: studentList) {
			if (s.getStudentId() == id)
				return s;
		}
		return null;
	}
	
	public Student attemptLogin(String username, String password) {
		for (Student s: studentList) {
			if (s.getStudentName().equals(username) && s.getPassword().equals(password))
				return s;
		}
		return null;
	}
	
	public ArrayList <Course> getCourseList() {
		return courseList;
	}
	
	public ArrayList <Student> getStudentList() {
		return studentList;
	}
	
	public CourseCatalogue getCourses(){
		return courses;
	}

}
