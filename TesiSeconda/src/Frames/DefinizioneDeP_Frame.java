package Frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Bean.GestoreStruttura;
import Bean.Progetto;
import Bean.Variabile;

public class DefinizioneDeP_Frame extends JFrame {
	
	//Dichiarazione delle vaiabili di istanza della classe
	private GestoreStruttura gestore;
	private int k;
	private ArrayList<Integer> pesi;
	private ArrayList<Variabile> variabili_selezionate;
	private ArrayList<Progetto> progetti_ritornati;
	private ArrayList<Progetto> appoggio;
	
	//Per l'interfaccia
	private JPanel central_panel;
	private JPanel sud_panel;
	private JButton elabora_button;
	private JButton indietro;
	private JButton effort;
	private JTextArea area;
	private JScrollPane scroll;
	
	//Variabile per controllo (Clicco di Elabora)
	private boolean check= false;
	
	public DefinizioneDeP_Frame(GestoreStruttura gestore, ArrayList<Variabile> selezionate, int k, ArrayList<Integer> inter) {
		this.setTitle("Esecuzione Euclide Ponderata");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		
		this.gestore= gestore;
		this.variabili_selezionate= selezionate;
		this.k= k;
		this.pesi= inter;
		progetti_ritornati= new ArrayList<>();
		appoggio= new ArrayList<>();
		
		craeteSudPanel();
		
		area= new JTextArea();
		scroll= new JScrollPane(area);
		
		add(area, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	private void craeteSudPanel() {
		// TODO Auto-generated method stub
		sud_panel= new JPanel();
		elabora_button= new JButton("Elabora");
		indietro= new JButton("Indietro");
		effort= new JButton("Effort");
		
		class Elabora_Distanza implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/*Richiamo il metodo da gestore che mi consente di effettuare l'operazione gli devo passare come parametri:
				 * ArrayList degli inter che rappresentano i pesi dei progetti;
				 * ArrayList delle variabili selezionate dall'utente
				 * Passare anche k per sapere quanti devono essere i progetti di ritorno
				 * Ritornarmi un ArrayList di progetti.
				 */
				check= true;
				progetti_ritornati= gestore.distanza_euclidea_ponderata(variabili_selezionate, pesi, k);
				createCentralPanel();
			}
		}
		ActionListener listener= new Elabora_Distanza();
		elabora_button.addActionListener(listener);
		
		class Operazione_Indietro implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame= new MenuFrame(gestore, variabili_selezionate);
				dispose();
			}
		}
		ActionListener listener2= new Operazione_Indietro();
		indietro.addActionListener(listener2);
		
		class Operazione_Effort implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(check==false) {
					JOptionPane.showMessageDialog(null, "Prima di trovare il tuo effort elabora i dati");
				}else {
					JFrame frame= new Decidi_Procedura_Effort(progetti_ritornati, gestore, variabili_selezionate, k);
					dispose();
				}
			}
		}
		ActionListener listener3= new Operazione_Effort();
		effort.addActionListener(listener3);
		
		sud_panel.add(elabora_button);
		sud_panel.add(effort);
		sud_panel.add(indietro);
		add(sud_panel, BorderLayout.SOUTH);
	}

	private void createCentralPanel() {
		// TODO Auto-generated method stub
		central_panel= new JPanel();
		if (check= true) {
			for (Progetto p: progetti_ritornati) {
				ArrayList<Variabile> vars= p.getVariabili_progetto();
				for (Variabile v: vars) {
					if (v.getNome().equals("Effort")) {
						System.out.println("Effort valore: " + v.getValore());
						area.append("Effort valore: " + v.getValore());
						area.append("\n");
					}
				}
			}
			central_panel.add(area);
			add (central_panel, BorderLayout.CENTER);
		}
		add(central_panel, BorderLayout.CENTER);
	}
}