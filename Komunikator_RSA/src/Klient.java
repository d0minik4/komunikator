
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;


public class Klient extends Thread {
	
	private JTextArea areaWiadomosci;
	
	private JButton dodajWiadomoscPrzycisk;
	
	private JTextPane trescKomunikator;

	private String nazwa;
	
	private Klient klient;
	
	
	private ArrayList<String> otrzymanaWiadomosc;
	
	private BigInteger liczbaE;
	
	private BigInteger liczbaD;
	
	private BigInteger liczbaN;

	
	public Klient(String nazwa, String p, String q) {
		
		this.nazwa = nazwa;
		
		BigInteger liczbaP = new BigInteger(p);
		BigInteger liczbaQ = new BigInteger(q);
		
		liczbaN = liczbaP.multiply(liczbaQ);
		
		BigInteger liczbaM = liczbaP.subtract(new BigInteger("1")).multiply(liczbaQ.subtract(new BigInteger("1")));
		
		liczbaE = new BigInteger("3");
		
		while (liczbaM.gcd(liczbaE).intValue() > 1) {
			
			liczbaE = liczbaE.add(new BigInteger("2"));
		}
		
		liczbaD = liczbaE.modInverse(liczbaM);
	}

	
	public void inicjuj(JTextArea areaWiadomosci, JButton dodajWiadomoscPrzycisk, JTextPane trescKomunikator) {
		
		this.areaWiadomosci = areaWiadomosci;
		this.dodajWiadomoscPrzycisk = dodajWiadomoscPrzycisk;
		this.trescKomunikator = trescKomunikator;

		
		this.dodajWiadomoscPrzycisk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			
				wyslijWiadomosc();
			}
		});
	}

	
	protected void wyslijWiadomosc() {
	
		String wiadomosc = areaWiadomosci.getText();
		
		if (wiadomosc != null && !wiadomosc.isEmpty()) {
		
			areaWiadomosci.setText("");
			
			dodajWiadomoscDoListy("Ja", wiadomosc);
		
			ArrayList<String> zaszyfrowanaWiadomosc = szyfruj(wiadomosc);
			
			dodajWiadomoscDoListy("Ja (szyfrowane)", jakoString(zaszyfrowanaWiadomosc));
			
			klient.przekazWiadomosc(zaszyfrowanaWiadomosc);
		}
	}

	
	private void dodajWiadomoscDoListy(String kto, String wiadomosc) {
	
		String obecnaTresc = trescKomunikator.getText();
		
		obecnaTresc = obecnaTresc + kto + "> " + wiadomosc + "\n";
		
		trescKomunikator.setText(obecnaTresc);
	}

	
	private void przekazWiadomosc(ArrayList<String> zaszyfrowanaWiadomosc) {
		
		otrzymanaWiadomosc = zaszyfrowanaWiadomosc;
	}

	
	public void polacz(Klient klient) {
		
		this.klient = klient;
	
		klient.klient = this;
	}

	
	@Override
	public void run() {
		
		super.run();
		
		while (true) {
			
			if (otrzymanaWiadomosc != null) {
				
				obsluzNowaWiadomosc(otrzymanaWiadomosc);
				
				otrzymanaWiadomosc = null;
			}
			
			else {
				
				try {
					
					sleep(1000);
				}
				
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
			}
		}
	}


	private void obsluzNowaWiadomosc(ArrayList<String> nowaWiadomosc) {
		
		String odszyfrowanaWiadomosc = deszyfruj(nowaWiadomosc);
		
		dodajWiadomoscDoListy(klient.nazwa + "(szyfrowane)", jakoString(nowaWiadomosc));
	
		dodajWiadomoscDoListy(klient.nazwa, odszyfrowanaWiadomosc);
	}

	
	private String jakoString(ArrayList<String> nowaWiadomosc) {
		
		String jakoString = "";
		
		for (String dane : nowaWiadomosc) {
			
			jakoString += dane + " ";
		}
		
		return jakoString;
	}

	
	public ArrayList<String> szyfruj(String wiadomosc) {
		
		ArrayList<String> zaszyfrowaneZnaki = new ArrayList<>();
		
		for (int i = 0; i < wiadomosc.length(); i++) {
			
			BigInteger wiadomoscBytes = new BigInteger(wiadomosc.substring(i, i + 1).getBytes());
			
			BigInteger odwPot = wiadomoscBytes.modPow(klient.liczbaE, klient.liczbaN);
			
			String zaszyfrowanaWiadomosc = odwPot.toString();
			
			zaszyfrowaneZnaki.add(zaszyfrowanaWiadomosc);
		}
		
		return zaszyfrowaneZnaki;
	}

	
	public String deszyfruj(ArrayList<String> nowaWiadomosc) {
		
		String rozszyfrowanaWiadomosc = "";
		
		for (String dane : nowaWiadomosc) {
			
			BigInteger daneInteger = new BigInteger(dane);
			
			BigInteger odwPot = daneInteger.modPow(liczbaD, liczbaN);
			
			rozszyfrowanaWiadomosc += new String(odwPot.toByteArray());
		}
		
		return rozszyfrowanaWiadomosc;
	}
}
