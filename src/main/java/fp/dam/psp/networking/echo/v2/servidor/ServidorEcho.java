package fp.dam.psp.networking.echo.v2.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorEcho {

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			ExecutorService executor = Executors.newFixedThreadPool(100);
			System.out.println("Servidor escuchando en el puerto 5000");
			while (true) {
				Socket socket = serverSocket.accept();
				executor.execute(new TareaRespuesta(socket));
			}
		}
	}

}
