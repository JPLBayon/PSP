package fp.dam.psp.threads.filosofos;

import com.kitfox.svg.SVGDiagram;

public class Estado {
	
	private SVGDiagram svg;
	private boolean izda;
	private boolean dcha;
	
	public Estado(SVGDiagram svg, boolean izda, boolean dcha) {
		this.svg = svg;
		this.izda = izda;
		this.dcha = dcha;
	}

	public synchronized SVGDiagram getSVG() {
		return svg;
	}

	public synchronized void setSVG(SVGDiagram svg) {
		this.svg = svg;
	}

	public synchronized boolean isIzda() {
		return izda;
	}

	public synchronized void setIzda(boolean izda) {
		this.izda = izda;
	}

	public synchronized boolean isDcha() {
		return dcha;
	}

	public synchronized void setDcha(boolean dcha) {
		this.dcha = dcha;
	}
	
	
	

}
