package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Bean.GestoreStruttura;
import Bean.Variabile;

public class Esecuzione_EuclidePond_Frame extends JFrame {
	
	//Dichiarazione delle variabili di istanza della classe
	private GestoreStruttura gestore;
	private ArrayList<Variabile> variabili_selezionate;
	private ArrayList<JTextField> fields;
	private ArrayList<Integer> pesi_inseriti;
	private JPanel central_panel;
	private JPanel sud_panel;
	private JButton conferma;
	private JButton indietro;
	private int k;
	
	public Esecuzione_EuclidePond_Frame(GestoreStruttura gestore, ArrayList<Variabile> selezionate, int k) {
		this.setTitle("Esecuzione Euclide Ponderata");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		
		this.gestore= gestore;
		this.variabili_selezionate= selezionate;
		this.k= k;
		fields= new ArrayList<>();
		pesi_inseriti= new ArrayList<>(); 
		
		JLabel label= new JLabel("Inserisci il peso per ogni variabile selezionata");
		label.setForeground(Color.red);
		
		createCentralPanel();
		createSudPanel();
		
		add(label, BorderLayout.NORTH);
		this.setVisible(true);
	}

	private void createSudPanel() {
		// TODO Auto-generated method stub
		sud_panel= new JPanel();
		conferma= new JButton("Conferma");
		indietro= new JButton("Indietro");
		
		class Avanti_Activity implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean check= false;
				for (JTextField f: fields) {
					int valore= Integer.parseInt(f.getText());
					if(valore!=0) {
						check= true;
						pesi_inseriti.add(valore);
					}else {
						pesi_inseriti.add(0);
					}
				}
				
				if (check=true) {
					JFrame frame= new DefinizioneDeP_Frame(gestore, variabili_selezionate, k, pesi_inseriti);
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Se non inserisci nessun peso torna indietro ed esegui Similarity Measure");
				}
			}	
		}
		ActionListener listener= new Avanti_Activity();
		conferma.addActionListener(listener);
		
		class Attività_Indietro implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame= new MenuFrame(gestore, variabili_selezionate);
				dispose();
			}
		}
		ActionListener listener2= new Attività_Indietro();
		indietro.addActionListener(listener2);
		
		sud_panel.add(conferma);
		sud_panel.add(indietro);
		add(sud_panel, BorderLayout.SOUTH);
	}

	private void createCentralPanel() {
		// TODO Auto-generated method stub
		central_panel= new JPanel();
		
		for (Variabile v: variabili_selezionate) {
			JLabel label1= new JLabel(v.getNome());
			JTextField field= new JTextField(5);
			field.setText("1");
			fields.add(field);
			central_panel.add(label1);
			central_panel.add(field);
		}
		
		add(central_panel, BorderLayout.CENTER);
	}
}