package us.lsi.graphs;

import java.util.Set;

/**
 * El tipo representa un v�rtice de un grafo virtual no dirigido
 * 
 * @author Miguel Toro
 *
 * @param <V> Tipo de los v�rtices
 * @param <E> Tipo de las aristas
 */
public interface VirtualVertex<V extends VirtualVertex<V,E>, E> {
	/**
	 * @return Conjunto de los v�rtices vecinos
	 */
	Set<V> getNeighborListOf();
	/**
	 * @return Conjunto de las arista hacia los v�rtices vecinos
	 */
	Set<E> edgesOf();
	/**
	 * @param e V�rtice que se pregunta si es vecino
	 * @return Si el v�rtice es vecino
	 */
	boolean isNeighbor(V e);
}
