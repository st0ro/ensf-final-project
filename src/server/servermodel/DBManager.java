package server.servermodel;

import java.util.ArrayList;

//This class is simulating a database for our
//program
public class DBManager {
	
	ArrayList <Course> courseList;

	public DBManager () {
		courseList = new ArrayList<Course>();
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
		return courseList;
	}

}
