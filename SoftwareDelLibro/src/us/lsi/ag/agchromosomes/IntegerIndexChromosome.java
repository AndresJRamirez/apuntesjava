package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGIndex;
import us.lsi.common.Lists2;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 * 
 * 
 * <p> Una implementaci�n del tipo Cromosoma&lt;Integer&gt;. Toma como informaci�n la definici�n de un problema que implementa el interfaz ProblemaAGBag.
 * Asumimos que el n�mero de objetos es n. Usa una  secuencia normal impl�cita de tama�o n de la forma (0,1,2, ..., n-1). </p>
 * 
 * <p> La lista decodificada est� formada por una lista de  tama�o n cuyos elementos para cada i son 
 *  valores en en rango [0,m(i)], sinendo m(i) la multiplicidad m�xima para i. </p>
 * 
 * <p> La implementaci�n usa un cromosoma binario del tama�o n*nbits. 
 * Siendo nbits el n�mero de bits usados para representar cada uno de los enteros. </p>
 * 
 * <p> Es un cromosoma adecuado para codificar problemas de subconjuntos de multiconjuntos</p>
 *
 */
public class IntegerIndexChromosome extends BinaryChromosome implements IndexChromosome {
	
	public static Integer numeroDeBits = 5;
	
	public static ProblemaAGIndex<?> problema;
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(ProblemaAG problema){
		IntegerIndexChromosome.problema = (ProblemaAGIndex<?>) problema; 
		IntegerIndexChromosome.DIMENSION = IntegerIndexChromosome.numeroDeBits*IntegerIndexChromosome.problema.getObjectsNumber();
	}
	
	private static Integer pow = Math2.pow(2., numeroDeBits).intValue();
	
	public IntegerIndexChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public IntegerIndexChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
		return new IntegerIndexChromosome(ls);
	}
	
	public List<Integer> decode() {
		List<Integer> ls = super.getRepresentation();
		List<Integer> r = new ArrayList<Integer>();
		int index1 = 0;
		for(int i = 0; i < getObjectsNumber(); i++){			
			int index2 = index1+numeroDeBits;
			Integer e = Lists2.decode(ls.subList(index1, index2));
			Integer d = Math2.escalaIncluded(e, pow, getMax(i));
			r.add(d);
			index1 = index1+numeroDeBits;;
		}
		return r;
	}

	public List<Integer> getRepresentation(){
		return super.getRepresentation();
	}
	
	public static IntegerIndexChromosome getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(IntegerIndexChromosome.DIMENSION);
		return new IntegerIndexChromosome(ls);
	}

	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return IntegerIndexChromosome.problema.fitnessFunction(this);
	}

	@Override
	public Integer getObjectsNumber() {
		return IntegerIndexChromosome.problema.getObjectsNumber();
	}

	@Override
	public Integer getMax(int i) {
		return IntegerIndexChromosome.problema.getMax(i);
	}

	@Override
	public ProblemaAGIndex<?> getProblem() {
		return IntegerIndexChromosome.problema;
	}

	@Override
	public Chromosome asChromosome() {
		return this;
	}
	
	
	
}
