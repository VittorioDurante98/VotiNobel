package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	private List<Esame> esami;
	private double bestMedia= 0.0;
	private Set<Esame> bestSoluzione = null;
	
	public Model() {
		EsameDAO edao = new EsameDAO();
		this.esami = edao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale = new HashSet<>();
		
		cerca1(parziale, 0, numeroCrediti);
		
	
		return bestSoluzione;
	}

	//APPROCCIO 1
	private void cerca1(Set<Esame> parziale, int l, int m) {
		//casi terminali
		int crediti= sommaCrediti(parziale);
		if(crediti> m)
			return;
		
		if(crediti==m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				bestSoluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}
		//qui, crediti < m
		if(l==esami.size())
			return;
		
		
		//provo ad aggiungere
		parziale.add(esami.get(l));
		cerca1(parziale, l+1, m);
		parziale.remove(esami.get(l));
		
		//provo a non aggiungerlo
		cerca1(parziale, l+1, m);
		
	}
	
	//APPROCCIO 2
	//Complessià 2 alla n
	private void cerca2(Set<Esame> parziale, int l, int m) {
		//casi terminali
		int crediti= sommaCrediti(parziale);
		if(crediti> m)
			return;
				
		if(crediti==m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				bestSoluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}
		//qui, crediti < m
		if(l==esami.size())
			return;
				
		for(Esame e: esami) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale, l+1, m);
				parziale.remove(e);
			}
		}
	}

	public double calcolaMedia(Set<Esame> parziale) {
		int crediti=0;
		int somma=0;
		
		for(Esame e: parziale) {
			crediti+= e.getCrediti();
			somma +=(e.getVoto()*e.getCrediti());
		}
		return somma/crediti;
	}

	private int sommaCrediti(Set<Esame> parziale) {
		int somma =0;
		for(Esame e: parziale)
			somma+= e.getCrediti();
		
		return somma;
	}
	
}
