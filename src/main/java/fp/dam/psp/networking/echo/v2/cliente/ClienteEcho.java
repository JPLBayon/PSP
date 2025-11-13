package fp.dam.psp.networking.echo.v2.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteEcho {

	public static void main(String[] args) throws IOException {
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("> ");
		String linea = teclado.readLine();
		while (linea != null) {

			try (Socket socket = new Socket("localhost", 5000)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				while (linea != null && !linea.equalsIgnoreCase("fin")) {
					out.println(linea);
					out.flush();
					System.out.print("> ");
					linea = teclado.readLine();
				}
				socket.shutdownOutput();
				while ((linea = in.readLine()) != null)
					System.out.println(linea);
			}

		}
	}

}
