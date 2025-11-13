package fp.dam.psp.threads.fumadores.solucion.bajonivel;

public class CtrlPausa {
	private boolean pausado;
	
	public synchronized void pausar() {
		pausado = true;
	}
	
	public synchronized void reanudar() {
		if (pausado) {
			pausado = false;
			notify();
		}
	}
	
	public synchronized void check() {
		if (pausado)
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
	}
}
