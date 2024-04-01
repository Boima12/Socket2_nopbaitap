package bai1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Handler implements Runnable {
	
	private static ArrayList<Handler> Handler_Clients = new ArrayList<>();
	
	private Socket Handler_Socket;
	private DataInputStream Handler_Datain;
	private DataOutputStream Handler_Dataout;
	private BufferedReader Handler_Reader;
	private BufferedWriter Handler_Writer;

	public Handler(Socket clientValue1) {
		try {
			// setup client socket
			Handler_Socket = clientValue1;
			Handler_Clients.add(this); 	// can we just add(socket) only? -- no due to we have to take whole Handler object later!
			
			// setup input
			Handler_Datain = new DataInputStream(clientValue1.getInputStream());
			Handler_Reader = new BufferedReader(new InputStreamReader(Handler_Datain));
			
			// setup output
			Handler_Dataout = new DataOutputStream(clientValue1.getOutputStream());
			Handler_Writer = new BufferedWriter(new OutputStreamWriter(Handler_Dataout));
			
		} catch (IOException e) {
			closeEverything(Handler_Socket, Handler_Datain, Handler_Dataout, Handler_Reader, Handler_Writer);
		}
	}
	
	
	@Override
	public void run() {
		if (Handler_Socket.isConnected()) {
			try {
				Thread.sleep(0);	// wait for Client.java to loaded each clients? or the Client will load first before Client.receiveMessage() ?
				sendSignalToClient("check brro");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void sendSignalToClient(String msgToBroad) {
		try {
			this.Handler_Writer.write(msgToBroad);
			this.Handler_Writer.newLine();
			this.Handler_Writer.flush();	
		} catch (IOException e) {
			closeEverything(Handler_Socket, Handler_Datain, Handler_Dataout, Handler_Reader, Handler_Writer);
		}
	}
	
	
	public void removeHandler() {
		Handler_Clients.remove(this);
	}
	
	
	public void closeEverything(Socket sk1, DataInputStream datain, DataOutputStream dataout, BufferedReader bfread, BufferedWriter bfwrite) {
		removeHandler();
		
		
		try {
			if (sk1 != null) {
				sk1.close();
			}
			if (datain != null) {
				datain.close();
			}
			if (dataout != null) {
				dataout.close();
			}
			if (bfread != null) {
				bfread.close();
			}
			if (bfwrite != null) {
				bfwrite.close();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}