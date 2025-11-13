package fp.dam.psp.threads.fumadores.solucion.bajonivel;

import static fp.dam.psp.threads.fumadores.solucion.bajonivel.Main.actualizar;

public class Fumador extends Thread{
	private Ingrediente ingrediente;
	private Mesa mesa;
	private CtrlPausa ctrlPausa = new CtrlPausa();
	
	public Fumador(String nombre, Ingrediente ingrediente, Mesa mesa) {
		super(nombre);
		this.ingrediente = ingrediente;
		this.mesa = mesa;
	}

	public void pausar() {
		ctrlPausa.pausar();
	}
	
	public void reanudar() {
		ctrlPausa.reanudar();
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			ctrlPausa.check();
			mesa.retirar(ingrediente);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				interrupt();
			}
			actualizar(getName() + " terminó de fumar\n");
		}
		System.out.println(getName() + " finaliza su tarea");
	}
}
