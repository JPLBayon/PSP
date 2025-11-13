package fp.dam.psp.threads.fumadores.base;

import static fp.dam.psp.threads.fumadores.base.Main.actualizar;

public class Fumador extends Thread{
	Ingrediente ingrediente;
	Mesa mesa;
	
	public Fumador(String nombre, Ingrediente ingrediente, Mesa mesa) {
		super(nombre);
		this.ingrediente = ingrediente;
		this.mesa = mesa;
	}

	@Override
	public void run() {
		while(true) {
			mesa.retirar(ingrediente);
			try {
				sleep(1000);
			} catch (InterruptedException e) {}
			actualizar(getName() + " terminó de fumar\n");
		}
		// TODO quitar el comentario de la línea siguiente cuando se pueda finalizar el hilo (es decir, cuando el bucle ya no sea infinito)
//		actualizar (getName() + " finaliza su tarea");
	}
}
