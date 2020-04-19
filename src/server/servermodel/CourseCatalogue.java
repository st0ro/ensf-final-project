package server.servermodel;

import java.util.ArrayList;

/**
 * The CourseCatalogue class containing all courses available for the student to register in.
 * 
 * @author Tony Fang
 */
public class CourseCatalogue {
	/**
	 * The ArrayList containing all courses
	 */
	private ArrayList <Course> courseList;
	/**
	 * Creates a course offering for the specified course.
	 * @param c the course that the course offering is created for
	 * @param secNum the section number for the course offering
	 * @param secCap the section capacity for the course offering
	 */
	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c!= null) {
			CourseOffering theOffering = new CourseOffering (secNum, secCap);
			c.addOffering(theOffering);
		}
	}
	/**
	 * Searches for the specified course in the CourseCatalogue object.
	 * @param courseName the name of the course
	 * @param courseNum the number of the course
	 * @return
	 */
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		return null;
	}
	/**
	 * Gets the list of courses.
	 * @return the list of courses
	 */
	public ArrayList <Course> getCourseList() {
		return courseList;
	}
	/**
	 * Sets the list of courses.
	 * @param courseList the list of courses
	 */
	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	/**
	 * Converts the CourseCatalogue object to a String.
	 */
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
