package server.servermodel;

import java.util.ArrayList;

public class CourseOffering {
	
	private int secNum;
	private int secCap;
	private Course theCourse;
	private ArrayList <Registration> offeringRegList;
	
	public CourseOffering (int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList <Registration>();
	}
	public int getSecNum() {
		return secNum;
	}
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	public int getSecCap() {
		return secCap;
	}
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}
	public Course getTheCourse() {
		return theCourse;
	}
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	@Override
	public String toString () {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: "+ getSecCap() +"\n";
		if (offeringRegList.size() < 8)
			st += "Currently, there are not enough students to run this section.\n";
		//We also want to print the names of all students in the section
		return st;
	}
	public void addRegistration(Registration registration) {
		offeringRegList.add(registration);
		
	}
	public ArrayList<Registration> getOfferingRegList() {
		return offeringRegList;
	}
	

	
	

}
