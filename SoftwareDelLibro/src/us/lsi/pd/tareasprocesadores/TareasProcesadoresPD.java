package us.lsi.pd.tareasprocesadores;

import java.util.List;
import java.util.Map;
import java.util.Comparator;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TareasProcesadoresPD implements ProblemaPD<Map<Integer,List<Tarea>>, Integer> {

	public static Integer numeroDeProcesadores;
	public static Integer numeroDeTareas;
	public static TareasProcesadoresPD inicial;
	
	public static TareasProcesadoresPD create(String fichero, Integer np) {
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		CargaDeProcesadores cargaProcesadoresAcumulada = CargaDeProcesadores.create(np);
		inicial = new TareasProcesadoresPD(0, cargaProcesadoresAcumulada);
		return inicial;
	}
	
	public static TareasProcesadoresPD create(int index, CargaDeProcesadores cargaProcesadoresAcumulada) {
		return new TareasProcesadoresPD(index, cargaProcesadoresAcumulada);
	}
	
	private int index;
	private CargaDeProcesadores cargaProcesadoresAcumulada;
	private Double tiempoMaximo; //el del procesador m�s cargado
	
	private TareasProcesadoresPD(String fichero, Integer np){
		Tarea.leeTareas(fichero);
		numeroDeProcesadores = np;
		numeroDeTareas = Tarea.tareas.size();
		this.index = 0;
		this.cargaProcesadoresAcumulada = CargaDeProcesadores.create(np);
		this.tiempoMaximo = this.cargaProcesadoresAcumulada.getTiempoDelMasCargado();
	}

	private TareasProcesadoresPD(int index, CargaDeProcesadores cargaProcesadoresAcumulada) {
		super();
		this.index = index;
		this.cargaProcesadoresAcumulada = cargaProcesadoresAcumulada;
		this.tiempoMaximo = this.cargaProcesadoresAcumulada.getTiempoDelMasCargado();
	}

	@Override
	public ProblemaPD.Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return numeroDeTareas - index;
	}

	@Override
	public boolean esCasoBase() {
		return index == numeroDeTareas;
	}

	@Override
	public Sp<Integer> getSolucionCasoBase() {	
		Sp<Integer> r = Sp.create(0,this.tiempoMaximo);
		return r;
	}

	@Override 
	public Sp<Integer> seleccionaAlternativa(List<Sp<Integer>> ls) {
		Sp<Integer> r = ls.stream().min(Comparator.naturalOrder()).get();
		return r;
	}
	
	@Override
	public ProblemaPD<Map<Integer, List<Tarea>>, Integer> getSubProblema(Integer a, int np) {
		CargaDeProcesadores carga = this.cargaProcesadoresAcumulada.addTareaAProcesador(index, a);
		TareasProcesadoresPD p = TareasProcesadoresPD.create(index+1, carga);
		return p;
	}

	@Override
	public Sp<Integer> combinaSolucionesParciales(Integer a, List<Sp<Integer>> ls) {
		Sp<Integer> s = Sp.create(a, ls.get(0).propiedad);
		return s;
	}

	@Override
	public List<Integer> getAlternativas() {
		return IntStream.range(0,numeroDeProcesadores).boxed().collect(Collectors.toList());
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {		
		return 1;
	}

	@Override
	public Map<Integer,List<Tarea>> getSolucionReconstruida(Sp<Integer> sp) {
		return Maps.newHashMap();
	}

	@Override
	public Map<Integer, List<Tarea>> getSolucionReconstruida(Sp<Integer> sp, List<Map<Integer, List<Tarea>>> ls) {
		Map<Integer,List<Tarea>>  m = Maps.newHashMap(ls.get(0));
		List<Tarea>  ts;
		if(m.containsKey(sp.alternativa)){
			ts = m.get(sp.alternativa);
		} else {
			ts = Lists.newArrayList();
			m.put(sp.alternativa, ts);
		}
		ts.add(Tarea.tareas.get(index));
		return m;
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
	    CargaDeProcesadores carga = this.cargaProcesadoresAcumulada;
	    carga = carga.addTareaAProcesador(index, a);
		return carga.getTiempoDelMasCargado();
	}

	@Override
	public Double getObjetivo() {
		return this.tiempoMaximo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cargaProcesadoresAcumulada == null) ? 0
						: cargaProcesadoresAcumulada.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TareasProcesadoresPD))
			return false;
		TareasProcesadoresPD other = (TareasProcesadoresPD) obj;
		if (cargaProcesadoresAcumulada == null) {
			if (other.cargaProcesadoresAcumulada != null)
				return false;
		} else if (!cargaProcesadoresAcumulada
				.equals(other.cargaProcesadoresAcumulada))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + index
				+ "," + cargaProcesadoresAcumulada
				+ ")";
	}
}
