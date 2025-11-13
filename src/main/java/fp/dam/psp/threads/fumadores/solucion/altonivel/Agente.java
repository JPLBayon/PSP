package fp.dam.psp.threads.fumadores.solucion.altonivel;

import fp.dam.psp.threads.fumadores.solucion.Pausometro;

public class Agente implements Runnable{

	private Mesa mesa;
	private Pausometro pausometro = new Pausometro();
	
	public Agente(Mesa mesa) {
		this.mesa = mesa;
	}
	
	public void pausar() {
		pausometro.pausar();
	}
	
	public void reanudar() {
		pausometro.reanudar();
	}
	
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			pausometro.check();
			Ingrediente i1 = Ingrediente.get();
			Ingrediente i2 = Ingrediente.get();
			while(i1 == i2)
				i2 = Ingrediente.get();
			mesa.depositar(i1, i2);
		}
		System.out.println("El agente finaliza su tarea");
	}
}
