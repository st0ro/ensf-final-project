package server.servermodel;

/**
 * The Registration class for both the student and the course offering.
 * 
 * @author Tony Fang
 */
public class Registration {
	/**
	 * The registered student
	 */
	private Student theStudent;
	/**
	 * The course offering
	 */
	private CourseOffering theOffering;
	/**
	 * The student's course letter grade
	 */
	private char grade;
	
	/**
	 * Registers a student to a course offering.
	 * @param st the student
	 * @param of the course offering
	 */
	public void completeRegistration(Student st, CourseOffering of) {
		theStudent = st;
		theOffering = of;
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	/**
	 * Gets the registered student.
	 * @return the registered student
	 */
	public Student getTheStudent() {
		return theStudent;
	}
	
	/**
	 * Sets the registered student.
	 * @param theStudent the registered student
	 */
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	
	/**
	 * Gets the course offering.
	 * @return the course offering
	 */
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	
	/**
	 * Sets the course offering.
	 * @param theOffering the course offering
	 */
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	
	/**
	 * Gets the student's course letter grade.
	 * @return the student's course letter grade
	 */
	public char getGrade() {
		return grade;
	}
	
	/**
	 * Sets the student's course letter grade.
	 * @param grade the student's course letter grade
	 */
	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	/**
	 * Converts the Registration object to a String.
	 */
	@Override
	public String toString() {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering() + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;

	}

}
