package fp.dam.psp.threads.fumadores.solucion.altonivel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
	private static final JTextArea textArea = new JTextArea();
	private JButton pausa = new JButton("PAUSA");
	private JButton reanudar = new JButton("REANUDAR");
	
	private ExecutorService executor = Executors.newFixedThreadPool(4);
	private Mesa mesa = new Mesa();
 	private Fumador f1 = new Fumador("Fernándo", Ingrediente.TABACO, mesa);
	private Fumador f2 = new Fumador("Manuela", Ingrediente.CERILLAS, mesa);
	private Fumador f3 = new Fumador("Carmen", Ingrediente.PAPEL, mesa);
	private Agente agente = new Agente(mesa);
	
	public Main() {
		super("Fumadores");
		this.addWindowListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setPreferredSize(new Dimension(900, 700));
		JPanel panel = new JPanel();
		pausa.addActionListener(this::pausa);
		panel.add(pausa, BorderLayout.WEST);
		reanudar.setEnabled(false);
		reanudar.addActionListener(this::reanudar);
		panel.add(reanudar, BorderLayout.EAST);
		contentPane.add(panel, BorderLayout.NORTH);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
	}
	
	public static void actualizar(String msg) {
		SwingUtilities.invokeLater(() -> textArea.append(msg));
	}
	
	private void pausa(ActionEvent e) {
		pausa.setEnabled(false);
		reanudar.setEnabled(true);
		f1.pausar();
		f2.pausar();
		f3.pausar();
		agente.pausar();
	}
	
	private void reanudar(ActionEvent e) {
		pausa.setEnabled(true);
		reanudar.setEnabled(false);
		f1.reanudar();
		f2.reanudar();
		f3.reanudar();
		agente.reanudar();
	}
	
	private void iniciar() {
		setVisible(true);
		executor.submit(f1);
		executor.submit(f2);
		executor.submit(f3);
		executor.submit(agente);
	}
	
	private static void crear() {
		new Main().iniciar();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Main::crear);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		executor.shutdownNow();
		try {
			executor.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException ex) {}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
