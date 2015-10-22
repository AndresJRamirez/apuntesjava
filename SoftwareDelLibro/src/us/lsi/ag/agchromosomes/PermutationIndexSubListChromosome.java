package us.lsi.ag.agchromosomes;

import java.util.List;




import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;



import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.ProblemaAG;
import us.lsi.ag.ProblemaAGIndex;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;


/**
 * 
 * 
 * 
 * 
 * @author Miguel Toro
 * 
 * 
 * Una implementaci�n del tipo Cromosoma&lt;Integer&gt;. Toma como informaci�n la definici�n de un problema que implementa el interfaz ProblemaAGBag.
 * A partir de esa informaci�n construye una secuencia normal. Asumimos que el n�mero de objetos es n y el tama�o de la secuencia normal r. 
 *  
 * La lista decodificada est� formada por una lista de  tama�o menor o igual que r, cuyos valores son 
 * �ndices en el rango [0,n-1], y cada �ndice i se puede repetir un m�ximo n�mero de veces dado por la multiplicidad m�xima del objeto i
 * definida en el problema.
 * 
 * La implementaci�n usa un cromosoma binario y otro de tipo clave aleatoria. Ambos de tama�o r.
 * Es un cromosoma adecuado para codificar problemas de permutaciones de subconjuntos de multiconjuntos
 *
 */
public class PermutationIndexSubListChromosome extends Chromosome implements IndexChromosome {

	public static List<Integer> normalSequence;
	
	public static ProblemaAGIndex<?> problema;
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	protected static int DIMENSION;
	
	
	public static void iniValues(ProblemaAG problema){
		PermutationIndexSubListChromosome.problema = (ProblemaAGIndex<?>) problema; 
		PermutationIndexSubListChromosome.normalSequence = PermutationIndexSubListChromosome.problema.getNormalSequence();
		PermutationIndexSubListChromosome.DIMENSION = PermutationIndexSubListChromosome.normalSequence.size();
	}
	
	private BinaryChromosome2 binary;
	private RandomKey2 randomKey;	
	
	
	
	/**
	 * @param binary Un cromosoma binario
	 * @param randomKey Un cromosoma randomKey
	 */
	public PermutationIndexSubListChromosome(Chromosome binary, Chromosome randomKey) {
		super();
		if(binary instanceof BinaryChromosome2){
			this.binary = (BinaryChromosome2)binary;
		}
		if(randomKey instanceof RandomKey2){
			this.randomKey = (RandomKey2) randomKey;
		}
		Preconditions.checkArgument(this.binary!=null);
		Preconditions.checkArgument(this.randomKey!=null);
		Preconditions.checkArgument(this.binary.getLength()==this.randomKey.getLength());	
		this.ft = this.calculateFt();
	}
	
	/**
	 * Un constructor adecuado para crear un objeto por defecto de este tipo
	 */
	public PermutationIndexSubListChromosome() {
		super();
		List<Integer> ls1 = BinaryChromosome2.randomBinaryRepresentation(100);
		List<Double>  ls2 = RandomKey2.randomPermutation(100);
		BinaryChromosome2 c1 = new BinaryChromosome2(ls1);
		RandomKey2 c2 = new RandomKey2(ls2);
		this.binary =  c1;
		this.randomKey = c2;	
		this.ft = 0.;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.math3.genetics.Fitness#fitness()
	 */
	
	
	@Override
	public double fitness() {
		return ft;
	}
	
	private Double ft;
	
	private double calculateFt(){
		return PermutationIndexSubListChromosome.problema.fitnessFunction(this.decode());
	}
	
	@Override
	public ProblemaAGIndex<?> getProblem() {
		return PermutationIndexSubListChromosome.problema;
	}

	@Override
	public Integer getObjectsNumber() {
		return PermutationIndexSubListChromosome.problema.getObjectsNumber();
	}

	@Override
	public Integer getMax(int i) {
		return PermutationIndexSubListChromosome.problema.getMax(i);
	}
	
	
	public int compareTo(Chromosome another) {
		if (!(another instanceof PermutationIndexSubListChromosome))
			throw new IllegalArgumentException();;
		PermutationIndexSubListChromosome other = (PermutationIndexSubListChromosome) another;
		Double f1 = this.fitness();
		Double f2 = other.fitness();
		return f1.compareTo(f2);
	}
	
	/**
	 * @return Una lista de enteros obtenida permutando la secuencia normal (0, 1, 2, ..., r-1) filtrada para incluir 
	 * s�lo los seleccionados por el cromosoma binario 
	 */
	public List<Integer> decode() {	
		List<Integer> rk = randomKey.decode(normalSequence);
		List<Integer> r = Lists.newArrayList();
		List<Integer> bn = binary.getRepresentation();
		Preconditions.checkArgument(rk.size()==bn.size());
		for(int i=0; i< rk.size();i++){
			if(bn.get(i)==1){
				r.add(rk.get(i));
			}
		}
		return r;
	}

	/**
	 * @return La dimensi�n del cromosoma
	 */
	public int getLength() {
		return randomKey.getLength();
	}	
	
	@Override
	public Chromosome asChromosome() {
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PermutationIndexSubListChromosome))
			return false;
		PermutationIndexSubListChromosome other = (PermutationIndexSubListChromosome) obj;
		if (binary == null) {
			if (other.binary != null)
				return false;
		} else if (!binary.equals(other.binary))
			return false;
		if (randomKey == null) {
			if (other.randomKey != null)
				return false;
		} else if (!randomKey.equals(other.randomKey))
			return false;
		return true;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((binary == null) ? 0 : binary.hashCode());
		result = prime * result
				+ ((randomKey == null) ? 0 : randomKey.hashCode());
		return result;
	}

	public String toString() {
		return this.fitness()+","+this.decode();
	}

	
	
	public BinaryChromosome getBinary() {
		return binary;
	}

	public RandomKey<Integer> getRandomKey() {
		return randomKey;
	}
	
	/**
	 * @param dimension La dimensi�n del cromosoma
	 * @pre Debe estar inicializada la propiedad factory
	 * @return Un cromosoma mixto aleatorio
	 * 
	 */
	private static PermutationIndexSubListChromosome random(Integer dimension){
		List<Integer> ls1 = BinaryChromosome2.randomBinaryRepresentation(dimension);
		List<Double>  ls2 = RandomKey2.randomPermutation(dimension);
		BinaryChromosome2 c1 = new BinaryChromosome2(ls1);
		RandomKey2 c2 = new RandomKey2(ls2);
		return new PermutationIndexSubListChromosome(c1, c2);
	}
	
	public static PermutationIndexSubListChromosome getInitialChromosome() {
		return PermutationIndexSubListChromosome.random(PermutationIndexSubListChromosome.DIMENSION);
	}

	private static class BinaryChromosome2 extends BinaryChromosome {		

		public BinaryChromosome2(Integer[] representation)
				throws InvalidRepresentationException {
			super(representation);
		}

		public BinaryChromosome2(List<Integer> representation)
				throws InvalidRepresentationException {
			super(representation);
		}

		@Override
		public double fitness() {
			return 0;
		}

		@Override
		public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ls) {
			return new BinaryChromosome2(ls);
		}
		 
		@Override
		public List<Integer> getRepresentation(){
			return super.getRepresentation();
		}	
	}
	
	private static class RandomKey2 extends  RandomKey<Integer> {
		
		
		public RandomKey2(Double[] representation) throws InvalidRepresentationException {
			super(representation);
		}

		public RandomKey2(List<Double> representation) throws InvalidRepresentationException {
			super(representation);
		}

		@Override
		public double fitness() {
			return 0;
		}

		@Override
		public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
			return new RandomKey2(ls);
		}
	}

	

	
	
	
	
}
