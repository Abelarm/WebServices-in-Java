package it.unisa.service;

import it.unisa.data.GiochiManager;

public class GameHouseService {
	
	public String getGiochi(){
		return GiochiManager.getAll();
	}
	
	public String getPrezzo(String codice){
		double prezzo=GiochiManager.getPrezzo(codice);
		if(prezzo==-1)
			return "Il codice inserito non e' valido";
		else
			return "Il prezzo e': "+prezzo;
	}
	
	public String getPrezzoScontato(String codice,String sconto){
		double prezzo=GiochiManager.getPrezzo(codice);
		double saldo=Double.parseDouble(sconto);
		if(saldo==-1)
			return "Il prezzo e': "+prezzo;
		else{
			double scontodouble=(prezzo/100)*saldo;
			double tot=prezzo-scontodouble;
			return "il prezzo scontato è: "+ new Double(tot).toString();
		}
	}

}
