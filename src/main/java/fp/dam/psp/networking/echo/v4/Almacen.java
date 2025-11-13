package fp.dam.psp.networking.echo.v4;

import java.io.DataOutputStream;
import java.util.LinkedList;

public class Almacen {

	private LinkedList<Item> almacen = new LinkedList<>();
	private static final int MAX = 1000;
	
	public synchronized void almacenar(DataOutputStream out, String s) {
		while (almacen.size() == MAX)
			try {
				wait();
			} catch (InterruptedException e) {}
		almacen.offer(new Item(out, s));
		notify();
	}
	
	public synchronized Item retirar() {
		while (almacen.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {}
		Item i = almacen.poll();
		notify();
		return i;
	}
	
	public static class Item {
		DataOutputStream out;
		String string;
		
		public Item(DataOutputStream out, String string) {
			this.out = out;
			this.string = string;
		}
	}
}
