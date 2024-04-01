package bai1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.SwingConstants;

public class Client extends JFrame {
	
	private Socket Client_Socket;
	private DataInputStream Client_Datain;
	private DataOutputStream Client_Dataout;
	private BufferedReader Client_Reader;
	@SuppressWarnings("unused")
	private BufferedWriter Client_Writer;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lb1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Socket socket = new Socket("localhost", 51002);
										
					Client frame = new Client(socket);
					frame.setVisible(true);
					
					frame.receiveMessage();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void receiveMessage() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (Client_Socket.isConnected()) {
						Client_Reader.readLine();	// await for the call "check brro" from the server
						
						int i = 0;
						while (i++ < 1000) {
							lb1.setText(i + "");
							Thread.sleep(10);	// :3
						}
					}
					
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
	}

	
	public Client(Socket sk1) {
		// setting up Socket
		try {
			// setup client
			Client_Socket = sk1;
			
			// setup input
			Client_Datain = new DataInputStream(sk1.getInputStream());
			Client_Reader = new BufferedReader(new InputStreamReader(Client_Datain));
			
			// setup output
			Client_Dataout = new DataOutputStream(sk1.getOutputStream());
			Client_Writer = new BufferedWriter(new OutputStreamWriter(Client_Dataout));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lb1 = new JLabel("New label");
		lb1.setHorizontalAlignment(SwingConstants.CENTER);
		lb1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb1.setBounds(154, 91, 112, 58);
		contentPane.add(lb1);
	}
}