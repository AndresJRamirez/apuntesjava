package us.lsi.ag;


import us.lsi.ag.agchromosomes.IBinaryChromosome;

/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solcui�n del problema
 * 
 * 
 * <p> Un problema cuya soluci�n puede ser modelada con variables binarias. Usa un cromomosoma de tipo IBinaryChromosom</p>
 */
public interface ProblemaAGBinario<S> extends ProblemaAG {
	
	/**
	 * @return Dimensi�n del cromosoma. Es el n�mero de variables binarias del problema
	 */
	int getDimensionDelChromosoma();
	
	/**
	 * @param cr Un cromosoma
	 * @return La funci�n de fitness del cromosoma
	 */
	
	Double fitnessFunction(IBinaryChromosome cr);
	
	/**
	 * @param cr Un cromosoma
	 * @return La soluci�n definida por el cromosoma
	 */
	S getSolucion(IBinaryChromosome cr);
}
