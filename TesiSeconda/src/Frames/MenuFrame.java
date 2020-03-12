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
import javax.swing.JTextField;

import Bean.GestoreStruttura;
import Bean.Variabile;

public class MenuFrame extends JFrame {
	//Dichiarazione delle variabili di istanza della classe
	private GestoreStruttura gestore;
	private ArrayList<Variabile> variabili_selezionate;
	private JTextField field;
	private JButton distanza_euclidea;
	private JButton distanza_euclidea_ponderata;
	private JButton indietro;
	private JButton distanza_manhattan;
	private JButton distanza_minkowski;
	private JPanel panel_center;
	private JPanel panel_sud;
	
	public MenuFrame (GestoreStruttura ges, ArrayList<Variabile> selezionate) {
		this.setTitle("Menu Frame");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		
		this.gestore= ges;
		this.variabili_selezionate= selezionate;
		JLabel label= new JLabel("Sceglio tipo di distanza e seleziona k (numero riscontri di ritorno)");
		label.setForeground(Color.RED);
		
		createCentralPanel();
		createSudPanel();
		
		add(label, BorderLayout.NORTH);
		
		this.setVisible(true);
	}

	private void createSudPanel() {
		// TODO Auto-generated method stub
		panel_sud= new JPanel();
		JLabel label1= new JLabel("Inserisci k: ");
		field= new JTextField(5);
		field.setText("0");
		panel_sud.add(label1);
		panel_sud.add(field);
		add(panel_sud, BorderLayout.SOUTH);
	}

	private void createCentralPanel() {
		// TODO Auto-generated method stub
		panel_center= new JPanel();
		distanza_euclidea= new JButton("Similarity Measure");
		distanza_euclidea_ponderata= new JButton("Distanza euclidea ponderata");
		distanza_manhattan= new JButton("Distanza Manhattan");
		distanza_minkowski= new JButton("Distanza Minkowski");
		indietro= new JButton("Indietro");
		
		class Attivazione_DE implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int valore= Integer.parseInt(field.getText());
				if(valore!=0) {
					JFrame frame= new Esecuzione_Euclide_Frame(gestore, variabili_selezionate, valore);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Inserire il k campo obbligatorio, per ritorno di analogie");
				}
			}
		}
		ActionListener listener= new Attivazione_DE();
		distanza_euclidea.addActionListener(listener);
		
		class Attivazione_DeP implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int valore= Integer.parseInt(field.getText());
				if(valore!=0) {
					JFrame frame= new Esecuzione_EuclidePond_Frame(gestore, variabili_selezionate, valore);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Inserire il k campo obbligatorio, per ritorno di analogie");
				}
			}
		}
		ActionListener listener2= new Attivazione_DeP();
		distanza_euclidea_ponderata.addActionListener(listener2);
		
		class Operazione_Indietro implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFrame frame = new Inserisci_Valori_Progetto(gestore);
			}
		}
		ActionListener listener4= new Operazione_Indietro();
		indietro.addActionListener(listener4);
		
		class Calcolo_Manhattan implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int valore= Integer.parseInt(field.getText());
				if (valore!=0) {
					JFrame frame= new Calcolo_Manhattan_Frame(gestore, variabili_selezionate, valore);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Inserire il k campo obbligatorio, per ritorno di analogie");
				}
			}
		}
		ActionListener listener3= new Calcolo_Manhattan();
		distanza_manhattan.addActionListener(listener3);
		
		class Calcolo_Minkowski implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int valore= Integer.parseInt(field.getText());
				if (valore!=0) {
					JFrame frame= new Calcolo_Minkowski_Frame(gestore, variabili_selezionate, valore);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Inserire il k campo obbligatorio, per ritorno di analogie");
				}
			}
		}
		ActionListener listener5= new Calcolo_Minkowski();
		distanza_minkowski.addActionListener(listener5);
		
		panel_center.add(distanza_euclidea);
		panel_center.add(distanza_euclidea_ponderata);
		panel_center.add(distanza_manhattan);
		panel_center.add(distanza_minkowski);
		panel_center.add(indietro);
		add(panel_center, BorderLayout.CENTER);
	}
}