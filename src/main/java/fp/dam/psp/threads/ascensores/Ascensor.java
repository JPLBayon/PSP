package fp.dam.psp.threads.ascensores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Ascensor extends Semaphore {
	
	private static final long serialVersionUID = 1L;
	private static Random random = new Random();
	
	private Edificio edificio;
	private int plantas;
	private int planta;
	private int sentido;
	private HashSet<Integer> solicitudes = new HashSet<>();
	private HashMap<Integer, Lock> locksPlanta = new HashMap<>();
	private HashMap<Integer, Condition> esperasDestinoAlcanzado = new HashMap<>();
	
	public Ascensor(Edificio edificio, int capacidad, int plantas) {
		super(capacidad);
		this.edificio = edificio;
		this.plantas = plantas;
		planta = random.nextInt(plantas);
		sentido = random.nextInt(2);
	}
	
	public void run() {
		while (true) {
			
		}
	}

	public void solicitarPlanta(int planta) {
		if (planta >= 0 && planta < plantas) {
			edificio.getLock().lock();
			try {
				solicitudes.add(planta);
			} finally {
				edificio.getCondition().signalAll();
				edificio.getLock().unlock();
			}
			try {
				esperasDestinoAlcanzado.get(planta).await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	private synchronized void irAlSiguienteDestino() {
		edificio.getLock().lock();
		try {
			while (solicitudes.isEmpty() && edificio.sinLlamadas()) {
				try {
					edificio.getCondition().await();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			boolean seleccionado = false;
			do {
				planta += sentido;
				seleccionado = solicitudes.remove(planta);
				if (!seleccionado)
					seleccionado = solicitudes.remove(planta);
				if (seleccionado && (planta == plantas - 1 || planta == 0))
						sentido = -sentido;
			} while (!seleccionado);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
		}
		finally {
			edificio.getLock().unlock();
		}
	}
	
}
