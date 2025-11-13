package fp.dam.psp.networking.echo.v4;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorEcho {

	private static final Almacen almacen = new Almacen();

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			ExecutorService executor = Executors.newFixedThreadPool(100);
			executor.submit(new Emisor(almacen));
			System.out.println("Servidor ECHO escuchando en puerto 5000");
			while (true)
				executor.submit(new Receptor(serverSocket.accept(), almacen));
		}
	}

}
