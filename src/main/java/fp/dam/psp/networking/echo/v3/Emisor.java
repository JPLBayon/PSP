package fp.dam.psp.networking.echo.v3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Emisor implements Runnable {

	private Socket socket;
	private Almacen almacen;

	public Emisor(Socket socket, Almacen almacen) {
		this.socket = socket;
		this.almacen = almacen;
	}

	@Override
	public void run() {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			while (!Thread.currentThread().isInterrupted()) {
				String s = almacen.retirar();
				if (s != null)
					out.writeUTF(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(socket.getInetAddress() + ": FIN EMISOR");
	}

}
