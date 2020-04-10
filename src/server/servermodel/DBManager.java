package server.servermodel;

import java.util.ArrayList;

//This class is simulating a database for our
//program
public class DBManager {
	
	ArrayList <Course> courseList;
	ArrayList <Student> studentList;

	public DBManager () {
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();
	}

	public ArrayList readFromDataBase() {
		courseList.add(new Course ("ENGG", 233));
		courseList.add(new Course ("ENSF", 409));
		courseList.add(new Course ("PHYS", 259));
		courseList.add(new Course ("ENGG", 200));
		courseList.add(new Course ("MATH", 275));
		courseList.add(new Course ("MATH", 277));
		courseList.get(1).addPreReq(courseList.get(0));
		courseList.get(1).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(4));
		courseList.get(5).addPreReq(courseList.get(4));
		studentList.add(new Student("Timmy", 1));
		studentList.add(new Student("Jimmy", 2));
		studentList.add(new Student("Joseph", 3));
		studentList.add(new Student("John", 4));
		studentList.add(new Student("Bill", 5));
		studentList.add(new Student("Sam", 6));
		studentList.add(new Student("Bob", 7));
		studentList.add(new Student("Karen", 8));
		return courseList;
	}

}
