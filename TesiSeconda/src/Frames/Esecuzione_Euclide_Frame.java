package Frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.InsetsUIResource;

import Bean.GestoreStruttura;
import Bean.Progetto;
import Bean.Variabile;

public class Esecuzione_Euclide_Frame extends JFrame {
	
	//Dichiarazione delle variabili di istanza della classe
	private GestoreStruttura gestore;
	private ArrayList<Variabile> variabili_selezionate;
	private ArrayList<Progetto> progetti_ritornati;
	
	private int k;
	private boolean check= false;
	private JPanel est_panel;
	private JPanel central_panel;
	private JButton elabora;
	private JButton mostra_effort;
	private JButton indietro;
	private JTextArea area;
	private JScrollPane scroll;
	
	public Esecuzione_Euclide_Frame(GestoreStruttura gestore, ArrayList<Variabile> selezionate, int k) {
		this.setTitle("Esecuzione Euclide");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		
		this.gestore= gestore;
		this.variabili_selezionate= selezionate;
		this.progetti_ritornati= new ArrayList<>();
		this.k= k;
		
		createEstPanel();
		
		area= new JTextArea();
		scroll= new JScrollPane(area);
		
		add(area, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	private void createCentralPanel() {
		// TODO Auto-generated method stub
		central_panel= new JPanel();
		
		for (Progetto p: progetti_ritornati) {
			System.out.println(p.miaToString());
		}
		
		if (check==true) {
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

	private void createEstPanel() {
		// TODO Auto-generated method stub
		est_panel= new JPanel();
		elabora= new JButton("Elabora");
		mostra_effort= new JButton("Effort");
		indietro= new JButton("Indietro");
		
		class Elabora_Euclide implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				check= true;
				progetti_ritornati= gestore.distanza_euclidea_semplice(variabili_selezionate, k);
				createCentralPanel();
			}
		}
		ActionListener listener= new Elabora_Euclide();
		elabora.addActionListener(listener);
		
		class Show_Effort implements ActionListener{

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
		ActionListener listener2= new Show_Effort();
		mostra_effort.addActionListener(listener2);
		
		class Vado_Indietro implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame= new MenuFrame (gestore, variabili_selezionate);
				dispose();
			}
		}
		ActionListener listener3= new Vado_Indietro();
		indietro.addActionListener(listener3);
		
		est_panel.add(elabora);
		est_panel.add(mostra_effort);
		est_panel.add(indietro);
		add(est_panel, BorderLayout.SOUTH);
	}
}