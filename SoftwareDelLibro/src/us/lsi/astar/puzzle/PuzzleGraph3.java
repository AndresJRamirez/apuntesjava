package us.lsi.astar.puzzle;

import java.util.Set;
import java.util.function.Function;

import org.jgrapht.EdgeFactory;

import us.lsi.graphs.SimpleEdge;

public class PuzzleGraph3 extends PuzzleGraph1 {

	public static PuzzleGraph3 create(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		return new PuzzleGraph3(edgeFactory);
	}

	private PuzzleGraph3(
			EdgeFactory<EstadoPuzzle, SimpleEdge<EstadoPuzzle>> edgeFactory) {
		super(edgeFactory);
	}

	@Override
	public double getWeightToEnd(EstadoPuzzle startVertex,EstadoPuzzle endVertex, Function<EstadoPuzzle,Double> goal,
			Set<EstadoPuzzle> goalSet) {
		if(startVertex==null || goalSet==null)
			throw new IllegalArgumentException("El v�rtice inicial y el goalSet no pueden ser null");
		
		return goalSet.stream().mapToDouble(e->startVertex.getNumDiferentes(e)).min().getAsDouble();
	}
}
