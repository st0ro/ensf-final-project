package server.servermodel;

/**
 * An interface containing the credentials for logging into the database.
 * @author James Zhou
 *
 */
public interface IDBCredentials {
	
	/**
	 * Java database connector driver location
	 */
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	/**
	 * URL of the database
	 */
	static final String DB_URL = "jdbc:mysql://localhost/ensf409";
	
	/**
	 * Database username
	 */
	static final String USERNAME = "root";
	
	/**
	 * Database password
	 */
	static final String PASSWORD = "password";

}
