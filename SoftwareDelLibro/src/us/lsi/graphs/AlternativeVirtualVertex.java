package us.lsi.graphs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Miguel Toro
 *
 * @param <A> Tipo de la alternativa
 * @param <V> Tipo del v�rtice
 * 
 * 
 * <a> Tipo adecuado para modelar un v�rtice de un grafo virtual simple cuyas aristas est�n 
 * definidas por un conjunto finito de alternativas. 
 * Cada alternativa v�lida identifica de forma �nica uno de los v�cinos del v�rtice. 
 * Cada v�rtice conce sus vecinos y la forma de llegar a ellos medainte una de las alternativas v�lidas disponibles </a>
 */
public abstract class AlternativeVirtualVertex<A, V extends AlternativeVirtualVertex<A,V>> 
			implements VirtualVertex<V, AlternativeSimpleEdge<A,V>> {

	public AlternativeVirtualVertex() {
	}
	
	/**
	 * @return Si es un valor v�lido del tipo
	 */
	public abstract boolean isValid();
	/**
	 * @param a Una acci�n
	 * @return El vecino tras tomar esa acci�n
	 */
	public abstract V neighbor(A a);
	/**
	 * @param a Una acci�n
	 * @return Si la acci�n es aplicable en este v�rtice
	 */
	public abstract boolean isApplicable(A a);
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de valores del tipo enumerado A
	 */
	protected abstract List<A> values();
	protected abstract V getThis();
	
	private Set<V> neighbors = null;
	private Set<AlternativeSimpleEdge<A,V>> edges = null;
	

	@Override
	public Set<V> getNeighborListOf() {
		if (neighbors == null) {
			neighbors = values().stream()
					.filter(x -> this.isApplicable(x))
					.map(x -> this.neighbor(x))
					.collect(Collectors.toSet());
		}
		return neighbors;
	}

	@Override
	public Set<AlternativeSimpleEdge<A,V>> edgesOf() {
		if (edges == null) {
			edges = values().stream()
					.filter(x -> this.isApplicable(x))
					.<AlternativeSimpleEdge<A,V>>map(x -> AlternativeSimpleEdge.create(getThis(),this.neighbor(x),x))
					.collect(Collectors.toSet());
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

	
}
