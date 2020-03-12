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

public class Inserisci_Valori_Progetto extends JFrame {

	//Dichiarazione delle variabili di istanza della classe
	private GestoreStruttura gestore;
	private ArrayList<String> presenti;
	private ArrayList<JTextField> fields;
	private ArrayList<Variabile> var_selezionate;
	private int lunghezza_Text_Field= 5;
	private JPanel central_panel;
	private JPanel sud_panel;
	private JButton conferma;
	private JButton annulla;

	public Inserisci_Valori_Progetto(GestoreStruttura gestore) {
		this.setTitle("Inserisci dati progetto");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setBackground(Color.gray);

		this.gestore= gestore;
		this.presenti= gestore.nomi_variabili();
		fields= new ArrayList<>();
		var_selezionate= new ArrayList<>();

		createCentralPanel();
		createSudPanel();

		JLabel label= new JLabel("Ricorda! Puoi inserire solo numeri! Possono essere Float");
		label.setForeground(Color.RED);

		add(label, BorderLayout.NORTH);
		this.setVisible(true);
	}

	private void createSudPanel() {
		// TODO Auto-generated method stub
		central_panel= new JPanel();
		
		for(int i=0; i<presenti.size()-1; i++) {
			JLabel label= new JLabel(presenti.get(i));
			JTextField field= new JTextField(lunghezza_Text_Field);
			field.setText("0.0");
			fields.add(field);
			central_panel.add(label);
			central_panel.add(field);
		}

		add(central_panel, BorderLayout.CENTER);
	}

	private void createCentralPanel() {
		// TODO Auto-generated method stub
		sud_panel= new JPanel();
		conferma= new JButton("Conferma");
		annulla= new JButton("Annulla");

		class Conferma_Button implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				//Prima di richiamare il frame si deve controllare se sono stati inseriti qualche valore nei TextField
				boolean check= false;
				for (int i=0; i<fields.size(); i++) {
					JTextField appoggio= fields.get(i);
					String stringa= presenti.get(i);
					float valore= Float.parseFloat(appoggio.getText());
					if(valore!=0.0) {
						//Creiamo la variabile fittizzia
						check= true;
						Variabile fittizia= new Variabile(stringa, valore);
						System.out.println("Variabile selezionata: " + fittizia.getNome() + "  ...  " + fittizia.getValore());
						var_selezionate.add(fittizia);
						for (Variabile v: var_selezionate) {
							System.out.println("Selezionate: " + v.getNome() + " " + v.getValore());
						}
					}
					System.out.println(stringa + " Valore:  " + valore);
				}
				
				if(check==true) {
					JFrame frame= new MenuFrame(gestore, var_selezionate);
					dispose();
				}else {
					//Non è stata inserita nessuna variabile per lo studio torno una notifica di errore
					JOptionPane.showMessageDialog(null, "Inserisci almeno una variabile per svolgere l'attivita di studio");
				}
			}
		}
		ActionListener listener= new Conferma_Button();
		conferma.addActionListener(listener);
		
		class Annulla_Operazione implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame frame= new IndicazioniFrame(gestore);
				dispose();
			}
		}
		ActionListener listener2= new Annulla_Operazione();
		annulla.addActionListener(listener2);
		
		sud_panel.add(conferma);
		sud_panel.add(annulla);
		add(sud_panel, BorderLayout.SOUTH);
	}
}