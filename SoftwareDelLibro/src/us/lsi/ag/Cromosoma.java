package us.lsi.ag;

import java.util.List;

public interface Cromosoma<T> {
	/**
	 * @return La secuencia decodificada seg�n la informaci�n en el cromosoma
	 */
	List<T> decode();
	
	
	/**
	 * @return Fitness del cromosoma
	 */
	double fitness();
		
}
