package fp.dam.psp.networking.echo.v1.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class TareaRespuesta implements Runnable {

	private Socket socket;
	
	public TareaRespuesta(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (DataInputStream in = new DataInputStream(socket.getInputStream());
			 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
			String s;
			while (true) 
				try {
					s = in.readUTF();
					out.writeUTF(s);
					System.out.println(s);
				} catch (EOFException e) {
					break;
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
