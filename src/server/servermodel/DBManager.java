// Written by T. Fang
package server.servermodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//This class is simulating a database for our
//program
public class DBManager {

	private ArrayList<Course> courseList;
	private ArrayList<Student> studentList;
	private CourseCatalogue courses;
	private Scanner courseInput;
	private Scanner studentInput;

	public DBManager(CourseCatalogue courseCat) {
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();
		courses = courseCat;
		courseCat.setCourseList(courseList);
		try {
			courseInput = new Scanner(new File("database.txt"));
			studentInput = new Scanner(new File("students.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void readFromDataBase() {
		String input;
		String[] arr;
		while (courseInput.hasNextLine()) {
			input = courseInput.nextLine();
			arr = input.split(" ");
			boolean found = false;
			Course co = new Course(arr[0], Integer.parseInt(arr[1]));
			for (Course c : courseList) {
				if (c.getCourseName().equals(arr[0]) && c.getCourseNum() == Integer.parseInt(arr[1])) {
					found = true;
					co = c;
					break;
				}
			}
			if(!found) {
				courseList.add(co);
			}
			courses.createCourseOffering(co, Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
		}
		courses.setCourseList(courseList);
		while (studentInput.hasNextLine()) {
			input = studentInput.nextLine();
			arr = input.split(" ");
			studentList.add(new Student(arr[0], arr[1], arr[2]));
		}
		courseInput.close();
		studentInput.close();
		return;
	}

	public Student searchStudent(String id) {
		for (Student s : studentList) {
			if (s.getStudentId() == id)
				return s;
		}
		return null;
	}

	public Student attemptLogin(String username, String password) {
		for (Student s : studentList) {
			if (s.getStudentName().equals(username) && s.getPassword().equals(password))
				return s;
		}
		return null;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public CourseCatalogue getCourses() {
		return courses;
	}

}
