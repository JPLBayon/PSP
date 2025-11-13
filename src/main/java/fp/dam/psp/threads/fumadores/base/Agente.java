package fp.dam.psp.threads.fumadores.base;

public class Agente extends Thread{

	private Mesa mesa;
	
	public Agente(Mesa mesa) {
		super("Agente");
		this.mesa = mesa;
	}
	
	@Override
	public void run() {
		while (true) {
			Ingrediente i1 = Ingrediente.get();
			Ingrediente i2 = Ingrediente.get();
			while(i1==i2)
				i2 = Ingrediente.get();
			mesa.depositar(i1, i2);
		}
		// TODO quitar el comentario de la línea siguiente cuando se pueda finalizar el hilo (es decir, cuando el bucle ya no sea infinito)
//		actualizar ("El agente finaliza su tarea");
	}
}
