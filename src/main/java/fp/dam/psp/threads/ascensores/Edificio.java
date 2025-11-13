package fp.dam.psp.threads.ascensores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Edificio {

	private int numPlantas;
	private HashSet<Integer> llamadas = new HashSet<>();
	private Lock lock = new ReentrantLock();
	private Condition sinDestino = lock.newCondition();
	private HashMap<Integer, List<Thread>> plantas = new HashMap<>();

	
	public Edificio(int numPlantas) {
		this.numPlantas = numPlantas;
		for (int i=0; i<numPlantas; i++) {
			plantas.put(i, new LinkedList<>());
		}
	}
	
	public Lock getLock() {
		return lock;
	}
	
	public Condition getCondition() {
		return sinDestino;
	}
	
	public boolean sinLlamadas() {
		return llamadas.isEmpty();
	}
	
	public void llamarEsperar(int origen, int destino) {
		if (destino >= 0 && destino < numPlantas) {
			lock.lock();
			try {
				llamadas.add(destino);
			} finally {
				sinDestino.signalAll();
				lock.unlock();
			}
			plantas.get(origen).add(Thread.currentThread());
		}
	}
		
	public static void main(String[] args) {
		
	}

}
