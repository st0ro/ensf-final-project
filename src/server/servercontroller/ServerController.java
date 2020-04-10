package server.servercontroller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerController {

	private ServerSocket serverSocket;
	private ExecutorService pool;

	public ServerController(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communicate() {
		while (true) {
			try {
				Socket aSocket = serverSocket.accept();
				ClientReceiver receiver = new ClientReceiver(aSocket);
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
