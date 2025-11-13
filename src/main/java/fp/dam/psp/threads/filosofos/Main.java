package fp.dam.psp.threads.filosofos;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends WindowAdapter  {

	private Lienzo lienzo;
	private Thread engine;
	
	private void crearGUI() {
		JFrame frame = new JFrame("La Cena de Filósofas y Filósofos Chinos");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finalizar();
			}
		});
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					finalizar();
			}
		});
		frame.add(lienzo = new Lienzo(1000, 1000));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		lienzo.createBufferStrategy(2);
		engine = new Thread(lienzo::run);
		engine.setPriority(Thread.MAX_PRIORITY);
		engine.start();
	}
	
	private void finalizar() {
		lienzo.finalizar();
		engine.interrupt();
		try {
			engine.join();
		} catch (InterruptedException e1) {
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		Recursos.cargarRecursos(0, 0);
		SwingUtilities.invokeLater(new Main()::crearGUI);
	}

}
