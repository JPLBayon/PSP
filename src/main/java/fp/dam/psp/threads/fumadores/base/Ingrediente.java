package fp.dam.psp.threads.fumadores.base;

public enum Ingrediente {

	TABACO, CERILLAS, PAPEL;
	
	public static Ingrediente get() {
		return values()[(int)(Math.random()*1000)%values().length];
	}

}
