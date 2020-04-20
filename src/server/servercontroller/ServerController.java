package server.servercontroller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.servermodel.CourseCatalogue;
import server.servermodel.DBManager;
import server.servermodel.Student;

/**
 * A class for the core server functions
 * @author James Zhou
 *
 */
public class ServerController {

	private ServerSocket serverSocket;
	private ExecutorService pool;
	private DBManager database;
	private CourseCatalogue courses;

	/**
	 * Creates a ServerController object on the specified port
	 * 
	 * @param portNumber Port to create server on
	 */
	public ServerController(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
			courses = new CourseCatalogue();
			database = new DBManager(courses);
			database.readFromDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Attempts to log in with the given parameters
	 * 
	 * @param username Account username
	 * @param password Account password
	 * @return Student object corresponding to username if log in is successful,
	 *         null otherwise
	 * 
	 */
	public Student attemptLogin(String username, String password) {
		return database.attemptLogin(username, password);
	}

	/**
	 * Establishes communication with the client.
	 * Will continue accepting connections while the server is running.
	 */
	public void communicate() {
		while (true) {
			try {
				Socket aSocket = serverSocket.accept();
				System.out.println("Client connected!");
				ClientReceiver receiver = new ClientReceiver(aSocket, database.getCourses(), this, database);
				pool.execute(receiver);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ServerController server = new ServerController(25565);
		System.out.println("Server is now running.");
		server.communicate();

	}

}
