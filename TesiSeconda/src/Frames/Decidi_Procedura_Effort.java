package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Bean.GestoreStruttura;
import Bean.Progetto;
import Bean.Variabile;

public class Decidi_Procedura_Effort extends JFrame {
	
	//Dichiarazione delle variabili di istanza della classe
	private ArrayList<Variabile> selezionate;
	private int k;
	private ArrayList<Progetto> progetti;
	private GestoreStruttura gestore;
	private JPanel central_panel;
	private JButton media;
	private JButton mediana;
	private JButton inverse_Rank_Mean;
	private JButton indietro;
	
	public Decidi_Procedura_Effort(ArrayList<Progetto> p, GestoreStruttura g, ArrayList<Variabile> selezionate, int k) {
		this.setTitle("Esecuzione Euclide");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		
		this.k= k;
		this.selezionate= selezionate;
		this.progetti= p;
		this.gestore= g;
		
		JLabel label= new JLabel("Inserisci la procedura con la quale vuoi ritornare l'effort");
		label.setForeground(Color.RED);
		
		add(label, BorderLayout.NORTH);
		
		createCentralPanel();
		
		this.setVisible(true);
	}

	private void createCentralPanel() {
		// TODO Auto-generated method stub
		central_panel= new JPanel();
		media= new JButton ("Media");
		mediana= new JButton("Mediana");
		inverse_Rank_Mean= new JButton ("Inverse Rank Mean");
		indietro= new JButton("Annulla Operazione");
		
		class Implementazione_Media implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				float ritorno= gestore.calcola_media(progetti);
				if (ritorno==0) {
					System.out.println("Errore nel calcolo della media");
				}else {
					JOptionPane.showMessageDialog(null, ritorno);
				}
			}
		}
		ActionListener listener= new Implementazione_Media();
		media.addActionListener(listener);
		
		class Implementazione_mediana implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				float ritorno= gestore.calcola_Mediana(progetti);
				if (ritorno==0) {
					System.out.println("Qualcosa è andato storto all'interno della procedura");
				}else {
					JOptionPane.showMessageDialog(null, ritorno);
				}
			}
		}
		ActionListener listener2= new Implementazione_mediana();
		mediana.addActionListener(listener2);
		
		class Torna_Indietro implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				progetti= new ArrayList<>();
				JFrame frame= new MenuFrame(gestore, selezionate);
				dispose();
			}
		}
		ActionListener listener3= new Torna_Indietro();
		indietro.addActionListener(listener3);
		
		class Operazione_Inverse_Rank implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				float ritorno= gestore.calcola_Inverse_Rank_Mean(progetti);
				
				if (ritorno==0) {
					System.out.println("Qualcosa è andato storto all'interno della procedura");
				}else {
					JOptionPane.showMessageDialog(null, ritorno);
				}
			}
		}
		ActionListener listener4= new Operazione_Inverse_Rank();
		inverse_Rank_Mean.addActionListener(listener4);
		
		central_panel.add(media);
		central_panel.add(mediana);
		central_panel.add(inverse_Rank_Mean);
		central_panel.add(indietro);
		add (central_panel, BorderLayout.CENTER);
	}
}