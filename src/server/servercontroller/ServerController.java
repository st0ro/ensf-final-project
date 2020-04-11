package server.servercontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private DBManager database;
	private CourseCatalogue courses;

	public ServerController(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
			database = new DBManager();
			database.readFromDataBase();
			courses = new CourseCatalogue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Student login(Socket socket) {
		try {
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			String input = socketIn.readLine();
			String[] arr = input.split(" ");
			//Student s = database.search(); // Don't commit non-functional code
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void communicate() {
		while (true) {
			try {
				Socket aSocket = serverSocket.accept();
				Student student = login(aSocket);
				ClientReceiver receiver = new ClientReceiver(aSocket, courses, student);
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
