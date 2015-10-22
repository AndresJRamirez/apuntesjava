package us.lsi.graphs;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EnumVirtualVertex<A extends Enum<A>, V extends EnumVirtualVertex<A,V>> 
			implements VirtualVertex<V, SimpleEdge<V>> {

	public EnumVirtualVertex() {
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
	 * @param v V�rtice opuesto
	 * @return Devuelve una arista del v�rtice actual al opuesto
	 */
	protected abstract SimpleEdge<V> createEdgeTo(V v);
	
	/**
	 * Para ser implementado por el subtipo
	 * @return Lista de valores del tipo enumerado A
	 */
	protected abstract List<A> values();
	private Set<V> neighbors = null;
	private Set<SimpleEdge<V>> edges = null;

	@Override
	public Set<V> getNeighborListOf() {
		if (neighbors == null) {
			neighbors = values().stream().filter(x -> this.isApplicable(x))
					.map(x -> this.neighbor(x)).collect(Collectors.toSet());
		}
		return neighbors;
	}

	@Override
	public Set<SimpleEdge<V>> edgesOf() {
		if (edges == null) {
			edges = values().stream().filter(x -> this.isApplicable(x))
					.map(x -> createEdgeTo(this.neighbor(x)))
					.collect(Collectors.toSet());
		}
		return edges;
	}

	@Override
	public boolean isNeighbor(V e) {
		return this.getNeighborListOf().contains(e);
	}

}
