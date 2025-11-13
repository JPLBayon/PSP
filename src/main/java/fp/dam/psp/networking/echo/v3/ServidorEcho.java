package fp.dam.psp.networking.echo.v3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorEcho {

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			Almacen almacen = new Almacen();
			ExecutorService executor = Executors.newFixedThreadPool(1000);
			System.out.println("Servidor ECHO escuchando en puerto 5000");
			while (true) {
				Socket socket = serverSocket.accept();
				executor.submit(new Receptor(socket, almacen, executor));
			}
		}
	}
}
