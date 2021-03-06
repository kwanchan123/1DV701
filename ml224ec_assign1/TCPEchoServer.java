package ml224ec_assign1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Martin Lyr�
 *
 */
public class TCPEchoServer extends NetLayer {

	public static final int DEFAULT_BUFFER_SIZE = 1024;
	public static final int DEFAULT_PORT = 6555;
	
	private static ServerSocket socket;
	
	/**
	 * Main entry for TCPEchoServer as a program
	 * @param args
	 */
	public static void main(String[] args)
	{
		try {
			int port = DEFAULT_PORT;
			int bufferSize = DEFAULT_BUFFER_SIZE;
			
			// Check if there are arguments, else use default(s)
			if (args.length > 0)
				port = Integer.parseInt(args[0]);
			if (args.length > 1)
				bufferSize = Integer.parseInt(args[1]);
			
			// Assign arguments
			TCPEchoServer server = new TCPEchoServer(
						port, bufferSize, 0
					);
			server.start(); // Start working
		}
		catch (Exception e)
		{
			System.out.printf("Error: %s\n", e.toString());
		}
	}
	
	public TCPEchoServer(int localPort, int bufferSize, int transferRate) {
		super(localPort, bufferSize, transferRate);
	}

	@Override
	protected void start() throws IOException {
		try {
			socket = new ServerSocket();
			socket.bind(localAddress);
		} 
		catch (Exception e)
		{
			System.out.printf("Error initializing socket: %s\n", e.getMessage());
			return;
		}
		
		while (true) {
			try {
				Socket remote = socket.accept(); // Listen on local port and accept inbound connections
				
				new Thread(new TCPConnectionHandler(remote, bufferSize)).start(); // Assign connection to a threadable handler
			}
			catch (Exception e)
			{
				System.out.printf("Error accepting connection: %s\n", e.getMessage());
			}
		}
	}

	@Override
	protected void tick() throws IOException {
		// TODO Auto-generated method stub
	}
}
