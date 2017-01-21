
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;


public class Glowna extends JFrame {

	
	public Glowna() {
		initUI();
	}

	
	private void initUI() {
		
		setTitle("Komunikator");
		
		setSize(570, 500);
		
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 260, 306);
		getContentPane().add(scrollPane);

		
		JTextPane trescKomunikatorLewy = new JTextPane();
		
		trescKomunikatorLewy.setEditable(false);
		
		scrollPane.setViewportView(trescKomunikatorLewy);

		
		JTextArea wiadomoscLewy = new JTextArea();
		
		wiadomoscLewy.setLineWrap(true);
		
		
		wiadomoscLewy.setBounds(10, 328, 260, 86);
		
		getContentPane().add(wiadomoscLewy);

		
		JButton dodajWiadomoscLewy = new JButton("Wyœlij");
		
		dodajWiadomoscLewy.setBounds(95, 425, 89, 23);
		
		getContentPane().add(dodajWiadomoscLewy);

		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(280, 11, 260, 306);
	
		getContentPane().add(scrollPane_1);

		
		JTextPane trescKomunikatorPrawy = new JTextPane();
	
		trescKomunikatorPrawy.setEditable(false);
		
		scrollPane_1.setViewportView(trescKomunikatorPrawy);

		
		JTextArea wiadomoscPrawy = new JTextArea();
		
		wiadomoscPrawy.setLineWrap(true);
		
		wiadomoscPrawy.setBounds(280, 328, 260, 86);
		
		getContentPane().add(wiadomoscPrawy);

		
		JButton dodajWiadomoscPrawy = new JButton("Wyœlij");
		
		dodajWiadomoscPrawy.setBounds(365, 425, 89, 23);
		
		getContentPane().add(dodajWiadomoscPrawy);

		Klient lewy = new Klient("Lewy", "23", "43");
		
		lewy.inicjuj(wiadomoscLewy, dodajWiadomoscLewy, trescKomunikatorLewy);

		
		Klient prawy = new Klient("Prawy", "19", "37");

		
		prawy.inicjuj(wiadomoscPrawy, dodajWiadomoscPrawy, trescKomunikatorPrawy);

		
		lewy.polacz(prawy);

		
		lewy.start();
		prawy.start();
	}

	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				Glowna ex = new Glowna();
				
				ex.setVisible(true);
			}
		});
	}
}

