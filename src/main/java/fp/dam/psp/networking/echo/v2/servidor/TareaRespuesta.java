package fp.dam.psp.networking.echo.v2.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TareaRespuesta implements Runnable {

	private Socket socket;
	
	public TareaRespuesta(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("petición de " + socket.getInetAddress());
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			String s;
			while ((s = in.readLine()) != null) {
				System.out.println(s);
				out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
