
package server.servermodel;

import java.sql.*;
import java.util.ArrayList;

/**
 * A class responsible for communication with the database
 * 
 * @author James Zhou
 *
 */
public class DBManager implements IDBCredentials{

	/**
	 * Holds the courses read from the database
	 */
	private ArrayList<Course> courseList;

	/**
	 * Holds the students in the database
	 */
	private ArrayList<Student> studentList;

	/**
	 * Assists with searching through the courses
	 */
	private CourseCatalogue courses;

	/**
	 * Used for establishing a connection with the database
	 */
	private Connection conn;

	/**
	 * Used for writing queries
	 */
	private Statement stmt;

	/**
	 * Holds the data returned by the query
	 */
	private ResultSet rs;

	/**
	 * Creates a DBManager object and fills the provided CourseCatalogue.
	 * Establishes a connection with the database in constructor.
	 * 
	 * @param courseCat CourseCatalogue to fill
	 */
	public DBManager(CourseCatalogue courseCat) {
		initializeConnection();
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();
		courses = courseCat;
		courseCat.setCourseList(courseList);
	}

	/**
	 * Getter for the course list
	 * 
	 * @return the list of courses
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * Getter for the student list
	 * 
	 * @return the list of students
	 */
	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	/*
	 * Returns the course catalogue
	 * 
	 * @return the catalogue
	 */
	public CourseCatalogue getCourses() {
		return courses;
	}

	/**
	 * Establishes a connection with the database
	 */
	public void initializeConnection() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("An error occured while connecting to database");
			e.printStackTrace();
		}
	}

	/**
	 * Closes the database connection
	 */
	public void close() {
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("An error occurred when trying to close the database connection");
			e.printStackTrace();
		}
	}

	/**
	 * Reads data from the database and fills the respective arrays
	 */
	public void readFromDataBase() {
		readCoursesFromDatabase();
		readStudentsFromDatabase();
		readRegistrationsFromDatabase();
	}

	/**
	 * Reads the students from the database and fills the students array
	 */
	private void readStudentsFromDatabase() {
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM ensf409.students";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				studentList.add(new Student(rs.getString("StudentName"), rs.getInt("StudentID"),
						rs.getString("StudentPassword")));
			}
		} catch (SQLException e) {
			System.out.println("Problem loading students");
			e.printStackTrace();
		}
	}

	/**
	 * Reads the courses from the dabatase and fills the course list
	 */
	private void readCoursesFromDatabase() {
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM ensf409.courses";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courseList.add(new Course(rs.getInt("CourseID"), rs.getString("CourseName"), rs.getInt("CourseNum")));
			}

			stmt = conn.createStatement();
			query = "SELECT * FROM ensf409.courseofferings \n"
					+ "JOIN ensf409.courses ON ensf409.courseofferings.CourseID = ensf409.courses.CourseID";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courses.createCourseOffering(rs.getInt("OfferingID"),
						courses.searchCat(rs.getString("CourseName"), rs.getInt("CourseNum")), rs.getInt("SecNum"),
						rs.getInt("SecCap"));
			}
		} catch (SQLException e) {
			System.out.println("Problem loading courses");
			e.printStackTrace();
		}
	}

	/**
	 * Reads the registrations from database and updates student registrations
	 */
	private void readRegistrationsFromDatabase() {
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM ensf409.registrations\r\n"
					+ "JOIN ensf409.courseofferings ON ensf409.registrations.OfferingID = ensf409.courseofferings.OfferingID";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Registration reg = new Registration();
				reg.completeRegistration(searchStudent(rs.getInt("StudentID")),
						courseList.get(rs.getInt("CourseID") - 1).getCourseOfferingAt(rs.getInt("SecNum") - 1));
			}
		} catch (SQLException e) {
			System.out.println("Problem loading courses");
			e.printStackTrace();
		}
	}

	/**
	 * Adds a registration to the database
	 * 
	 * @param r Registration to add
	 */
	public void addRegistration(Registration r) {
		try {
			String query = "INSERT INTO `ensf409`.`registrations` (`StudentID`, `OfferingID`) VALUES (?, ?)";
			PreparedStatement stat = conn.prepareStatement(query);
			stat.setInt(1, r.getTheStudent().getStudentId());
			stat.setInt(2, r.getTheOffering().getOfferingID());
			stat.executeUpdate();
			stat.close();
		} catch (SQLException e) {
			System.out.println("Problem adding registration");
			e.printStackTrace();
		}
	}

	/**
	 * Removes a registration from the database
	 * 
	 * @param r Registration to remove
	 */
	public void removeRegistration(Registration r) {
		try {
			String query = "DELETE FROM ensf409.registrations WHERE StudentID=" + r.getTheStudent().getStudentId()
					+ " AND OfferingID=" + r.getTheOffering().getOfferingID();
			PreparedStatement stat = conn.prepareStatement(query);
			stat.executeUpdate();
			stat.close();
		} catch (SQLException e) {
			System.out.println("Problem removing registration");
			e.printStackTrace();
		}
	}

	/**
	 * Adds a course to the database
	 * 
	 * @param c Course to add
	 */
	public void addCourse(Course c) {
		try {
			String query = "INSERT INTO `ensf409`.`courses` (`CourseName`, `CourseNum`) VALUES (?,?)";
			PreparedStatement stat = conn.prepareStatement(query);
			stat.setString(1, c.getCourseName());
			stat.setInt(2, c.getCourseNum());
			stat.executeUpdate();
			stat.close();
			stmt = conn.createStatement();
			query = "SELECT * FROM ensf409.courses WHERE CourseID = LAST_INSERT_ID()";
			rs = stmt.executeQuery(query);
			rs.next();
			c.setCourseID(rs.getInt("CourseID"));
		} catch (SQLException e) {
			System.out.println("Problem adding course");
			e.printStackTrace();
		}
	}

	/**
	 * Adds a course offering to the database
	 * @param c CourseOffering to add
	 */
	public void addCourseOffering(CourseOffering c) {
		try {
			String query = "INSERT INTO `ensf409`.`courseofferings` (`CourseID`, `SecNum`, `SecCap`) VALUES (?, ?, ?)";
			PreparedStatement stat = conn.prepareStatement(query);
			stat.setInt(1, c.getTheCourse().getCourseID());
			stat.setInt(2, c.getSecNum());
			stat.setInt(3, c.getSecCap());
			stat.executeUpdate();
			stat.close();
			stmt = conn.createStatement();
			query = "SELECT * FROM ensf409.courseofferings WHERE OfferingID = LAST_INSERT_ID()";
			rs = stmt.executeQuery(query);
			rs.next();
			c.setOfferingID(rs.getInt("OfferingID"));
		} catch (SQLException e) {
			System.out.println("Problem adding course offering");
			e.printStackTrace();
		}
	}

	/**
	 * Searches the student list for a student
	 * 
	 * @param id ID of the student to search for
	 * @return Object of Student with matching ID if found, null otherwise
	 */
	public Student searchStudent(int id) {
		for (Student s : studentList) {
			if (s.getStudentId() == id)
				return s;
		}
		return null;
	}

	/**
	 * Attempts login
	 * 
	 * @param username Username to log in with
	 * @param password Password to verify identity
	 * @return Object of Student with username if password is correct/if student
	 *         exists, null otherwise
	 */
	public Student attemptLogin(String username, String password) {
		for (Student s : studentList) {
			if (s.getStudentName().equals(username) && s.getPassword().equals(password))
				return s;
		}
		return null;
	}

}
