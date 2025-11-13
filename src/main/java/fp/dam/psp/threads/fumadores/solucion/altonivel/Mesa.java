package fp.dam.psp.threads.fumadores.solucion.altonivel;

import static fp.dam.psp.threads.fumadores.solucion.altonivel.Main.actualizar;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Mesa {

	HashSet<Ingrediente> ingredientes = new HashSet<>();
	Lock lock = new ReentrantLock();
	Condition llena = lock.newCondition();
	Condition vacia = lock.newCondition();

	public void depositar(Ingrediente i1, Ingrediente i2) {
		try {
			lock.lock();
			while(!ingredientes.isEmpty())
				llena.await();
			ingredientes.add(i1);
			ingredientes.add(i2);
			actualizar("El agente depositó " + i1 + " y " + i2 + "\n");
			vacia.signalAll();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			lock.unlock();
		}
	}

	public void retirar(Ingrediente i) {
		try {
			lock.lock();
			while(ingredientes.isEmpty() || ingredientes.contains(i))
				vacia.await();
			actualizar(ingredientes.stream().map(j->j.toString()).collect(Collectors.joining(" y ", Thread.currentThread().getName() + " retira " , "\n")));
			ingredientes.clear();
			llena.signal();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally  {
			lock.unlock();
		}
	}
 

}
