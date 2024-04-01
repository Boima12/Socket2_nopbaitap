package bai2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket svSocket;
	
	public Server(ServerSocket svSocketvalue1) {
		svSocket = svSocketvalue1;
	}
	
	public void startServer() {
		try {
			while (!svSocket.isClosed()) {
				Socket client = svSocket.accept();
				
				Handler clientHandler = new Handler(client);
				Thread thread = new Thread(clientHandler);
				thread.start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			shutdownServer();
		}
	}
	
	public void shutdownServer() {
		try {
			svSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket server1 = new ServerSocket(51002);
		Server ServerObj = new Server(server1);
		ServerObj.startServer();
		
	}
	
}