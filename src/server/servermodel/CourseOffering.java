package server.servermodel;

import java.util.ArrayList;

/**
 * The CourseOffering class containing the course offering available for a
 * course.
 * 
 * @author Tony Fang
 */
public class CourseOffering {
	/**
	 * The ID of the course offering
	 */
	private int offeringID;
	/**
	 * The section number of the course offering
	 */
	private int secNum;
	/**
	 * The section capacity of the course offering
	 */
	private int secCap;
	/**
	 * The course this course offering is for
	 */
	private Course theCourse;
	/**
	 * The ArrayList of registrations for this course offering
	 */
	private ArrayList<Registration> offeringRegList;

	/**
	 * Constructs the CourseOffering object with specified values.
	 * 
	 * @param secNum the section number of the course offering
	 * @param secCap the section capacity of the course offering
	 */
	public CourseOffering(int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}
	
	/**
	 * Constructs the CourseOffering object with specified values.
	 * @param offeringID the ID of the course offering
	 * @param secNum the section number of the course offering
	 * @param secCap the section capacity of the course offering
	 */
	public CourseOffering (int offeringID, int secNum, int secCap) {
		this.setOfferingID(offeringID);
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}
	
	/**
	 * Gets the course offering ID
	 * @return the course offering ID
	 */
	public int getOfferingID() {
		return offeringID;
	}
	
	/**
	 * Sets the course offering ID
	 * @param offeringID the course offering ID
	 */
	public void setOfferingID(int offeringID) {
		this.offeringID = offeringID;
	}
	
	/**
	 * Gets the section number.
	 * 
	 * @return the course name
	 */
	public int getSecNum() {
		return secNum;
	}

	/**
	 * Sets the section number.
	 * 
	 * @param secNum the section number
	 */
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	/**
	 * Gets the section capacity.
	 * 
	 * @return the section capacity
	 */
	public int getSecCap() {
		return secCap;
	}

	/**
	 * Sets the section capacity.
	 * 
	 * @param secCap the section capacity
	 */
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	/**
	 * Gets the course.
	 * 
	 * @return the course
	 */
	public Course getTheCourse() {
		return theCourse;
	}

	/**
	 * Sets the course.
	 * 
	 * @param theCourse the course
	 */
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	/**
	 * Converts the CourseOffering object to a String.
	 */
	@Override
	public String toString() {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "\n";
		if (offeringRegList.size() < 8)
			st += "Currently, there are not enough students to run this section.\n";
		// We also want to print the names of all students in the section
		return st;
	}

	/**
	 * Adds a registration to the course offering.
	 * 
	 * @param registration the registration that is added
	 */
	public void addRegistration(Registration registration) {
		offeringRegList.add(registration);

	}

	/**
	 * Gets the list of registrations.
	 * 
	 * @return the list of registrations
	 */
	public ArrayList<Registration> getOfferingRegList() {
		return offeringRegList;
	}

	/**
	 * Removes a student registration from the registration list.
	 * 
	 * @param st the registered student to be removed
	 */
	public void removeStudent(Student st) {
		for (int i = 0; i < offeringRegList.size(); i++) {
			if (offeringRegList.get(i).getTheStudent().equals(st)) {
				offeringRegList.remove(i);
			}
		}
	}

}
