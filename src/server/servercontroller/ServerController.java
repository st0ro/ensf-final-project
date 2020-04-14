package server.servercontroller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.servermodel.CourseCatalogue;
import server.servermodel.DBManager;
import server.servermodel.Student;

public class ServerController {

	private ServerSocket serverSocket;
	private ExecutorService pool;
	private DBManager database;
	private CourseCatalogue courses;

	public ServerController(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
			courses = new CourseCatalogue();
			database = new DBManager();
			database.readFromDataBase(courses);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Student attemptLogin(String username, String password) {
		return database.attemptLogin(username, password);
	}

	public void communicate() {
		while (true) {
			try {
				Socket aSocket = serverSocket.accept();
				System.out.println("Client connected!");
				ClientReceiver receiver = new ClientReceiver(aSocket, courses, this);
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
