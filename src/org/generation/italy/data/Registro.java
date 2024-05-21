package org.generation.italy.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Registro {
//	data
//	codice prodotto
//	quantità prodotto
//	codice movimento
//	riferimento (opzionale - cod fornitore (movimento E01), cod. cliente (movimento E02))
	DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public LocalDate data;
	public String codiceProd, codiceMov, ref;
	public int qtaProd, id;

}
