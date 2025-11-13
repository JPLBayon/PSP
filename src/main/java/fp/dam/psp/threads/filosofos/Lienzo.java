package fp.dam.psp.threads.filosofos;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;

public class Lienzo extends Canvas {
	
	private static final long serialVersionUID = 1L;
	
	private final String [] nombres = {"孔夫子", "楊朱", "謝道韞", "商鞅", "吳誠真"};
	private final Color colorNombres = new Color(145, 102, 127);

	private final int FILOSOFOS = 5;
	private final int RADIOPLATO = 130;
	private final int LARGOPALILLO = 200;
	
	private Filósofo [] filosofos = new Filósofo[FILOSOFOS];
	
	private double angulo;
	private double anguloRotacion;
	private int xPlato;
	private int yPlato;
	private int xPalillo;
	private int yPalillo;
	private int xFlechaD;
	private int xFlechaI;
	private int yFlecha;
	private int cx;
	private int cy;
	private double scalePlato;
	private double scalePalillo;
	private double scaleFlecha;
		
	private Font fuente = Recursos.chineseFont.deriveFont(37f);
	private Font fuente2 = Recursos.chineseFont.deriveFont(31f);
	private BufferedImage capa1;
	private BufferedImage capa2;
	private BufferStrategy buffer;
	private RenderingHints rh;
	
	public Lienzo(int w, int h) {
		setSize(w, h);

		Semaphore mesa = new Semaphore(5);
		Lock palilloIzda;
		Lock palilloDcha;
		Lock primero = palilloDcha = new ReentrantLock();
		for (int i=0; i<FILOSOFOS; i++) {
			palilloIzda = i < FILOSOFOS - 1 ? new ReentrantLock() : primero;
			filosofos[i] = new Filósofo(nombres[i], mesa, palilloIzda, palilloDcha);
			palilloDcha = palilloIzda;
		}
		
//		int d = w < h ? w / 2 : h / 2;
		scalePlato = RADIOPLATO * 2 / Recursos.comer.getWidth();
		scalePalillo = LARGOPALILLO / Recursos.palillo.getHeight();
		scaleFlecha = 30 / Recursos.flechad.getHeight();
		cx = w / 2;
		cy = h / 2;
		xPlato = cx - RADIOPLATO;
		yPlato = 60;
		xPalillo = cx - (int) (Recursos.palillo.getWidth() * scalePalillo / 2);;
		yPalillo = 90;
		xFlechaI = cx + RADIOPLATO + 25;
		xFlechaD = cx - RADIOPLATO - (int) (Recursos.flechai.getWidth() * scaleFlecha) - 25;
		yFlecha = yPalillo + 70;
		angulo = (Math.PI * 2) / (FILOSOFOS * 2);
		rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		crearImagenes(w, h);
	}
	
	private void crearImagenes(int w, int h) {
		Graphics2D g;
		g = (capa1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)).createGraphics();
		g.setColor( Color.BLACK );
		g.fillRect( 0, 0, getWidth(), getHeight());
		g.setRenderingHints(rh);
		g.setColor(Color.WHITE);
		g.fillArc(10, 10, w - 20, h - 20, 0, 360);
		g.setFont(fuente2);
		Texto.dibujar(g, "中国男女哲学家的晚宴", new Rectangle2D.Float(0, 0, w, h), true, Color.RED, Color.GRAY);
		g = (capa2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)).createGraphics();
		g.setRenderingHints(rh);
		g.setFont(fuente.deriveFont(29f));
		for (int i=0; i<FILOSOFOS; i++) {
			Texto.dibujar(g, nombres[i], new Rectangle2D.Float(0, 0, w, 55), true, colorNombres, Color.LIGHT_GRAY);
			g.rotate(angulo * 2, cx, cy);
		}
	}
		
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(capa1, 0, 0, this);
		g2d.rotate(anguloRotacion, cx, cy);
		g2d.drawImage(capa2, 0, 0, this);
		g2d.setRenderingHints(rh);
		
		for (int i=0; i<FILOSOFOS; i++) {
			SVGDiagram svg = filosofos[i].getSVG();
			AffineTransform af = g2d.getTransform();
			g2d.translate(xPlato, yPlato);
			g2d.scale(scalePlato, scalePlato);
			try {
				svg.render(g2d);
				g2d.setTransform(af);
				if (filosofos[i].tienePalilloDcha()) {
					g2d.translate(xFlechaD, yFlecha);
					g2d.scale(scaleFlecha, scaleFlecha);
					Recursos.flechad.render(g2d);
				}
				g2d.setTransform(af);
				if (filosofos[i].tienePalilloIzda()) {
					g2d.translate(xFlechaI, yFlecha);
					g2d.scale(scaleFlecha, scaleFlecha);
					Recursos.flechai.render(g2d);
				}
				g2d.setTransform(af);
				g2d.rotate(angulo, cx, cy);
				af = g2d.getTransform();
				g2d.translate(xPalillo, yPalillo);
				g2d.scale(scalePalillo, scalePalillo);
				Recursos.palillo.render(g2d);
			} catch (SVGException e) {
				e.printStackTrace();
			}
			g2d.setTransform(af);
			g2d.rotate(angulo, cx, cy);
		}
	}
	
	public void sgteFrame() {
		Graphics g = null;
		try {
			g = buffer.getDrawGraphics();
			paint(g);
			if(!buffer.contentsLost())
				buffer.show();
		} finally {
			if( g != null ) 
				g.dispose();
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void run() {
//		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		for (Filósofo f: filosofos)
			f.start();
		buffer = getBufferStrategy();
		long t0 = System.nanoTime(), t1, lapso;
		while (!Thread.interrupted()) {
			t1 = System.nanoTime();
			lapso = t1 - t0;
			t0 = t1;
			anguloRotacion += lapso * (2 * Math.PI / 60) / 1000000000d;
			sgteFrame();
			Thread.yield();
		}
		System.out.println("la cena ha finalizado");
	}
	
	public void finalizar() {
		for (int i=0; i<filosofos.length; i++) {
			filosofos[i].interrupt();
			try {
				filosofos[i].join();
			} catch (InterruptedException e) {
			}
		}
	}
	
}
