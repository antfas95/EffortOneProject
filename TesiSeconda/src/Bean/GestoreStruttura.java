package Bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 * Questa classe mantiene l'insieme di tutti i progetti che sono presenti all'interno del file
 * di lettura selezionato, con la possibilità di svolgere le varie operazioni sui progetti.
 */
public class GestoreStruttura {

	//Dichiarazione delle variabili di istanza della classe
	private ArrayList<Progetto> progetti_presenti;
	private ArrayList<String> nomi_variabili;
	private ArrayList<Variabile> variabili_indicate;
	private ArrayList<Variabile> variabili_x_progetto;
	private ArrayList<Progetto> appoggio;

	//Inserimento delle variabile che permettono di elaborare e fornire le varie distanze
	private ArrayList<Float> distanze_progetto;
	private ArrayList<Float> distanze_ordinate;
	private ArrayList<Progetto> progetti_ritorno;

	//Costruttore della classe
	public GestoreStruttura() {
		progetti_presenti= new ArrayList<>();
		nomi_variabili= new ArrayList<>();
		variabili_indicate= new ArrayList<>();
		distanze_progetto= new ArrayList<>();
		progetti_ritorno= new ArrayList<>();
		appoggio= new ArrayList<>();
	}

	//Metodo che ritorna il nome delle variabili, cioè il suo insieme
	public ArrayList<String> nomi_variabili(){
		return nomi_variabili;
	}

	//Dichiarazione del metodo che permette di leggere qualsiasi File! Purchè esso sia un arttff
	public void carica_struttura() {
		String nome_file= JOptionPane.showInputDialog("Inserisci il nome del file", "progettiRif");
		System.out.println(nome_file);

		try {
			//Leggo il file che viene dato in input dall'utente
			FileReader reader= new FileReader(nome_file);
			//Inserisco il riferimento che consente l'operazione di lettura
			Scanner in= new Scanner(reader);

			//Inizio la lettura del file (sapendo che è un file formato: ARFF)
			while (in.hasNextLine()) {
				//Contino la lettura fin quando non incontro @data ogni nome rappresenterà il nome della variabile
				String valore= in.nextLine();
				System.out.println(valore);

				if(valore.equals("@data")) {
					//Finisco l'attività del while
					break;
				}else {
					if(valore.equals("")) {
						//nulla
					}else {
						int minimo= 11;
						valore= valore.substring(minimo, valore.length());
						String[] tokens= valore.split(" ");
						System.out.println("Da tokens: " + tokens[0]);
						nomi_variabili.add(tokens[0]);
					}
				}
			}

			//Da questo punto in questa variabile saranno presenti tutti gli attributi che possiede un determinato progetto
			nomi_variabili.remove(0);

			//Da questo punto in poi devo assegnare il valore per ogni variabile presente all'interno di nomi_variabili
			while(in.hasNextLine()) {
				variabili_x_progetto= new ArrayList<>();
				String variabile_studio= in.nextLine();
				System.out.println("Variabile di studio: " + variabile_studio);
				int minimo= 0;
				int massimo= 0;
				int contatore= 0;

				Progetto p= new Progetto();
				for (int i= 0; i<variabile_studio.length(); i++) {
					if (variabile_studio.charAt(i)==',') {
						int esempio= nomi_variabili.size()-1;
						String riferimento= variabile_studio.substring(minimo, massimo);
						float valore_variabile= Float.parseFloat(riferimento);
						Variabile nuova= new Variabile(nomi_variabili.get(contatore), valore_variabile);
						variabili_indicate.add(nuova);
						variabili_x_progetto.add(nuova);
						contatore++;
						if(contatore==nomi_variabili.size()-1) {
							//In questo caso devo leggere anche l'ultima variabile all'interno del file
							minimo= i+1;
							massimo= variabile_studio.length();
							String valore= variabile_studio.substring(minimo, massimo);
							float valore_finale= Float.parseFloat(valore);
							Variabile finale= new Variabile("Effort", valore_finale);
							variabili_indicate.add(finale);
							variabili_x_progetto.add(finale);
							System.out.println("Valore finale nome: " + finale.getNome() + " " + finale.getValore());
						}
						minimo= i+1;
						massimo= i+1;
					}else {
						massimo= i+1;
					}
				}

				p.setVariabili_progetto(variabili_x_progetto);
				progetti_presenti.add(p);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Progetto> getProgetti_presenti() {
		return progetti_presenti;
	}

	public void setProgetti_presenti(ArrayList<Progetto> progetti_presenti) {
		this.progetti_presenti = progetti_presenti;
	}

	public ArrayList<String> getNomi_variabili() {
		return nomi_variabili;
	}

	public void setNomi_variabili(ArrayList<String> nomi_variabili) {
		this.nomi_variabili = nomi_variabili;
	}

	public ArrayList<Variabile> getVariabili_indicate() {
		return variabili_indicate;
	}

	public void setVariabili_indicate(ArrayList<Variabile> variabili_indicate) {
		this.variabili_indicate = variabili_indicate;
	}

	public ArrayList<Variabile> ritornaProgetto_By_Id(int id) {
		return progetti_presenti.get(id).getVariabili_progetto();
	}


	//Metodo che permette di svolgere l'operazione della distanza euclidea semplice
	public ArrayList<Progetto> distanza_euclidea_semplice (ArrayList<Variabile> selezionate_utente, int k){

		progetti_ritorno= new ArrayList<>();

		System.out.println("Inizio la procedura");
		/*
		 * Prendo per ogni progetto le sue variabili di riferimento e salvo in un array ausiliario le variabili selezionate
		 * con quelle riscontrate in ogni progetto.
		 * 
		 * Tramite l'operazione di indice riesco a trovare i valore per ogni progetto e l'assegno alla variabile di istanza 
		 * distance che è presente all'interno del file
		 */

		for (Progetto p: progetti_presenti) {
			float distanza= 0;
			ArrayList<Variabile> vars_progetto= new ArrayList<>();
			vars_progetto= p.getVariabili_progetto();
			ArrayList<Variabile> iterate= new ArrayList<>();
			for (Variabile vara: vars_progetto) {
				for (Variabile sel: selezionate_utente) {
					if (sel.getNome().equals(vara.getNome())) {
						iterate.add(vara);
					}
				}
			}

			int index_fine= selezionate_utente.size();
			for (int i=0; i<index_fine; i++) {
				distanza+= (float) Math.pow(iterate.get(i).getValore()-selezionate_utente.get(i).getValore(), 2);
			}

			distanza= (float) Math.sqrt(distanza);
			p.setDistance(distanza);
			System.out.println(progetti_presenti.indexOf(p) + " Valore Distanza associata: " + distanza);
		}

		//Ritorno l'insieme delle distanze che si sono calcolate e inizio l'ordinamento dell'array
		distanze_progetto= ritorna_distanze();

		Collections.sort(distanze_progetto);

		for (Float f: distanze_progetto) {
			System.out.println("Ordinata: " + f);
		}
		//Devo prendere solo i primi k elementi dell'arrayOrdinato
		ArrayList<Float> solo_k= new ArrayList<>();

		for (int i= 0; i<k; i++) {
			solo_k.add(distanze_progetto.get(i));
			System.out.println("Solo k: " + distanze_progetto.get(i));
		}

		appoggio= progetti_presenti;

		//Adesso posso ritornare i progetti che trovano riscontro con la distanza
		for (int f=0; f<solo_k.size(); f++) {
			float valore_confronto= solo_k.get(f);
			System.out.println("Esamino i valori float presenti nell'array solo k");
			for (Progetto p: appoggio) {
				float distance= p.getDistance();

				if (distance==valore_confronto) {
					System.out.println("Aggiungo il progetto agli elementi di ritorno");
					progetti_ritorno.add(p);
					appoggio.remove(p);
					break;
				}
			}
		}

		appoggio= progetti_presenti;

		for (Progetto pi: progetti_ritorno) {
			ArrayList<Variabile> esempio= new ArrayList<>();
			esempio= pi.getVariabili_progetto();
			System.out.println(esempio + "Distanza: " + pi.getDistance());
		}

		return progetti_ritorno;

	}

	public ArrayList<Progetto> distanza_euclidea_ponderata(ArrayList<Variabile> selezionate_utente, ArrayList<Integer> pesi, int k){

		/*
		 * Prendo per ogni progetto le sue variabili di riferimento e salvo in un array ausiliario le variabili selezionate
		 * con quelle riscontrate in ogni progetto.
		 * 
		 * Tramite l'operazione di indice riesco a trovare i valore per ogni progetto e l'assegno alla variabile di istanza 
		 * distance che è presente all'interno del file
		 */
		progetti_ritorno= new ArrayList<>();

		for (Progetto p: progetti_presenti) {
			float distanza= 0;
			ArrayList<Variabile> vars_progetto= new ArrayList<>();
			vars_progetto= p.getVariabili_progetto();
			ArrayList<Variabile> iterate= new ArrayList<>();
			for (Variabile vara: vars_progetto) {
				for (Variabile sel: selezionate_utente) {
					if (sel.getNome().equals(vara.getNome())) {
						iterate.add(vara);
					}
				}
			}

			int index_fine= selezionate_utente.size();
			for (int i=0; i<index_fine; i++) {
				distanza+= (float) pesi.get(i)*(Math.pow(iterate.get(i).getValore()-selezionate_utente.get(i).getValore(), 2));
			}

			distanza= (float) Math.sqrt(distanza);
			p.setDistance(distanza);
			System.out.println(progetti_presenti.indexOf(p) + " Valore Distanza associata: " + distanza);
		}

		//Ritorno l'insieme delle distanze che si sono calcolate e inizio l'ordinamento dell'array
		distanze_progetto= ritorna_distanze();

		Collections.sort(distanze_progetto);

		int contatoree=1;
		for (Float f: distanze_progetto) {
			System.out.println(contatoree + "Ordinata: " + f);
		}
		//Devo prendere solo i primi k elementi dell'arrayOrdinato
		ArrayList<Float> solo_k= new ArrayList<>();

		for (int i= 0; i<k; i++) {
			solo_k.add(distanze_progetto.get(i));
			System.out.println("Solo k: " + distanze_progetto.get(i));
		}

		appoggio= progetti_presenti;

		//Adesso posso ritornare i progetti che trovano riscontro con la distanza
		for (int f=0; f<solo_k.size(); f++) {
			float valore_confronto= solo_k.get(f);
			System.out.println("Esamino i valori float presenti nell'array solo k");
			for (Progetto p: appoggio) {
				float distance= p.getDistance();

				if (distance==valore_confronto) {
					System.out.println("Aggiungo il progetto agli elementi di ritorno");
					progetti_ritorno.add(p);
					appoggio.remove(p);
					break;
				}
			}
		}

		for (Progetto pi: progetti_ritorno) {
			ArrayList<Variabile> esempio= new ArrayList<>();
			esempio= pi.getVariabili_progetto();
			System.out.println(esempio + "Distanza: " + pi.getDistance());
		}

		return progetti_ritorno;
	}

	//Dichiarazione del metodo che permette di effettuare il controllo della distanza di Manhattan
	public ArrayList<Progetto> distanza_Manhattan(ArrayList<Variabile> selezionate_utente, int k){

		progetti_ritorno= new ArrayList<>();

		for (Progetto p: progetti_presenti) {
			float distanza= 0;
			ArrayList<Variabile> vars_progetto= new ArrayList<>();
			vars_progetto= p.getVariabili_progetto();
			ArrayList<Variabile> iterate= new ArrayList<>();
			for (Variabile vara: vars_progetto) {
				for (Variabile sel: selezionate_utente) {
					if (sel.getNome().equals(vara.getNome())) {
						iterate.add(vara);
					}
				}
			}

			int index_fine= selezionate_utente.size();
			for (int i=0; i<index_fine; i++) {
				distanza+= Math.abs((float) iterate.get(i).getValore()-selezionate_utente.get(i).getValore());
			}

			p.setDistance(distanza);
			System.out.println(progetti_presenti.indexOf(p) + " Valore Distanza associata: " + distanza);
		}

		//Ritorno l'insieme delle distanze che si sono calcolate e inizio l'ordinamento dell'array
		distanze_progetto= ritorna_distanze();

		Collections.sort(distanze_progetto);

		int contatoree=1;
		for (Float f: distanze_progetto) {
			System.out.println(contatoree + "Ordinata: " + f);
		}
		//Devo prendere solo i primi k elementi dell'arrayOrdinato
		ArrayList<Float> solo_k= new ArrayList<>();

		for (int i= 0; i<k; i++) {
			solo_k.add(distanze_progetto.get(i));
			System.out.println("Solo k: " + distanze_progetto.get(i));
		}

		appoggio= progetti_presenti;

		//Adesso posso ritornare i progetti che trovano riscontro con la distanza
		for (int f=0; f<solo_k.size(); f++) {
			float valore_confronto= solo_k.get(f);
			System.out.println("Esamino i valori float presenti nell'array solo k");
			for (Progetto p: appoggio) {
				float distance= p.getDistance();

				if (distance==valore_confronto) {
					System.out.println("Aggiungo il progetto agli elementi di ritorno");
					progetti_ritorno.add(p);
					appoggio.remove(p);
					break;
				}
			}
		}

		for (Progetto pi: progetti_ritorno) {
			ArrayList<Variabile> esempio= new ArrayList<>();
			esempio= pi.getVariabili_progetto();
			System.out.println(esempio + "Distanza: " + pi.getDistance());
		}

		return progetti_ritorno;
	}

	//Metodo che permette di ritornare i progetti ai quali è stata sottoposta la distanza di Minkowski
	public ArrayList<Progetto> calcola_Minkowski(ArrayList<Variabile> selezionate_utente, int k, int p_mink){
		
		progetti_ritorno= new ArrayList<>();

		for (Progetto p: progetti_presenti) {
			float distanza= 0;
			ArrayList<Variabile> vars_progetto= new ArrayList<>();
			vars_progetto= p.getVariabili_progetto();
			ArrayList<Variabile> iterate= new ArrayList<>();
			for (Variabile vara: vars_progetto) {
				for (Variabile sel: selezionate_utente) {
					if (sel.getNome().equals(vara.getNome())) {
						iterate.add(vara);
					}
				}
			}

			int index_fine= selezionate_utente.size();
			for (int i=0; i<index_fine; i++) {
				distanza+= Math.pow(Math.abs(iterate.get(i).getValore()-selezionate_utente.get(i).getValore()), p_mink);
			}
			
			distanza= (float) Math.pow(distanza, (double) 1/p_mink);
			p.setDistance(distanza);
			System.out.println(progetti_presenti.indexOf(p) + " Valore Distanza associata: " + distanza);
		}

		//Ritorno l'insieme delle distanze che si sono calcolate e inizio l'ordinamento dell'array
		distanze_progetto= ritorna_distanze();

		Collections.sort(distanze_progetto);

		int contatoree=1;
		for (Float f: distanze_progetto) {
			System.out.println(contatoree + "Ordinata: " + f);
		}
		//Devo prendere solo i primi k elementi dell'arrayOrdinato
		ArrayList<Float> solo_k= new ArrayList<>();

		for (int i= 0; i<k; i++) {
			solo_k.add(distanze_progetto.get(i));
			System.out.println("Solo k: " + distanze_progetto.get(i));
		}

		appoggio= progetti_presenti;

		//Adesso posso ritornare i progetti che trovano riscontro con la distanza
		for (int f=0; f<solo_k.size(); f++) {
			float valore_confronto= solo_k.get(f);
			System.out.println("Esamino i valori float presenti nell'array solo k");
			for (Progetto p: appoggio) {
				float distance= p.getDistance();

				if (distance==valore_confronto) {
					System.out.println("Aggiungo il progetto agli elementi di ritorno");
					progetti_ritorno.add(p);
					appoggio.remove(p);
					break;
				}
			}
		}

		for (Progetto pi: progetti_ritorno) {
			ArrayList<Variabile> esempio= new ArrayList<>();
			esempio= pi.getVariabili_progetto();
			System.out.println(esempio + "Distanza: " + pi.getDistance());
		}

		return progetti_ritorno;
	}

	//Metodo che mi ritorna l'array di tutte le distanze presenti nel progetto
	public ArrayList<Float> ritorna_distanze(){
		ArrayList<Float> inter_dist= new ArrayList<>();
		for (Progetto p: progetti_presenti) {
			float valore= p.getDistance();
			inter_dist.add(valore);
		}
		return inter_dist;
	}

	//Metodo che permette di ritornare la media di un set di progetti passati come parametro
	public float calcola_media(ArrayList<Progetto> progetti) {
		float media= 0;
		ArrayList<Variabile> vars;
		for (Progetto p: progetti) {
			vars= new ArrayList<>();
			vars= p.getVariabili_progetto();
			for (Variabile v: vars) {
				if (v.getNome().equals("Effort")) {
					media+= v.getValore();
				}
			}
		}
		return media/progetti.size();
	}

	//Metodo che permette di ritornare la mediana di un set di progetti passati come paramentro
	public float calcola_Mediana (ArrayList<Progetto> progetti) {

		ArrayList<Float> valori_effort= new ArrayList<>();
		ArrayList<Variabile> vars1= new ArrayList<>();

		for (Progetto p: progetti) {
			vars1= p.getVariabili_progetto();
			for (Variabile v: vars1) {
				if (v.getNome().equals("Effort")) {
					valori_effort.add(v.getValore());
				}
			}
		}

		Collections.sort(valori_effort);

		for (Float f: valori_effort) {
			System.out.println("Flaot dell'effort calcolato: " + f);
		}

		float mediana= 0;
		if (progetti.size()%2==0) {
			//Verifico se il risultato è pari in questo caso la media dei progetti centrali
			float variabile1= valori_effort.get(valori_effort.size()/2-1);
			float vaiabile2= valori_effort.get(valori_effort.size()/2);

			mediana= vaiabile2+variabile1;

			return mediana/2;
		}else {
			System.out.println("Il caso è dispari");
			mediana= valori_effort.get((int) (valori_effort.size()/2+0.5));
			return mediana;
		}
	}
	
	/*
	 * Metodo che decreta l'attività dell'Inverse Rank Mean, rappresenta un calcolo dell'effort dove viene preso il progetto che 
	 * possiede un analogia più vicina al progetto selezionato e viene dato peso k (numero delle analogie) al successivo
	 * peso k-1, fino add arrivare all'ultimo che avrà peso 1.
	 */
	public float calcola_Inverse_Rank_Mean(ArrayList<Progetto> progetti) {
		
		//Variabile di ritorno del metodo
		float i_r_m= 0;
		
		/*
		 * I progetti che sono passati come argomento a questo metodo già risultano essere in ordine secondo la loro analogia
		 * per questo motivo basta applicare direttamente il peso per ogni progetto sull'effort del progetto stesso
		 */
		
		int pesi_progetto= progetti.size();
		int divisore= 0;
		
		for (Progetto p: progetti) {
			ArrayList<Variabile> vars= new ArrayList<>();
			vars= p.getVariabili_progetto();
			for (Variabile v: vars) {
				if (v.getNome().equals("Effort")) {
					float valore= v.getValore()*pesi_progetto;
					divisore+=pesi_progetto;
					pesi_progetto--;
					i_r_m+=valore;
				}
			}
		}
		
		return i_r_m/divisore;
	}
}