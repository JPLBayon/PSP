package fp.dam.psp.threads.fumadores.solucion.bajonivel;

public class Agente extends Thread{

	private Mesa mesa;
	private CtrlPausa ctrlPausa = new CtrlPausa();
	
	public Agente(Mesa mesa) {
		super("Agente");
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
			Ingrediente i1 = Ingrediente.get();
			Ingrediente i2 = Ingrediente.get();
			while(i1 == i2)
				i2 = Ingrediente.get();
			mesa.depositar(i1, i2);
		}
		System.out.println("El agente finaliza su tarea");
	}
}
