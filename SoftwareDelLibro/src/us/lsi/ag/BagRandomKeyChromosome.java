package us.lsi.ag;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import com.google.common.base.Preconditions;

/**
 * @author Miguel Toro
 * 
 * <p> Una implementaci�n del tipo Cromosoma&lt;Integer&gt;. Toma como informaci�n la definici�n de un problema que implementa el interfaz ProblemaAGBag.
 * A partir de esa informaci�n construye una secuencia normal. Asumimos que el n�mero de objetos es n y el tama�o de la secuencia normal r. 
 *  
 * La lista decodificada est� formada por una lista de  tama�o r, cuyos valores son 
 * �ndices en el rango [0,n-1], y cada �ndice i se  repite un n�mero de veces dado por la multiplicidad m�xima del objeto i
 * definida en el problema.
 * 
 * La implementaci�n usa un cromosoma de clave aleatoria de tama�o r.
 * Es un cromosoma adecuado para codificar problemas de permutaciones </p>
 *
 */
public class BagRandomKeyChromosome extends RandomKey<Integer> implements Cromosoma<Integer> {

	public static Function<List<Integer>,Double> fitnessFunction;
	public static List<Integer> normalSequence;
	
	public BagRandomKeyChromosome(List<Double> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public BagRandomKeyChromosome(Double[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new BagRandomKeyChromosome(ls);
		
	}

	@Override
	public List<Integer> decode() {
		Preconditions.checkArgument(BagRandomKeyChromosome.normalSequence!=null);
		return this.decode(BagRandomKeyChromosome.normalSequence);
	}
	
	
	public static BagRandomKeyChromosome getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(AlgoritmoAG.DIMENSION);
		return new BagRandomKeyChromosome(ls);
	}

	
	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return BagRandomKeyChromosome.fitnessFunction.apply(this.decode());
	}	
}