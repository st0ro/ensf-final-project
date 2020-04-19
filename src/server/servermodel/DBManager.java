// Written by T. Fang
package server.servermodel;

import java.sql.*;
import java.util.ArrayList;

//This class is simulating a database for our
//program
public class DBManager {

	private ArrayList<Course> courseList;
	private ArrayList<Student> studentList;
	private CourseCatalogue courses;

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	private int nextOfferingID;

	public DBManager(CourseCatalogue courseCat) {
		initializeConnection();
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();
		courses = courseCat;
		courseCat.setCourseList(courseList);
		nextOfferingID = 0;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public CourseCatalogue getCourses() {
		return courses;
	}

	public void initializeConnection() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(IDBCredentials.DB_URL, IDBCredentials.USERNAME, IDBCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("An error occured while connecting to database");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("An error occurred when trying to close the database connection");
			e.printStackTrace();
		}
	}

	public void readFromDataBase() {
		readCoursesFromDatabase();
		readStudentsFromDatabase();
		readRegistrationsFromDatabase();
		return;
	}

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

	private void readCoursesFromDatabase() {
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM ensf409.courses";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courseList.add(new Course(rs.getInt("CourseID"), rs.getString("CourseName"), rs.getInt("CourseNum")));
			}

			stmt = conn.createStatement();
			query = "SELECT * FROM ensf409.courseofferings";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				CourseOffering offering = courses.createCourseOffering(courseList.get(rs.getInt("CourseID") - 1), rs.getInt("SecNum"),
						rs.getInt("SecCap"));
				offering.setOfferingID(nextOfferingID++);
			}
		} catch (SQLException e) {
			System.out.println("Problem loading courses");
			e.printStackTrace();
		}
	}

	private void readRegistrationsFromDatabase() {
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM ensf409.registrations\r\n" + 
					"JOIN ensf409.courseofferings ON ensf409.registrations.OfferingID = ensf409.courseofferings.OfferingID";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Registration reg = new Registration();
				reg.completeRegistration(searchStudent(rs.getInt("StudentID")), courseList.get(rs.getInt("CourseID")-1).getCourseOfferingAt(rs.getInt("SecNum")-1));
			}
		} catch (SQLException e) {
			System.out.println("Problem loading courses");
			e.printStackTrace();
		}
	}

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
	
	public void removeRegistration(Registration r) {
		try {
			String query = "DELETE FROM ensf409.registrations WHERE StudentID=" + r.getTheStudent().getStudentId() + " AND OfferingID=" + r.getTheOffering().getOfferingID();
			PreparedStatement stat = conn.prepareStatement(query);
			stat.executeUpdate();
			stat.close();
		} catch (SQLException e) {
			System.out.println("Problem removing registration");
			e.printStackTrace();
		}
	}

	public void addCourse(Course c) {
		try {
			c.setCourseID(courseList.size() + 1);
			String query = "INSERT INTO `ensf409`.`courses` (`CourseName`, `CourseNum`) VALUES (?,?)";
			PreparedStatement stat = conn.prepareStatement(query);
			stat.setString(1, c.getCourseName());
			stat.setInt(2, c.getCourseNum());
			stat.executeUpdate();
			stat.close();
		} catch (SQLException e) {
			System.out.println("Problem adding course");
			e.printStackTrace();
		}
	}

	public void addCourseOffering(CourseOffering c) {
		try {
			String query = "INSERT INTO `ensf409`.`courseofferings` (OfferingID, `CourseID`, `SecNum`, `SecCap`) VALUES (?, ?, ?, ?)";
			PreparedStatement stat = conn.prepareStatement(query);
			stat.setInt(1, nextOfferingID);
			stat.setInt(2, c.getTheCourse().getCourseID());
			stat.setInt(3, c.getSecNum());
			stat.setInt(4, c.getSecCap());
			stat.executeUpdate();
			stat.close();
			c.setOfferingID(nextOfferingID++);
		} catch (SQLException e) {
			System.out.println("Problem adding course");
			e.printStackTrace();
		}
	}

	public Student searchStudent(int id) {
		for (Student s : studentList) {
			if (s.getStudentId() == id)
				return s;
		}
		return null;
	}

	public Student attemptLogin(String username, String password) {
		for (Student s : studentList) {
			if (s.getStudentName().equals(username) && s.getPassword().equals(password))
				return s;
		}
		return null;
	}

}
