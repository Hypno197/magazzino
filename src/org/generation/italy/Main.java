package org.generation.italy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.generation.italy.data.Registro;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		ArrayList<Registro> entrata = new ArrayList<Registro>();
		ArrayList<Registro> uscita = new ArrayList<Registro>();
		Registro entry;
		String codiceGiacenza = new String();
		int id = 1, scelta, giacenza = 0;
		HashMap<String, String> fornitori = new HashMap<String, String>() {
			{
				put("001", "Mario SRL");
				put("002", "Rossi SPA");
			}
		};
		HashMap<String, String> prodotti = new HashMap<String, String>() {
			{
				put("001", "carta");
				put("002", "plastica");
			}
		};

		HashMap<String, String> clienti = new HashMap<String, String>() {
			{
				put("001", "pinco");
				put("002", "pallino");
			}
		};
		HashMap<String, String> tipoMovimento = new HashMap<String, String>() {
			{
				put("E01", "acquisto da fornitore");
				put("E02", "reso da cliente");
				put("E03", "produzione interna");
				put("E04", "spostamento da altro magazzino");
				put("U01", "vendita a cliente");
				put("U02", "reso a fornitore");
				put("U03", "sostituzione in garanzia");
				put("U04", "spostamento a altro magazzino");
			}
		};
//		System.out.println(tipoMovimento);
		System.out.println("GESTIONALE MAGAZZINO");
		do {
			System.out.println("COSA VUOI FARE");
			do {
				System.out.println("1.Inserire Movimento in ENTRATA\n" + "2.Inserire Movimento in USCITA\n"
						+ "3.visualizzare movimenti in ENTRATA\n" + "4.visualizzare movimenti in USCITA\n"
						+ "5.visualizzare giacenza PRODOTTO");
				scelta = sc.nextInt();
				sc.nextLine();
				if (scelta > 5 || scelta == 0)
					System.out.println("Selezione non valida!");
			} while (scelta > 5 || scelta == 0);
			// inserimento movimenti
			if (scelta == 1 || scelta == 2) {
				entry = new Registro();
				System.out.println("Inserire DATA movimento gg/MM/aaaa");
				entry.data = LocalDate.parse(sc.nextLine(), df);
				do {
					System.out.println("Inserire codice per il tipo di movimento");
					System.out.println(tipoMovimento.keySet());
					entry.codiceMov = sc.nextLine().toUpperCase();
					if (!tipoMovimento.containsKey(entry.codiceMov))
						System.out.println("Codice non valido!1!");
				} while (!tipoMovimento.containsKey(entry.codiceMov));
				do {
					if (entry.codiceMov.contains("E"))
						System.out.println("Inserire codice prodotto in entrata");
					else
						System.out.println("Inserire codice prodotto in uscita");
					entry.codiceProd = sc.nextLine();
				} while (!prodotti.containsKey(entry.codiceProd));
				System.out.println("Inserire quantità prodotto");
				entry.qtaProd = sc.nextInt();
				sc.nextLine();
				if (entry.codiceMov.equals("E02") || entry.codiceMov.equals("U01") || entry.codiceMov.equals("U03")) {
					System.out.println("Inserire codice cliente");
					entry.ref = sc.nextLine();
				} else if (entry.codiceMov.equals("E02") || entry.codiceMov.equals("U01")) {
					System.out.println("Inserire codice fornitore");
					entry.ref = sc.nextLine();
				} else
					entry.ref = null;
				id++;
				entry.id = id;
				if (entry.codiceMov.contains("E"))
					entrata.add(entry);
				else
					uscita.add(entry);
			}
			if (scelta == 3 || scelta == 4) {
				String movType = new String();
				if (scelta == 3)
					movType = "entrata";
				else
					movType = "uscita";
				System.out.println("Visualizzazione movimenti in " + movType);
				if (scelta == 3) {
					for (Registro i : entrata) {
						System.out.println("Data della transazione: " + i.data + " ID: " + i.id + "\n Quantità = "
								+ i.qtaProd + "\nProdotto : " + prodotti.get(i.codiceProd) + "\nTipo di movimento : "
								+ tipoMovimento.get(i.codiceMov));
						if (!i.ref.equals(null) && i.codiceMov.equals("E02"))
							System.out.println("Mittente :" + clienti.get(i.ref));
						else if (!i.ref.equals(null) && i.codiceMov.equals("E01"))
							System.out.println("Mittente :" + fornitori.get(i.ref));
					}
				} else
					for (Registro i : uscita) {
						System.out.println("Data della transazione: " + i.data + " ID: " + i.id + "\n Quantità = "
								+ i.qtaProd + "\nProdotto : " + prodotti.get(i.codiceProd) + "\nTipo di movimento : "
								+ tipoMovimento.get(i.codiceMov));
						if (!i.ref.equals(null) && i.codiceMov.equals("U01")
								|| !i.ref.equals(null) && i.codiceMov.equals("U03"))
							System.out.println("Destinatario :" + clienti.get(i.ref));
						else if (!i.ref.equals(null) && i.codiceMov.equals("U02"))
							System.out.println("Destinatario :" + fornitori.get(i.ref));
					}
			}
			if (scelta == 5) {
				System.out.println("Inserisci il codice prodotto da controllare");
				codiceGiacenza = sc.nextLine();
				giacenza = 0;
				for (Registro i:entrata)
					if (codiceGiacenza.equals(i.codiceProd))
						giacenza+=i.qtaProd;
				for (Registro i:uscita)
					if (codiceGiacenza.equals(i.codiceProd))
						giacenza-=i.qtaProd;
				System.out.println("la giacenza di "+prodotti.get(codiceGiacenza)+" è di : "+giacenza+" unità.");
			}
		} while (true);

	}

}
