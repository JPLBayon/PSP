package fp.dam.psp.threads.fumadores.base;

import static fp.dam.psp.threads.fumadores.base.Main.actualizar;

import java.util.HashSet;
import java.util.stream.Collectors;

public class Mesa {

	HashSet<Ingrediente> ingredientes = new HashSet<>();

	public synchronized void depositar(Ingrediente i1, Ingrediente i2) {
		while(!ingredientes.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		ingredientes.add(i1);
		ingredientes.add(i2);
		actualizar("El agente depositó " + i1 + " y " + i2 + "\n");
		notifyAll();
	}

	public synchronized void retirar(Ingrediente i) {
		String fumador = Thread.currentThread().getName();
		while(ingredientes.isEmpty() || ingredientes.contains(i)) {
			actualizar(fumador + " tiene que esperar\n");
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		actualizar(ingredientes.stream().map(j->j.toString()).collect(Collectors.joining(" y ", fumador + " retira " , "\n")));
		ingredientes.clear();
		notifyAll();
	}
 

}
