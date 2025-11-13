package fp.dam.psp.threads.fumadores.solucion.altonivel;

import static fp.dam.psp.threads.fumadores.solucion.altonivel.Main.actualizar;

import fp.dam.psp.threads.fumadores.solucion.Pausometro;


public class Fumador implements Runnable {
	private Ingrediente ingrediente;
	private Mesa mesa;
	private Pausometro pausometro = new Pausometro();
	private String nombre;
	
	public Fumador(String nombre, Ingrediente ingrediente, Mesa mesa) {
		this.nombre = nombre;
		this.ingrediente = ingrediente;
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
		Thread.currentThread().setName(nombre);
		while(!Thread.currentThread().isInterrupted()) {
			pausometro.check();
			mesa.retirar(ingrediente);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			actualizar(nombre + " terminó de fumar\n");
		}
		System.out.println(nombre + " finaliza la tarea");
	}
}
