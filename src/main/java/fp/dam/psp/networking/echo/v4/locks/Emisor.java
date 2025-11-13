package fp.dam.psp.networking.echo.v4.locks;

import java.io.IOException;

public class Emisor implements Runnable {

	private Almacen almacen;

	public Emisor(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Almacen.Item i = almacen.retirar();
				i.out.writeUTF(i.string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
