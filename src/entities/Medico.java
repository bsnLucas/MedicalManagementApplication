package entities;

public class Medico {

	private int crm;
	private String nome;
	private Endereco endereco;
	private String telefone;
	private Especialidade especialidade;

	public Medico() {
		
	}
	
	public Medico(int crm, String nome, Endereco endereco, String telefone, Especialidade especialidade) {

		this.crm = crm;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.especialidade = especialidade;
	}

	public int getCrm() {
		return crm;
	}


	public void setCrm(int crm) {
		this.crm = crm;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	@Override
	public String toString() {
		return nome;
	}
}
