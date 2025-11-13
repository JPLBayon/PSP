package fp.dam.psp.threads.filosofos;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

public class Recursos {
	
	public static Font chineseFont;
	public static SVGDiagram pensar;
	public static SVGDiagram esperar;
	public static SVGDiagram comer;
	public static SVGDiagram palillo;
	public static SVGDiagram flechai;
	public static SVGDiagram flechad;
	
	private static Font cargarFuente(String resource) {
		InputStream s = Recursos.class.getResourceAsStream("/filosofos/" + resource);
		Font font = null;
		try {
			if (s != null) {
				try {
					font = Font.createFont(Font.PLAIN, s);
				} finally {
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else
				throw new NoSuchElementException("No se encuentra el recurso que contiene la fuente: " + resource);
		} catch (NoSuchElementException | FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return font;
	}
	
	public static void cargarRecursos(int screenWidth, int screenHeight) {
		chineseFont = cargarFuente("HanyiSentyPagoda.ttf");
		SVGUniverse universe = new SVGUniverse();
		pensar = universe.getDiagram(universe.loadSVG(Recursos.class.getResource("/filosofos/pensar.svg")));
		esperar = universe.getDiagram(universe.loadSVG(Recursos.class.getResource("/filosofos/esperar.svg")));
		comer = universe.getDiagram(universe.loadSVG(Recursos.class.getResource("/filosofos/comer.svg")));
		palillo = universe.getDiagram(universe.loadSVG(Recursos.class.getResource("/filosofos/palillo.svg")));
		flechai = universe.getDiagram(universe.loadSVG(Recursos.class.getResource("/filosofos/flechaizda.svg")));
		flechad = universe.getDiagram(universe.loadSVG(Recursos.class.getResource("/filosofos/flechadcha.svg")));
	}
	
}
