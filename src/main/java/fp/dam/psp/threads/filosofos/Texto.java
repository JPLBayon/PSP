package fp.dam.psp.threads.filosofos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Texto {
	private static final FontRenderContext FRC = new FontRenderContext(null, true, false);
	
	public static void dibujar(Graphics2D g, String text, Rectangle2D.Float rect, boolean centrado, Color color, Color sombra) {
		FontMetrics fm = g.getFontMetrics();
		String[] lineas = text.split("\n");
		Dimensiones d = getBounds(lineas, g.getFont());
		int y = centrado ? ((int) rect.getHeight() - d.height) / 2 : 0;
		y += fm.getAscent() + 10;
		for (int i = 0; i < lineas.length; i++) {
			if (sombra != null) {
				g.setColor(sombra);
				g.drawString(lineas[i], (centrado ? (int) (((int) (rect.x + rect.getWidth() - d.linea[i].getWidth()) / 2)) : rect.x) + 2, y + 2);
			}
			g.setColor(color);
			g.drawString(lineas[i], centrado ? (int) (((int) (rect.x + rect.getWidth() - d.linea[i].getWidth()) / 2)) : rect.x, y);
			y += d.linea[i].getHeight();
		}
	}

	private static Dimensiones getBounds(String [] lineas, Font font) {
		int width = Integer.MIN_VALUE;
		int height = 0;
		Rectangle2D[] bounds = new Rectangle2D[lineas.length];
		for (int i = 0; i < lineas.length; i++) {
			bounds[i] = font.getStringBounds(lineas[i], FRC);
			height += bounds[i].getHeight();
			if (bounds[i].getWidth() > width)
				width = (int) bounds[i].getWidth();
		}
		return new Dimensiones(width, height, bounds);
	}
	
	private static class Dimensiones {
//		public int width;
		public int height;
		public Rectangle2D [] linea;
	 	public Dimensiones(int width, int height, Rectangle2D [] linea) {
//	 		this.width = width;
	 		this.height = height;
	 		this.linea = linea;
	 	}
	}
}
