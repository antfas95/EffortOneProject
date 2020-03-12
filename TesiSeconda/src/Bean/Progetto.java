package Bean;

import java.util.ArrayList;

public class Progetto {
	
	//Dichiarazione delle variabili di istanza della classe
	private ArrayList<Variabile> variabili_progetto;
	private float distanza;
	
	//Costruttore della classe
	public Progetto () {}
	public Progetto (ArrayList<Variabile> variabili) {
		this.variabili_progetto= variabili;
		distanza= 0;
	}
	
	//Metodo che permette di ritornare una il valore di una determinata variabile
	public Variabile ritorna_Variabile(int i) {
		return variabili_progetto.get(i);
	}
	
	//Metodo che permette di ritornare la variabile finale, sempre chamata variabile_finale
	public Variabile ritorna_variabile_Finale() {
		Variabile ultima = null;
		for (Variabile x: variabili_progetto) {
			if (x.getNome().equals("variabile_finale")) {
				ultima= x;
			}
		}
		
		if(ultima==null) {
			return ultima;
		}else {
			return null;
		}
	}
	
	//Metodo che permette di ritornare la variabile finale di un progetto (utile per il controllo dell'effort)
	public Variabile get_Progetto_Finale() {
		Variabile finale_var= variabili_progetto.get(variabili_progetto.size());
		return finale_var;
	}
	
	//Assegnazione a un progetto di una variabile
	public void set_Unica_Variabile(Variabile v) {
		variabili_progetto.add(v);
	}
	
	//Metodo che permette di ritornare il valore della distanza
	public float getDistance() {
		return distanza;
	}
	
	//Metodo che permette di settare la variabile di istanza 
	public void setDistance(float d) {
		distanza= d;
	}
	
	//Generazione automatica dei metodi gettere e setter
	public ArrayList<Variabile> getVariabili_progetto() {
		return variabili_progetto;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(distanza);
		result = prime * result + ((variabili_progetto == null) ? 0 : variabili_progetto.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Progetto other = (Progetto) obj;
		if (Float.floatToIntBits(distanza) != Float.floatToIntBits(other.distanza))
			return false;
		if (variabili_progetto == null) {
			if (other.variabili_progetto != null)
				return false;
		} else if (!variabili_progetto.equals(other.variabili_progetto))
			return false;
		return true;
	}
	public void setVariabili_progetto(ArrayList<Variabile> variabili_progetto) {
		this.variabili_progetto = variabili_progetto;
	}
	@Override
	public String toString() {
		return "Progetto [variabili_progetto=" + variabili_progetto + "]";
	}
	
	public String miaToString() {
		String s = null;
		for (Variabile v: variabili_progetto) {
			s+= "Nome: " + v.getNome() + "Valore: " + v.getValore();
		}
		return s;
	}
}