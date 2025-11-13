package fp.dam.psp.networking.echo.v3;

import java.util.LinkedList;

public class Almacen {

	private LinkedList<String> almacen = new LinkedList<>();
	private static final int MAX = 1000;
	
	public synchronized void almacenar(String s) {
		while (almacen.size() == MAX)
			try {
				wait();
			} catch (InterruptedException e) {}
		almacen.offer(s);
		notify();
	}
	
	public synchronized String retirar() {
		try {
			while (almacen.isEmpty())
				wait();
			String s = almacen.poll();
			notify();
			return s;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}
}
