package fp.dam.psp.threads.filosofos;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import com.kitfox.svg.SVGDiagram;

public class Filósofo extends Thread {

	private Semaphore mesa;
	private Lock izda;
	private Lock dcha;
	private Estado estado;
	
	public Filósofo(String nombre, Semaphore mesa, Lock izda, Lock dcha) {
		super(nombre);
		this.mesa = mesa;
		this.izda = izda;
		this.dcha = dcha;
		estado = new Estado(Recursos.pensar, false, false);
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Thread.sleep((long) (Math.random() * 4000 + 1000));
//				estado.setSVG(Recursos.esperar);
				mesa.acquire(1);
				estado.setSVG(Recursos.esperar);
				if (izda.tryLock()) {
					estado.setIzda(true);
					dcha.lockInterruptibly();
					estado.setDcha(true);
				}
				else {
					dcha.lockInterruptibly();
					estado.setDcha(true);
					izda.lockInterruptibly();
					estado.setIzda(true);
				}
				estado.setSVG(Recursos.comer);
				Thread.sleep((long) (Math.random() * 4000 + 1000));
				dcha.unlock();
				estado.setDcha(false);
				izda.unlock();
				estado.setIzda(false);
				mesa.release();
				estado.setSVG(Recursos.pensar);
			} catch (InterruptedException e) {
				interrupt();
			}
		}
		System.out.println(getName() + " se ha ido");
	}
	
//	@Override
//	public void run() {
//		while (!interrupted()) {
//			try {
//				Thread.sleep((long) (Math.random() * 4000 + 1000));
//				estado.setSVG(Recursos.esperar);
//				mesa.acquire(1);
//				if (izda.tryLock()) {
//					estado.setIzda(true);
//					if (dcha.tryLock()) {
//						estado.setDcha(true);
//						estado.setSVG(Recursos.comer);
//						Thread.sleep((long) (Math.random() * 4000 + 1000));
//						dcha.unlock();
//						estado.setDcha(false);
//					}
//					izda.unlock();
//					estado.setIzda(false);
//				}
//				mesa.release();
//				estado.setSVG(Recursos.pensar);
//			} catch (InterruptedException e) {
//				interrupt();
//			}
//		}
//		System.out.println(getName() + " se ha ido");
//	}
	
	public SVGDiagram getSVG() {
		return estado.getSVG();
	}
	
	public boolean tienePalilloIzda() {
		return estado.isIzda();
	}
	
	public boolean tienePalilloDcha() {
		return estado.isDcha();
	}
	
}
