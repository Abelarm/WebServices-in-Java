package it.unisa.service;


import it.unisa.data.Cliente;
import it.unisa.data.ClientiManager;
import it.unisa.data.Prenotazione;
import it.unisa.data.Vacanza;
import it.unisa.data.VacanzeLoader;


public class TravelOnlineService 
{
	
	public boolean registraCliente(Cliente c)
	{
		if (ClientiManager.checkCliente(c.getLogin()))
			return false;
		ClientiManager.writeClient(c);
		return true;
	}
	
	
	public Vacanza[] elencoPacchetti(String filtro_categoria)
	{
		
		Vacanza[] vec = VacanzeLoader.loadVacanze(filtro_categoria.toLowerCase().equals("tutte") ? null : filtro_categoria);
		
	
		System.out.println("-> " + vec.length);
		return vec;
		
	}
	
	
	public String prenotazione (String ID, int num_posti){
		double prezzo; 
		String res= Prenotazione.prenota(ID, num_posti);
		 try{
			 prezzo=Double.parseDouble(res);
			 return "Il prezzo totale è:"+prezzo;
		 }catch(NumberFormatException e){
			 return res;
		 }
	}
	
}
