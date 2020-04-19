package server.servermodel;

import java.util.ArrayList;

/**
 * The Course class for the course registration system.
 * 
 * @author Tony Fang
 */
public class Course {
	/**
	 * The ID of the course
	 */
	private int courseID;
	/**
	 * The name of the course
	 */
	private String courseName;
	/**
	 * The number of the course
	 */
	private int courseNum;
	/**
	 * The ArrayList containing prerequisite courses
	 */
	private ArrayList<Course> preReq;
	/**
	 * The ArrayList containing all offerings for the course
	 */
	private ArrayList<CourseOffering> offeringList;
	/**
	 * Constructs the Course object with specified values.
	 * @param courseName the name of the course
	 * @param courseNum the number of the course
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}
	/**
	 * Constructs the Course object with specified values.
	 * @param courseID the ID of the course
	 * @param courseName the name of the course
	 * @param courseNum the number of the course
	 */
	public Course(int courseID, String courseName, int courseNum) {
		this.setCourseID(courseID);
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}
	/**
	 * Adds a course offering to the Course object.
	 * @param offering the CourseOffering object that is added
	 */
	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}
			
			offeringList.add(offering);
		}
	}
	/**
	 * Gets the course name.
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * Sets the course name.
	 * @param courseName the course name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * gets the course number.
	 * @return the course number
	 */
	public int getCourseNum() {
		return courseNum;
	}
	/**
	 * sets the course number.
	 * @param courseNum the course number
	 */
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	/**
	 * Converts the Course object to a String.
	 */
	@Override
	public String toString () {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum ();
		st += "\nAll course sections:\n";
		for (CourseOffering c : offeringList)
			st += c;
		st += "\n-------\n";
		return st;
	}
	/**
	 * Gets the course offering at the specified index.
	 * @param i the index in the ArrayList
	 * @return the course offering
	 */
	public CourseOffering getCourseOfferingAt(int i) {
		if (i < 0 || i >= offeringList.size() )
			return null;
		else
			return offeringList.get(i);
	}
	/**
	 * Adds a prerequisite for the course.
	 * @param course the prerequisite course to be added
	 */
	public void addPreReq(Course course) {
		preReq.add(course);
	}
	/**
	 * Gets the course offering list
	 * @return the course offering list
	 */
	public ArrayList<CourseOffering> getOfferingList() {
		return offeringList;
	}
	/**
	 * Gets the course ID
	 * @return the course ID
	 */
	public int getCourseID() {
		return courseID;
	}
	/**
	 * Sets the course ID
	 * @param courseID the course ID
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

}
