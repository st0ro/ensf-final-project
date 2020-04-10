package server.servermodel;

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;
	
	void startRegistration (Student st, Course c) {
		for (int i = 0; i < c.getOfferingList().size(); i++) {
			if (c.getCourseOfferingAt(i).getOfferingRegList().size() < c.getCourseOfferingAt(i).getSecCap()) {
				completeRegistration(st, c.getCourseOfferingAt(i));
				System.out.println("Registration successful.");
				return;
			}
		}
		System.out.println("All course offerings are full. Unable to register for this course.");
	}
	
	void completeRegistration (Student st, CourseOffering of) {
		theStudent = st;
		theOffering = of;
		addRegistration ();
	}
	
	private void addRegistration () {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	
	public Student getTheStudent() {
		return theStudent;
	}
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString () {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering () + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
		
	}
	

}
