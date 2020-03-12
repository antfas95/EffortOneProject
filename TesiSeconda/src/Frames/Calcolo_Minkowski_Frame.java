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

public class Calcolo_Minkowski_Frame extends JFrame {
	
	//Dichiarazione delle variabili di istanza della classe
		private GestoreStruttura gestore;
		private ArrayList<Variabile> variabili_selezionate;
		private int k;
		private ArrayList<Progetto> progetti_ritornati;
		
		private JPanel sud_panel;
		private JPanel central_panel;
		private JButton elbora;
		private JButton effort;
		private JButton indietro;
		private JTextArea area;
		private JScrollPane scroll;
		private boolean check= false;
		
		public Calcolo_Minkowski_Frame(GestoreStruttura gestore, ArrayList<Variabile> variabili, int intero) {
			
			this.setTitle("Esecuzione Euclide");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setSize(500, 500);
			
			this.gestore= gestore;
			this.variabili_selezionate= variabili;
			this.k= intero;
			this.progetti_ritornati= new ArrayList<>();
			
			createSudPanel();
			
			area= new JTextArea();
			scroll= new JScrollPane(area);
			
			add(area, BorderLayout.CENTER);
			
			this.setVisible(true);
		}

		private void createSudPanel() {
			// TODO Auto-generated method stub
			sud_panel= new JPanel();
			
			elbora= new JButton("Elabora");
			effort= new JButton("Effort");
			indietro= new JButton("Indietro");
			
			class Operazione_Elabora implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//In questa parte del codice bisogna richiamare il riferimento a Gestore Struttura e invocare il metodo d'interesse
					check= true;
					String valore= JOptionPane.showInputDialog("Digita il tuo 'p' generalmente compreso tra 1 e 2: ");
					int intero= Integer.parseInt(valore);
					progetti_ritornati= gestore.calcola_Minkowski(variabili_selezionate, k, intero);
					createCentralPanel();
				}
			}
			ActionListener listener= new Operazione_Elabora();
			elbora.addActionListener(listener);
			
			class Definizione_Effort implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (check==true) {
						JFrame decisioneEffort= new Decidi_Procedura_Effort(progetti_ritornati, gestore, variabili_selezionate, k);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Non puoi mostrare l'effort se non hai prima elaborato il risultato");
					}
				}
			}
			ActionListener listener2= new Definizione_Effort();
			effort.addActionListener(listener2);
			
			class Operazione_Indietro implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JFrame frame= new MenuFrame (gestore, variabili_selezionate);
					dispose();
				}
			}
			ActionListener listener3= new Operazione_Indietro();
			indietro.addActionListener(listener3);
			
			sud_panel.add(elbora);
			sud_panel.add(effort);
			sud_panel.add(indietro);
			add(sud_panel, BorderLayout.SOUTH);
		}
		
		public void createCentralPanel() {
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