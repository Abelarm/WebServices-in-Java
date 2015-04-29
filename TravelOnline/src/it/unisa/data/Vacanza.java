package it.unisa.data;

public class Vacanza 
{
	
	private int id;
	private String nome;
	private String categoria;
	private int prezzo;
	public Vacanza()
	{
		
	}
	public Vacanza(int id, String nome, String categoria, int prezzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
		this.prezzo = prezzo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	@Override
	public String toString() {
		return "Vacanze [id=" + id + ", nome=" + nome + ", categoria="
				+ categoria + ", prezzo=" + prezzo + "]";
	}
	

}
