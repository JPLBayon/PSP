package fp.dam.psp.networking.echo.v4.locks;

import java.io.DataOutputStream;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Almacen {
	
	private Lock lock = new ReentrantLock();
	private Condition llena = lock.newCondition();
	private Condition vacia = lock.newCondition();
	
	private LinkedList<Item> almacen = new LinkedList<>();
	private static final int MAX = 1000;
	
	public void almacenar(DataOutputStream out, String s) {
		try {
			lock.lock();
			while (almacen.size() == MAX) 
				llena.await();
			almacen.offer(new Item(out, s));
			vacia.signalAll();;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public Item retirar() {
		try {
			lock.lock();
			while (almacen.isEmpty())
				vacia.await();
			llena.signal();
			return almacen.poll();
		} catch (InterruptedException e) {
			return null;
		} finally {
			lock.unlock();
		}
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
