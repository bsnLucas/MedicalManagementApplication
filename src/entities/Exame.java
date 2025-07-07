package entities;

public class Exame {

	private int id;
	private String nome;
	private double preco;
	private String orientacoes;

	public Exame() {
		
	}
	
	public Exame(int id, String nome, double preco, String orientacoes) {

		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.orientacoes = orientacoes;
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getOrientacoes() {
		return orientacoes;
	}

	public void setOrientacoes(String orientacoes) {
		this.orientacoes = orientacoes;
	}

	@Override
	public String toString() {
		return nome;
	}

	
}
