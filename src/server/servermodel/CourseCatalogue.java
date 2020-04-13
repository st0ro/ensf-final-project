package server.servermodel;

import java.util.ArrayList;

public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	
	public CourseCatalogue () {
		loadFromDataBase ();
	}
	
	private void loadFromDataBase() {
		DBManager db = new DBManager();
		setCourseList(db.readFromDataBase());
		this.createCourseOffering(this.searchCat("ENGG", 233), 1, 100);
		this.createCourseOffering(this.searchCat("ENGG", 233), 2, 150);
		this.createCourseOffering(this.searchCat("ENSF", 409), 1, 100);
		this.createCourseOffering(this.searchCat("PHYS", 259), 1, 100);
		this.createCourseOffering(this.searchCat("ENGG", 200), 1, 100);
		this.createCourseOffering(this.searchCat("ENGG", 200), 2, 200);
		this.createCourseOffering(this.searchCat("MATH", 275), 1, 100);
		this.createCourseOffering(this.searchCat("MATH", 277), 1, 100);
	}
	
	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c!= null) {
			CourseOffering theOffering = new CourseOffering (secNum, secCap);
			c.addOffering(theOffering);
		}
	}
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		return null;
	}
	
	public ArrayList <Course> getCourseList() {
		return courseList;
	}


	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	@Override
	public String toString () {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c;  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}

}
