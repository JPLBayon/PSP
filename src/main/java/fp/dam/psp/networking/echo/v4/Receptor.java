package fp.dam.psp.networking.echo.v4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Receptor implements Runnable {
	
	private Socket socket;
	private Almacen almacen;
	
	public Receptor(Socket socket, Almacen almacen) {
		this.socket = socket;
		this.almacen = almacen;
	}
	
	@Override
	public void run() {
		try (DataInputStream in = new DataInputStream(socket.getInputStream());
			 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
			String s;
			while (true) {
				s = in.readUTF();
				System.out.println(socket.getInetAddress() + " -> " + s);
				almacen.almacenar(out, s);
			}
		} catch (EOFException e) {
			System.out.println(socket.getInetAddress() + ": EOF");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
